package shop.daijian.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Redis键模板枚举
 *
 * @author qiyubing
 * @since 2019-01-21
 */
@Getter
@AllArgsConstructor
public enum RedisKeyEnum {

    /**
     * 验证码 SmsTypeEnum, mobile
     */
    VERIFY_CODE("VERIFY_CODE_%s_%s"),

    /**
     * 验证码错误次数 SmsTypeEnum, mobile
     */
    VERIFY_CODE_ERROR_COUNT("VERIFY_CODE_ERROR_COUNT_%s_%s"),

    /**
     * 密码错误次数 username
     */
    PASSWORD_ERROR_COUNT("PASSWORD_ERROR_COUNT_%s"),

    /**
     * Token令牌 userId
     */
    TOKEN("TOKEN_%s"),

    /**
     * 购物车 userId
     */
    CART("CART_%s"),

    /**
     * 订单 userId orderId
     */
    ORDER("TRADE_%s_%s");

    /**
     * 模板文本
     */
    private String text;

    /**
     * 生成键
     *
     * @param args 参数
     * @return redis key
     */
    public String buildKey(Object... args) {
        return String.format(this.text, args);
    }

}
