package shop.daijian.support.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.daijian.common.support.IStatusEnum;

/**
 * 地区状态枚举类
 * @author liuin
 * @date 2019/8/12 21:26
 */
@Getter
@AllArgsConstructor
public enum RegionStatusEnum implements IStatusEnum {

    REGION_NOT_EXIST(404, "地区不存在");

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
