package shop.daijian.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.daijian.common.support.IStatusEnum;

/**
 * 购物车状态枚举
 *
 * @author hanshizhou
 * @since 2019/8/11
 **/
@Getter
@AllArgsConstructor
public enum  CartStatusEnum implements IStatusEnum {

    CART_NOT_EXIST(500, "购物车不存在"),
    ;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态解释
     */
    private String msg;

}