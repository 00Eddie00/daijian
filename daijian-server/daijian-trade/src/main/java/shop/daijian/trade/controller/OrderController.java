package shop.daijian.trade.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.daijian.common.dto.GoodsBriefDTO;
import shop.daijian.common.enums.ShopCreditEnum;
import shop.daijian.common.exception.BizException;
import shop.daijian.common.form.PageForm;
import shop.daijian.common.interfaces.GoodsService;
import shop.daijian.common.interfaces.ShopService;
import shop.daijian.common.support.BaseController;
import shop.daijian.common.util.BeanUtil;
import shop.daijian.common.vo.PageVO;
import shop.daijian.common.wrapper.ResultWrapper;
import shop.daijian.trade.entity.Order;
import shop.daijian.trade.entity.OrderGoods;
import shop.daijian.trade.entity.OrderInfo;
import shop.daijian.trade.enums.OrderStateEnum;
import shop.daijian.trade.enums.PaymentTypeEnum;
import shop.daijian.trade.enums.TradeStatusEnum;
import shop.daijian.trade.form.GoodsPackForm;
import shop.daijian.trade.form.OrderForm;
import shop.daijian.trade.service.AlipayService;
import shop.daijian.trade.service.OrderGoodsService;
import shop.daijian.trade.service.OrderInfoService;
import shop.daijian.trade.service.OrderService;
import shop.daijian.trade.vo.OrderDetailVO;
import shop.daijian.trade.vo.OrderGoodsVO;
import shop.daijian.trade.vo.OrderPreviewVO;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static shop.daijian.common.util.BeanUtil.transList;

/**
 * <p>
 * 订单信息 前端控制器
 * </p>
 *
 * @author guandongwei
 * @author hanshizhou
 * @since 2019-08-03
 */
