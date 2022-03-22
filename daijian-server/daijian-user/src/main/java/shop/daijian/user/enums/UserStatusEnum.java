package shop.daijian.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.daijian.common.support.IStatusEnum;

/**
 * 用户状态枚举
 *
 * @author qiyubing
 * @since 2019-08-01
 */
@Getter
@AllArgsConstructor
public enum UserStatusEnum implements IStatusEnum {

    USER_NOT_EXIST(500, "用户不存在"),

    ADDRESS_NOT_EXIST(500, "用户收货地址不存在");

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态解释
     */
    private String msg;

}
