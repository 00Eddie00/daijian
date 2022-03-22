package shop.daijian.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.daijian.common.support.IStatusEnum;

/**
 * 手记状态枚举
 *
 * @author qiyubing
 * @since 2019-08-01
 */
@Getter
@AllArgsConstructor
public enum NotesStatusEnum implements IStatusEnum {

    NOTES_NOT_EXIST(500, "该手记已被删除");

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态解释
     */
    private String msg;

}
