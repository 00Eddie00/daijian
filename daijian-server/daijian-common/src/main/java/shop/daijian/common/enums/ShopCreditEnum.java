package shop.daijian.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 店铺加分项/减分项枚举
 *
 * @author hsz
 */

@Getter
@ToString
@AllArgsConstructor
public enum ShopCreditEnum {

    /**
     * 好评而增加信誉分1分
     */
    GOOD_REVIEWS_REWARD_FOR_SHOP(1, true),

    /**
     * 一笔订单成功完成时增加信誉分5分
     */
    ORDER_SUCCESS_REWARD_FOR_SHOP(5, true),

    /**
     * 差评而减少信誉分1分
     */
    BAD_REVIEWS_PUNISHMENT_FOR_SHOP(1, false);

    /**
     * 增加或减少的信用值
     */
    private int score;

    /**
     * 是否为正数
     */
    private boolean positive;
}
