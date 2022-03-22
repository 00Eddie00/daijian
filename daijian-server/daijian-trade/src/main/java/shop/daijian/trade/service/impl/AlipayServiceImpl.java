package shop.daijian.trade.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.daijian.common.exception.BizException;
import shop.daijian.trade.configuration.AlipayProperties;
import shop.daijian.trade.entity.OrderInfo;
import shop.daijian.trade.enums.OrderStateEnum;
import shop.daijian.trade.enums.TradeStatusEnum;
import shop.daijian.trade.service.AlipayService;
import shop.daijian.trade.service.OrderGoodsService;
import shop.daijian.trade.service.OrderInfoService;
import shop.daijian.trade.service.PaymentInfoService;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author guandongwei
 * @since 2019/8/12
 */
@Service
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    private AlipayClient alipayClient;

    @Autowired
    private AlipayProperties alipayProperties;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private OrderGoodsService orderGoodsService;

    @Autowired
    private PaymentInfoService paymentInfoService;


    /**
     * 支付超时时间
     */
    private static final String TIMEOUT_EXPRESS = "2h";

    public String createAppPay(List<String> orderIdList) {
        List<OrderInfo> orderInfoList = (List<OrderInfo>) orderInfoService.listByIds(orderIdList);
        BigDecimal totalPrice =new BigDecimal(0);
        // 计算总价，填充交易名
        for (OrderInfo orderInfo : orderInfoList) {
            totalPrice = totalPrice.add(orderInfo.getTotalPrice());
        }
        // 构造支付宝交易对象
        AlipayTradeAppPayModel payModel = new AlipayTradeAppPayModel();
        String outTradeNo = IdWorker.getIdStr();
        payModel.setOutTradeNo(outTradeNo);
        //*销售产品码，与支付宝签约的产品码名称
        payModel.setProductCode("QUICK_MSECURITY_PAY");
        payModel.setSubject("待见订单");
        payModel.setTotalAmount(totalPrice.toString());
        payModel.setTimeoutExpress(TIMEOUT_EXPRESS);
        //订单id列表作为异步通知回传参数
        String orderIdJSONArray = JSONArray.toJSONString(orderIdList);
        payModel.setPassbackParams(orderIdJSONArray);
        // //创建API对应的request
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        request.setBizModel(payModel);
        //回调地址
        request.setReturnUrl(alipayProperties.getReturnUrl());
        //异步通知地址
        request.setNotifyUrl(alipayProperties.getNotifyUrl());
        String alipayCode=null;
        try {
            alipayCode = alipayClient.sdkExecute(request).getBody();
            System.out.println(alipayCode);
            return alipayCode;
        } catch (AlipayApiException e) {
            throw new BizException(TradeStatusEnum.FAIL_CRATE_TRADE.getMsg());
        }
    }


    @Override
    public String createPagePay(List<String> orderIdList) {
        List<OrderInfo> orderInfoList = (List<OrderInfo>) orderInfoService.listByIds(orderIdList);
        BigDecimal totalPrice =new BigDecimal(0);
        // 计算总价，填充交易名
        for (OrderInfo orderInfo : orderInfoList) {
            totalPrice = totalPrice.add(orderInfo.getTotalPrice());
        }
        // 构造支付宝交易对象
        AlipayTradePagePayModel payModel = new AlipayTradePagePayModel();
        String outTradeNo = IdWorker.getIdStr();
        payModel.setOutTradeNo(outTradeNo);
        //*销售产品码，与支付宝签约的产品码名称
        payModel.setProductCode("FAST_INSTANT_TRADE_PAY");
        payModel.setSubject("待见订单");
        payModel.setTotalAmount(totalPrice.toString());
        payModel.setTimeoutExpress(TIMEOUT_EXPRESS);
        //订单id列表作为异步通知回传参数
        String orderIdJSONArray = JSONArray.toJSONString(orderIdList);
         payModel.setPassbackParams(orderIdJSONArray);

        System.out.println(outTradeNo);

        // //创建API对应的request
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setBizModel(payModel);
        //回调地址
        request.setReturnUrl(alipayProperties.getReturnUrl());
        //异步通知地址
        request.setNotifyUrl(alipayProperties.getNotifyUrl());
        String alipayCode=null;
        try {
            alipayCode = alipayClient.pageExecute(request).getBody();
            System.out.println(alipayCode);

            return alipayCode;
        } catch (AlipayApiException e) {
            throw new BizException(TradeStatusEnum.FAIL_CRATE_TRADE.getMsg());
        }
    }

    @Override
    public void finishAlipayTrade(Map<String, String> paramMap) {
        String orderIdJSONArray = paramMap.get("passback_params");
        List<String> orderIdList = JSONArray.parseArray(orderIdJSONArray, String.class);
        Collection<OrderInfo> orderInfoList = orderInfoService.listByIds(orderIdList);
        for (OrderInfo orderInfo : orderInfoList) {
            if (!orderInfo.getState().equals(OrderStateEnum.WAIT_PAY)) {
                throw new BizException(TradeStatusEnum.ILLEGAL_ORDER_STATUS.getMsg());
            }
            orderInfo.setPayType("支付宝").setTradeNum(paramMap.get("trade_no")).setState(OrderStateEnum.WAIT_CONFIRM);
            // 更新订单状态
        }
        orderInfoService.updateBatchById(orderInfoList);
    }
}
