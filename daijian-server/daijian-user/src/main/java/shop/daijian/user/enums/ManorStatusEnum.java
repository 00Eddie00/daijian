package shop.daijian.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.daijian.common.enums.IStatusEnum;

/**
 * 庄园状态枚举
 *
 * @author qiyubing
 * @since 2019-08-01
 */
@Getter
@AllArgsConstructor
public enum ManorStatusEnum implements IStatusEnum {

    MANOR_NOT_EXIST(500, "该用户未开通庄园");

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态解释
     */
    private String msg;

}
