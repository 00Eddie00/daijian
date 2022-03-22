package shop.daijian.trade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.daijian.common.enums.RedisKeyEnum;
import shop.daijian.common.support.BaseRedisTemplate;
import shop.daijian.common.vo.PageVO;
import shop.daijian.trade.entity.Order;
import shop.daijian.trade.entity.OrderGoods;
import shop.daijian.trade.entity.OrderInfo;
import shop.daijian.trade.enums.OrderStateEnum;
import shop.daijian.trade.repository.mybatis.OrderGoodsMapper;
import shop.daijian.trade.service.OrderGoodsService;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单商品 服务实现类
 * </p>
 *
 * @author guandongwei
 * @author hanshizhou
 * @since 2019-08-04
 */
@Service
public class OrderGoodsServiceImpl extends ServiceImpl<OrderGoodsMapper, OrderGoods> implements OrderGoodsService {

    @Autowired
    private OrderGoodsMapper orderGoodsMapper;

    @Autowired
    private BaseRedisTemplate<Order> template;

    @Override
    public List<OrderGoods> getGoodsListByOrderId(String orderId) {

        QueryWrapper<OrderGoods> orderGoodsQueryWrapper = new QueryWrapper<>();
        orderGoodsQueryWrapper.eq("order_id", orderId);
        List<OrderGoods> orderGoodsList = orderGoodsMapper.selectList(orderGoodsQueryWrapper);
        return orderGoodsList;
    }

    @Override
    public String getGoodsIdByOrderGoodsId(String OrderGoodsId) {
        OrderGoods orderGoods = orderGoodsMapper.selectById(OrderGoodsId);
        return orderGoods.getGoodsId();
    }

    @Override
    public void setWaitComment(String orderId) {
        List<OrderGoods> goodsList = getGoodsListByOrderId(orderId);

        for (OrderGoods orderGoods : goodsList) {
            orderGoods.setState(OrderStateEnum.WAIT_COMMENT);
            orderGoodsMapper.updateById(orderGoods);
        }
    }

    @Override
    public List<String> getGoodsIdListByOrderId(String orderId, String userId) {

        Order order = template.get(RedisKeyEnum.ORDER.buildKey(orderId, userId), Order.class);
        List<OrderGoods> orderGoodsList = order.getOrderGoodsList();
        List<String> goodsIdList = orderGoodsList.stream().map(OrderGoods::getGoodsId).collect(Collectors.toList());
        return goodsIdList;
    }

    @Override
    public OrderGoods getByOrderIdAndGoodsId(String orderId, String goodsId) {
        QueryWrapper<OrderGoods> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id", orderId).eq("goods_id", goodsId);
        return orderGoodsMapper.selectOne(wrapper);
    }

    @Override
    public PageVO<String> pageRecentGoodsId(IPage<OrderGoods> page, String userId) {
        QueryWrapper<OrderGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("user_id", userId)
                .in("state", OrderStateEnum.WAIT_COMMENT, OrderStateEnum.COMMENTED)
                .ge("create_time", lastMonth())
                .orderByDesc("create_time");
        IPage<OrderGoods> orderGoodsPage = page(page, queryWrapper);
        List<String> goodsIdList = orderGoodsPage.getRecords().stream().map(OrderGoods::getGoodsId).distinct().collect(Collectors.toList());
        return new PageVO<>(page, goodsIdList);
    }

    @Override
    public Map<OrderStateEnum, Integer> getOrderStateNumMap(String userId) {

        Map<OrderStateEnum, Integer> orderStateNumMap = new HashMap<>();

        orderStateNumMap.put(OrderStateEnum.WAIT_COMMENT,
                count(new QueryWrapper<OrderGoods>()
                        .eq("state", OrderStateEnum.WAIT_COMMENT)
                        .eq("user_id", userId)));

        return orderStateNumMap;
    }

    private LocalDateTime lastMonth() {
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime lastMouth = ZonedDateTime.now().toLocalDateTime().minusMonths(1);
        return lastMouth;
    }

}
