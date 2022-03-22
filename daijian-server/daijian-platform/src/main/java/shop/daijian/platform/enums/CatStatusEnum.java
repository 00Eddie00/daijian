package shop.daijian.platform.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.daijian.common.enums.IStatusEnum;


/**
 * 分类状态枚举类
 *
 * @author liuxin
 * @since 2019-08-04
 */
@Getter
@AllArgsConstructor
public enum CatStatusEnum implements IStatusEnum {

    CAT_NOT_EXIST(500, "分类不存在"),
     ;

    /**
     *
     * 状态码
     */
    private int code;

    /**
     * 状态解释
     */
    private String msg;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