@Slf4j
@Api(tags = "订单接口")
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private OrderGoodsService orderGoodsService;

    @Reference
    private GoodsService goodsService;

    @Autowired
    private AlipayService alipayService;

    @Reference
    private ShopService shopService;

    @ApiOperation("返回订单状态对应的订单数量")
    @GetMapping("/orderStateNums")
    public ResultWrapper<Map<OrderStateEnum, Integer>> getOrderStateNums(@RequestHeader String userId) {
        Map<OrderStateEnum, Integer> orderStateEnumIntegerMap = orderInfoService.getOrderStateNumsMap(userId);
        orderStateEnumIntegerMap.putAll(orderGoodsService.getOrderStateNumMap(userId));
        return ResultWrapper.success(orderStateEnumIntegerMap);
    }

    @ApiOperation("生成订单预览")
    @PostMapping("/preview")
    public ResultWrapper<List<OrderPreviewVO>> generateCache(@ApiParam("商品包裹列表表单") @RequestBody @Valid List<GoodsPackForm> goodsPackFormList, @RequestHeader String userId, BindingResult bindingResult) {

        //验证参数
        validateParams(bindingResult);
        //缓存订单并返回按商铺分单后的订单列表
        List<Order> orderList = orderService.previewOrderList(goodsPackFormList, userId);

        if (orderList == null) {
            throw new BizException(TradeStatusEnum.FAIL_CRATE_TRADE);
        }
        //包装并返回订单VO
        List<OrderPreviewVO> orderPreviewVOList = new ArrayList<>();
        for (int i = 0; i < orderList.size(); i++) {
            Order order = orderList.get(i);
            OrderPreviewVO orderPreviewVO = BeanUtil.transObj(order.getOrderInfo(), OrderPreviewVO.class);
            orderPreviewVO.setShopAvatarUrl(order.getOrderInfo().getShopLogoUrl());
            orderPreviewVO.setCreateTime(LocalDateTime.now());
            orderPreviewVO.setTotalPrice(order.getOrderInfo().getTotalPrice());
            //获得订单中订单商品
            List<OrderGoods> orderGoodsList = order.getOrderGoodsList();
            List<String> goodsIdList = orderGoodsList.stream().map(OrderGoods::getGoodsId).collect(Collectors.toList());
            List<GoodsBriefDTO> goodsBriefDTOS = goodsService.listByIdList(goodsIdList);
            List<OrderGoodsVO> orderGoodsVOS = transList(goodsBriefDTOS, OrderGoodsVO.class);
            //判断订单是否出现异常
            if (goodsIdList.size() != goodsBriefDTOS.size()) {
                throw new BizException(500, TradeStatusEnum.ILLEGAL_ORDER_STATUS.getMsg());
            }
            for (int j = 0; j < orderGoodsVOS.size(); j++) {
                OrderGoodsVO orderGoodsVO = orderGoodsVOS.get(j);
                OrderGoods orderGoods = orderGoodsList.get(j);
                GoodsBriefDTO goodsBriefDTO = goodsBriefDTOS.get(j);
                Integer num = orderGoods.getNum();
                BigDecimal unitPrice = orderGoods.getUnitPrice();
                //价格转换单位为元
                orderGoodsVO.setUnitPrice(unitPrice);
                orderGoodsVO.setNum(num);
                orderGoodsVO.setTotalPrice(unitPrice.multiply(BigDecimal.valueOf(num)));
                orderGoodsVO.setOriginPrice(goodsBriefDTO.getOriginPrice());
            }
            orderPreviewVO.setOrderGoodsVO(orderGoodsVOS);
            orderPreviewVOList.add(orderPreviewVO);
        }
        return ResultWrapper.success(orderPreviewVOList);
    }

    @ApiOperation("创建订单")
    @PostMapping("/create")
    public ResultWrapper createOrder(@ApiParam("订单表单") @Valid @RequestBody OrderForm orderForm, @RequestHeader String userId, BindingResult bindingResult) {
        //验证参数
        validateParams(bindingResult);
        //创建订单
        orderService.creatOrder(orderForm, userId);
        //支付模块
        String alipayTradeBody = null;
        if (orderForm.getPaymentTypeEnum().equals(PaymentTypeEnum.ALIPAY_PAGE)) {
            //阿里电脑网页支付
            alipayTradeBody = alipayService.createPagePay(orderForm.getOrderIdList());
        }
        if (orderForm.getPaymentTypeEnum().equals(PaymentTypeEnum.ALIPAY_APP)) {
            //阿里APP支付
            alipayTradeBody = alipayService.createAppPay(orderForm.getOrderIdList());
        }

        return ResultWrapper.success(alipayTradeBody);
    }

    @ApiOperation("取消订单")
    @PostMapping("/cancel/{orderId}")
    public ResultWrapper cancelOrder(@ApiParam("订单Id") @PathVariable String orderId, @RequestHeader String userId) {
        if (!orderInfoService.getById(orderId).getUserId().equals(userId)) {
            throw new BizException(TradeStatusEnum.STATE_UN_SUPPORT);
        }
        try {
            //回滚库存
            orderService.rollBackStock(orderId);
            //取消订单
            orderService.cancel(orderId, userId);
        } catch (Exception e) {
            log.debug(e.getMessage());
            e.printStackTrace();
            return ResultWrapper.failure(e.getMessage());
        }
        return ResultWrapper.success();
    }

    @ApiOperation("删除历史订单")
    @DeleteMapping("/{orderId}")
    public ResultWrapper removeOrder(@ApiParam("订单id") @PathVariable String orderId, @RequestHeader String userId) {
        if (!orderInfoService.getById(orderId).getUserId().equals(userId)) {
            throw new BizException(TradeStatusEnum.STATE_UN_SUPPORT);
        }
        OrderInfo orderInfo = orderInfoService.getById(orderId);
        if (orderInfo.getState() == OrderStateEnum.DONE) {
            orderInfoService.removeById(orderId);
        } else {
            throw new BizException(TradeStatusEnum.STATE_UN_SUPPORT);
        }
        return ResultWrapper.success();
    }


    @ApiOperation("获取订单详情")
    @GetMapping("/detail/{orderId}")
    public ResultWrapper<OrderDetailVO> listOrderDetail(@ApiParam("订单Id") @PathVariable String orderId,
                                                        @RequestHeader String userId) {

        // 验证订单信息
        OrderInfo orderInfo = orderInfoService.getById(orderId);
        if (orderInfo == null) {
            throw new BizException(TradeStatusEnum.ORDER_NOT_EXIST);
        }
        if (!orderInfo.getUserId().equals(userId)) {
            throw new BizException(TradeStatusEnum.NO_PERMISSION);
        }
        //创建订单详细信息
        OrderDetailVO orderDetailVO = new OrderDetailVO();
        //转换组装订单信息
        OrderDetailVO detailVO = BeanUtil.transObj(orderInfo, orderDetailVO.getClass());
        //获取订单商品列表
        List<OrderGoodsVO> goodsVOList = getGoodsVOListByOrderId(orderInfo.getOrderId());
        // 组装订单商品列表
        detailVO.setOrderGoodsVOList(goodsVOList);
        return ResultWrapper.success(detailVO);
    }

    @ApiOperation("确认收货")
    @PutMapping("/confirm/{orderId}")
    public ResultWrapper confirm(@ApiParam("订单Id") @PathVariable String orderId,
                                 @RequestHeader String userId) {

        OrderInfo orderInfo = orderInfoService.getById(orderId);
        //验证用户
        if (!orderInfo.getUserId().equals(userId)) {
            throw new BizException(500, TradeStatusEnum.NO_PERMISSION.toString());
        }
        //设置订单状态为已完成
        orderInfo.setState(OrderStateEnum.DONE);
        orderInfoService.updateById(orderInfo);
        //将订单商品状态改为待评价
        orderGoodsService.setWaitComment(orderId);
        //增加店铺信用值
        String shopIdByOrderId = orderInfoService.getShopIdByOrderId(orderId);
        shopService.setCredit(shopIdByOrderId, ShopCreditEnum.ORDER_SUCCESS_REWARD_FOR_SHOP);
        return ResultWrapper.success();
    }

    @ApiOperation("分页查询用户订单列表")
    @GetMapping("/user")
    public ResultWrapper<PageVO<OrderDetailVO>> listBrief(@RequestHeader String userId, @RequestParam(required = false) OrderStateEnum orderState,
                                                          @ApiParam("订单状态(为空则获取所有)") @Valid PageForm pageForm, BindingResult bindResult) {
        validateParams(bindResult);
        IPage<OrderInfo> orderInfoPage = new Page<>(pageForm.getPage(), pageForm.getSize());

        // 获取content为orderInfoList的page
        if (orderState == null) {
            orderInfoPage = orderInfoService.pageByUserId(orderInfoPage, userId);
        } else {
            orderInfoPage = orderInfoService.pageByUserIdAndState(orderInfoPage, orderState, userId);
        }

        // 订单简略信息列表
        List<OrderInfo> orderInfoList = orderInfoPage.getRecords();
        //把pageDTO的content转化成orderBriefVOList
        List<OrderDetailVO> orderDetailVOList = transList(orderInfoList, OrderDetailVO.class);
        for (OrderDetailVO orderDetailVO : orderDetailVOList) {
            List<OrderGoodsVO> goodsVOListByOrderId = getGoodsVOListByOrderId(orderDetailVO.getOrderId());
            //组装OrderBriefVO
            orderDetailVO.setOrderGoodsVOList(goodsVOListByOrderId);
        }
        //偷梁换柱，把之前查出的PageDTO<orderInfo>的content换成orderBriefVOList
        PageVO<OrderDetailVO> orderBriefVOPageDTO = new PageVO<>(orderInfoPage, orderDetailVOList);
        return ResultWrapper.success(orderBriefVOPageDTO);
    }

    @ApiOperation("分页获取近期用户购买的商品")
    @GetMapping("/recent-goods")
    public ResultWrapper<PageVO<GoodsBriefDTO>> pageRecentGoods(@RequestHeader String userId, @Valid PageForm pageForm, BindingResult bindResult) {
        validateParams(bindResult);
        //分页查询
        IPage<OrderGoods> orderGoodsPage = new Page<>(pageForm.getPage(), pageForm.getSize());
        PageVO<String> goodsIdPage = orderGoodsService.pageRecentGoodsId(orderGoodsPage, userId);
        //填充详细信息
        List<GoodsBriefDTO> goodsBriefDTOList = goodsService.listByIdList(goodsIdPage.getContent());
        return ResultWrapper.success(goodsIdPage.replaceContent(goodsBriefDTOList));
    }

    /**
     * 通过orderId获取GoodsVOList
     */
    private List<OrderGoodsVO> getGoodsVOListByOrderId(String orderId) {
        List<OrderGoods> orderGoodsList = orderGoodsService.getGoodsListByOrderId(orderId);
        List<String> goodsIdList = orderGoodsList.stream().map(OrderGoods::getGoodsId).collect(Collectors.toList());
        List<GoodsBriefDTO> goodsBriefDTOS = goodsService.listByIdList(goodsIdList);
        List<OrderGoodsVO> orderGoodsVOS = transList(goodsBriefDTOS, OrderGoodsVO.class);
        //判断订单是否出现异常
        if (goodsIdList.size() != goodsBriefDTOS.size()) {
            throw new BizException(500, TradeStatusEnum.ILLEGAL_ORDER_STATUS.getMsg());
        }
        for (int i = 0; i < orderGoodsVOS.size(); i++) {
            OrderGoodsVO orderGoodsVO = orderGoodsVOS.get(i);
            OrderGoods orderGoods = orderGoodsList.get(i);
            GoodsBriefDTO goodsBriefDTO = goodsBriefDTOS.get(i);
            //从数据库查是否有订单商品状态
            OrderGoods orderGoodsByOrderIdAndGoodsId = orderGoodsService.getByOrderIdAndGoodsId(orderId, orderGoodsVO.getGoodsId());
            if (orderGoodsByOrderIdAndGoodsId != null) {
                orderGoodsVO.setState(orderGoodsByOrderIdAndGoodsId.getState());
            }
            Integer num = orderGoods.getNum();
            orderGoodsVO.setNum(num);
            BigDecimal unitPrice = orderGoods.getUnitPrice();
            orderGoodsVO.setOrderGoodsId(orderGoods.getOrderGoodsId());
            //设置价格单位为元
            orderGoodsVO.setOriginPrice(goodsBriefDTO.getOriginPrice());
            orderGoodsVO.setUnitPrice(unitPrice);
            orderGoodsVO.setTotalPrice(unitPrice.multiply(BigDecimal.valueOf(num)));
        }
        return orderGoodsVOS;
    }
}
