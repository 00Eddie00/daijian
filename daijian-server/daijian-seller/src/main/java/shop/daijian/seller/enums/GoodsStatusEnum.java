package shop.daijian.seller.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.daijian.common.support.IStatusEnum;

/**
 * 商品状态枚举类
 *
 * @author liuxin
 * @since 2019-08-04
 */
@Getter
@AllArgsConstructor
public enum GoodsStatusEnum implements IStatusEnum {

    NO_STOCK(500, "商品库存不足"),

    NOT_EXIST(404, "商品已下架或不存在");

    /**
     * 状态码
     */
    private int code;

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
