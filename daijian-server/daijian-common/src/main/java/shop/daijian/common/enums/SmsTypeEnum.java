package shop.daijian.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 短信类型枚举
 *
 * @author qiyubing
 * @since 2019-07-26
 */
@Getter
@AllArgsConstructor
public enum SmsTypeEnum {

    /**
     * 注册验证码
     */
    REGISTER("SMS_184621746", "{\"code\":\"%s\"}"),

    /**
     * 登录验证码
     */
    LOGIN("SMS_184621749", "{\"code\":\"%s\"}"),

    /**
     * 重置密码验证码
     */
    RESET_PASSWORD("SMS_184626655", "{\"code\":\"%s\"}"),

    /**
     * 重置手机号给新手机发验证码
     */
    RESET_MOBILE("SMS_184626659", "{\"code\":\"%s\"}"),
    ;

    /**
     * 短信模版代码
     */
    private String templateCode;

    /**
     * 信息模版
     */
    private String msgTemplate;
}
