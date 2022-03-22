package shop.daijian.trade.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import shop.daijian.common.dto.GoodsBriefDTO;
import shop.daijian.common.exception.BizException;
import shop.daijian.common.interfaces.GoodsService;
import shop.daijian.trade.entity.Order;
import shop.daijian.trade.entity.OrderGoods;
import shop.daijian.trade.entity.OrderInfo;
import shop.daijian.trade.enums.OrderStateEnum;
import shop.daijian.trade.enums.TradeStatusEnum;
import shop.daijian.trade.form.GoodsPackForm;
import shop.daijian.trade.form.OrderForm;
import shop.daijian.trade.repository.mybatis.OrderInfoMapper;
import shop.daijian.trade.repository.redis.OrderRedisTemplate;
import shop.daijian.trade.service.OrderGoodsService;
import shop.daijian.trade.service.OrderInfoService;
import shop.daijian.trade.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static shop.daijian.common.util.BeanUtil.transObj;

/**
 * <p>
 * 订单信息 服务实现类
 * </p>
 *
 * @author 关栋伟
 * @since 2019-08-03
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderService {

    @Reference
    private GoodsService goodsService;

    @Autowired
    private OrderRedisTemplate orderRedisTemplate;

    @Autowired
    private OrderGoodsService orderGoodsService;

    @Autowired
    private OrderInfoService orderInfoService;

    @Override
    public List<Order> previewOrderList(List<GoodsPackForm> goodsPackFormList, String userId) {

        // Map<店铺id, 订单商品列表> 分组
        HashMap<String, Order> orderGroup = new HashMap<>(2);
        // 获取商品id列表
        List<String> goodsIdList = goodsPackFormList.stream().map(GoodsPackForm::getGoodsId).collect(Collectors.toList());
        // 商品信息列表
        List<GoodsBriefDTO> goodsBriefDTOList = goodsService.listByIdList(goodsIdList);
        //根据shopId分单并填充orderGroup
        for (int i = 0; i < goodsBriefDTOList.size(); i++) {
            GoodsBriefDTO goodsBriefDTO = goodsBriefDTOList.get(i);
            String shopId = goodsBriefDTO.getShopId();
            OrderGoods orderGoods = transObj(goodsBriefDTO, OrderGoods.class);
            orderGoods.setUserId(userId).setNum(goodsPackFormList.get(i).getNum());
            //shopId已存在
            if(orderGroup.containsKey(shopId)){
                Order order = orderGroup.get(shopId);
                // 订单信息
                OrderInfo orderInfo = order.getOrderInfo();
                // 订单商品列表
                List<OrderGoods> orderGoodsList = order.getOrderGoodsList();
                // 设置订单商品的订单id
                orderGoods.setOrderId(orderInfo.getOrderId());
                // 添加进订单商品列表
                orderGoodsList.add(orderGoods);
                // 累加订单总价
                BigDecimal orderPrice = orderInfo.getTotalPrice();
                Integer num = orderGoods.getNum();
                BigDecimal newPrice = orderPrice.add(orderGoods.getUnitPrice().multiply(BigDecimal.valueOf(num)));
                orderInfo.setTotalPrice(newPrice);
                orderInfo.setNum(orderInfo.getNum() + 1);
            }else {
                OrderInfo orderInfo = new OrderInfo();
                List<OrderGoods> orderGoodsList = new ArrayList();
                // 生成订单id
                String orderId = IdWorker.getIdStr();
                //补充orderGoods
                orderGoods.setOrderId(orderId);
                //填充orderGoodsList
                orderGoodsList.add(orderGoods);
                Integer num = orderGoods.getNum();
                //填充orderInfo
                orderInfo.setOrderId(orderId)
                        .setShopId(shopId)
                        .setShopName(goodsBriefDTO.getShopName())
                        .setShopLogoUrl(goodsBriefDTO.getShopAvatarUrl())
                        .setNum(1)
                        .setTotalPrice(orderGoods.getUnitPrice().multiply(BigDecimal.valueOf(num)))
                        .setState(OrderStateEnum.WAIT_PAY)
                        .setUserId(userId);
                //新建order并加入orderGroup
                Order order = new Order().setOrderInfo(orderInfo).setOrderGoodsList(orderGoodsList);
                orderGroup.put(shopId,order);
            }

        }
        ArrayList<Order> orderList = new ArrayList<>(orderGroup.values());
        orderRedisTemplate.set(orderList,userId);
        return orderList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void creatOrder(OrderForm orderForm, String userId) {

        //获取订单缓存
        List<Order> orderList = orderRedisTemplate.get(orderForm.getOrderIdList(), userId);
        if (orderList.size() == 0) {
            throw new BizException(TradeStatusEnum.CREATE_EXPIRED);
        }

        //TODO 待优化
        //逐个减少库存
        for (Order order : orderList) {
            for (int i = 0; i < order.getOrderGoodsList().size(); i++) {
                OrderGoods goods = order.getOrderGoodsList().get(i);
                goodsService.reduceStock(goods.getGoodsId(),goods.getNum());
            }
        }
        // 订单信息列表
        List<OrderInfo> orderInfoList = new ArrayList<>();
        // 订单商品列表
        List<OrderGoods> orderGoodsList = new ArrayList<>();

        orderList.forEach(order -> {
            // 完善公共订单信息
            OrderInfo orderInfo = order.getOrderInfo();
            orderInfo.setUserAddressId(orderForm.getUserAddressId())
                    //改变订单状态为待付款
                    .setState(OrderStateEnum.WAIT_PAY);
            orderInfoList.add(order.getOrderInfo());
            orderGoodsList.addAll(order.getOrderGoodsList());
        });
        // 保存到数据库
        orderInfoService.saveBatch(orderInfoList);
        orderGoodsService.saveBatch(orderGoodsList);

        //从缓存中删除
        orderRedisTemplate.deleteList(orderForm.getOrderIdList(), userId);
    }

    @Override
    public void cancel(String orderId, String userId) {
        OrderInfo orderInfo = orderInfoService.getById(orderId);
        if(orderInfo==null || orderInfo.getState()!=OrderStateEnum.WAIT_PAY){
            throw new BizException(TradeStatusEnum.STATE_UN_SUPPORT);
        }
        //改变订单状态
        orderInfo.setState(OrderStateEnum.CANCLED);
        orderInfoService.updateById(orderInfo);
        //改变订单商品状态
        List<OrderGoods> orderGoodsList = orderGoodsService.getGoodsListByOrderId(orderId);
        for (int i = 0; i < orderGoodsList.size(); i++) {
            orderGoodsList.get(i).setState(OrderStateEnum.CANCLED);
        }
        orderGoodsService.updateBatchById(orderGoodsList);
    }

    @Override
    public void rollBackStock(String orderId) {
        List<OrderGoods> orderGoodsList = orderGoodsService.getGoodsListByOrderId(orderId);
        for (int i = 0; i < orderGoodsList.size(); i++) {
            OrderGoods goods = orderGoodsList.get(i);
            goodsService.reduceStock(goods.getGoodsId(),-goods.getNum());
        }
    }


}
