package shop.daijian.common.enums;

/**
 * 状态枚举接口
 *
 * @author qiyubing
 * @since 2019-01-20
 */
public interface IStatusEnum {

    /**
     * 返回状态码
     *
     * @return 状态码
     */
    Integer getCode();

    /**
     * 返回状态解释
     *
     * @return 状态解释
     */
    String getMsg();
}
