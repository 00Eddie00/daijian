package shop.daijian.common.enums;

import lombok.AllArgsConstructor;
import shop.daijian.common.support.IStatusEnum;

/**
 * 基本错误枚举
 *
 * @author qiyubing
 * @since 2019-01-20
 */
@AllArgsConstructor
public enum BaseStatusEnum implements IStatusEnum {

    UN_KNOW_EXCEPTION(500, "系统繁忙，请稍后重试"),

    JSON_TRANS_ERROR(1000, "JSON转换出错"),

    PARAM_VALID_ERROR(400, "%s"),

    MISSING_PARAM(400, "缺少参数:%s"),

    PARAM_FORMAT_ERROR(400, "参数格式错误"),

    BEAN_TARNS_ERROR(1000, "对象转换出错"),

    TOKEN_ERROR(401, "登录已过期"),

    NO_PERMISSION(500, "没有权限"),

    HTTP_CALL(500, "请求发生错误"),

    MAX_FILE_UPLOAD(500, "超出最大上传大小");

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误信息
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
