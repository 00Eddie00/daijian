package shop.daijian.trade.enums;

/**
 *订单收货状态枚举类
 *
 * @author guandongwei
 * 2019/8/4
 */
public enum  OrderStateEnum {

    /**
     * 初始化
     */
    INITIALIZE,
    /**
     * 待付款
     */
    WAIT_PAY,

    /**
     * 待发货
     */
    WAIT_SEND,

    /**
     * 待收货
     */
    WAIT_CONFIRM,

    /**
     *待评价
     */
    WAIT_COMMENT,

    /**
     * 已评价
     */
    COMMENTED,
    /**
     * 订单完成
     */
    DONE,

    /**
     * 已取消
     */
    CANCLED,


}
