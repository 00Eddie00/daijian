package shop.daijian.common.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品详细信息
 * 排序规则：综合、销量、好评率
 * 索引：商品名、产地、分类链
 *
 * @author liuxin
 * @date 2019/8/8 17:13
 */
@Data
@Accessors(chain = true)
public class GoodsSearchDTO implements Serializable {

    private static final long serialVersionUID = 8470156530736577709L;

    /**
     * 商品ID
     */
    private String goodsId;

    /**
     * 商品名
     */
    private String name;

    /**
     * 商品头像链接
     */
    private String avatarUrl;

    /**
     * 店铺名
     */
    private String shopName;

    /**
     * 月销量
     */
    private Integer monthlySales;

    /**
     * 规格
     */
    private String specification;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 原价
     */
    private BigDecimal originPrice;

    /**
     * 后台分类id
     */
    private String catBackId;

    /**
     * 产地文本
     */
    private String originRegion;

    /**
     * 好评率
     */
    private Integer favorableRate;

}
