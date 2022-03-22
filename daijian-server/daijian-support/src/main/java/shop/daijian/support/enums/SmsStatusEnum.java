package shop.daijian.support.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.daijian.common.support.IStatusEnum;

/**
 * 短信服务状态枚举
 *
 * @author qiyubing
 * @since 2019-07-26
 */
@Getter
@AllArgsConstructor
public enum SmsStatusEnum implements IStatusEnum {

    MOBILE_NOT_REGISTER(500, "该手机号尚未注册"),

    LESS_THAN_ONE_MINUTE(500, "已发送验证码"),

    MOBILE_ALREADY_REGISTER(500, "该手机号已被注册"),

    SEND_ERROR(500, "验证码发送失败"),

    TOO_FREQUENTLY(500, "发送过于频繁，请稍后重试"),

    VERIFY_CODE_ERROR(500, "验证码错误");

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态解释
     */
    private String msg;

}
