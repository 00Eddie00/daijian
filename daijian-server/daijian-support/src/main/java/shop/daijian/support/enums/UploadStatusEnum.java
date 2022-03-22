package shop.daijian.support.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.daijian.common.support.IStatusEnum;

/**
 * 上传状态枚举
 *
 * @author liuin
 * @date 2019/8/12 21:26
 */
@Getter
@AllArgsConstructor
public enum UploadStatusEnum implements IStatusEnum {

    FILE_CAN_NOT_NULL(500, "文件不能为空"),

    ILLEGAL_SUFFIX(500, "不支持此种文件类型"),

    OVERSIZE_FILE(500, "文件过大"),

    FAIL_READ_FILE(500, "读取文件发生错误");

    /**
     * 状态码
     */
    private int code;

    /**
     * 状态码解释
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
