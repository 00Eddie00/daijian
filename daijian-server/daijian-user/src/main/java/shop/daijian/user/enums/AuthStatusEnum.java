package shop.daijian.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.daijian.common.support.IStatusEnum;

/**
 * 认证状态枚举
 *
 * @author qiyubing
 * @since 2019-08-01
 */
@Getter
@AllArgsConstructor
public enum AuthStatusEnum implements IStatusEnum {

    NOT_REGISTER(500, "该账号尚未注册"),

    AUTH_FAILURE(500, "用户名或密码错误"),

    MOBILE_ALREADY_REGISTER(500, "该手机号已被注册"),

    PASSWORD_SHOULD_DIFFERENT(500, "两次输入密码不能相同");

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态解释
     */
    private String msg;

}
