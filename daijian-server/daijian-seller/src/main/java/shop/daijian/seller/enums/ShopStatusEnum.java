package shop.daijian.seller.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.daijian.common.support.IStatusEnum;

/**
 * 店铺状态枚举
 *
 * @author hanshizhpu
 * @since 2019-08-7
 */
@Getter
@AllArgsConstructor
public enum ShopStatusEnum implements IStatusEnum {

    SHOP_NOT_EXIST(500, "店铺不存在"),
    CREDIT_CHANGE_NOT_SUCCESS(500, "信誉分增减失败"),
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
