package shop.daijian.trade.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.daijian.common.support.IStatusEnum;

/**
 * @author guandongwei
 * 2019/8/14
 */
@Getter
@AllArgsConstructor
public enum TradeStatusEnum implements IStatusEnum {

    FAIL_CRATE_TRADE(500, "创建交易失败"),

    ILLEGAL_ORDER_STATUS(500, "非法的订单状态"),

    ORDER_NOT_EXIST(404, "订单不存在"),

    /**
     * 生成的订单在redis中已失效或不存在，无法继续创建订单
     */
    CREATE_EXPIRED(500, "提交订单已创建或失效"),

    PAY_EXPIRED(500, "订单付款超时"),

    STATE_UN_SUPPORT(500, "当前订单状态不支持此操作"),

    NO_PERMISSION(500, "没有权限");

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态解释
     */
    private String msg;
}

