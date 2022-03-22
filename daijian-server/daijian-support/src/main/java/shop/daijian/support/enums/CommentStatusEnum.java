package shop.daijian.support.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.daijian.common.support.IStatusEnum;

/**
 * @author liuxin
 * @since 2019/8/27 9:00
 */
@Getter
@AllArgsConstructor
public enum CommentStatusEnum implements IStatusEnum {

    COMMENT_ALREADY_EXIST(404, "不能重复评论");

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态解释
     */
    private String msg;

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}

