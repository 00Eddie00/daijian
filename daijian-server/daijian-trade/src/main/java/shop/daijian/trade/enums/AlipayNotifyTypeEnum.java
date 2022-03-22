package shop.daijian.trade.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付宝异步通知类型枚举类
 * @author guandongwei
 * 2019/8/4
 */
@Getter
@AllArgsConstructor
public enum AlipayNotifyTypeEnum {

    /**
     * 支付异步通知
     */
    TRADE("trade_status_sync");

    /**
     * 类型代码
     */
    private String code;

}
