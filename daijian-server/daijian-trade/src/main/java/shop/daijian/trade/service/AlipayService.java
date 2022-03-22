package shop.daijian.trade.service;

import java.util.List;
import java.util.Map;

/**
 * @author guandongwei
 * @since 2019/8/12
 */
public interface AlipayService {
    /**
     * 创建阿里巴巴网页支付
     * @param orderIdList
     */
    String createPagePay(List<String> orderIdList);

    /**
     * 创建阿里巴巴APP支付
     * @param orderIdList
     */
    String createAppPay(List<String> orderIdList);
    /**
     * 验签后完善订单信息
     * @param paramMap
     */
    void finishAlipayTrade(Map<String, String> paramMap);
}
