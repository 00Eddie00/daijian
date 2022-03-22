package shop.daijian.seller.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import shop.daijian.common.support.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 商品信息
 * </p>
 *
 * @author liuxin
 * @since 2019-08-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("goods_info")
public class GoodsInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 4052885004717562815L;

    /**
     * 商品ID
     */
    @TableId(value = "goods_id", type = IdType.ID_WORKER_STR)
    private String goodsId;

    /**
     * 所属店铺id
     */
    @TableField("shop_id")
    private String shopId;

    /**
     * 店铺名
     */
    @TableField("shop_name")
    private String shopName;

    /**
     * 商品名
     */
    @TableField("name")
    private String name;

    /**
     * 商品头像链接
     */
    @TableField("avatar_url")
    private String avatarUrl;

    /**
     * 店铺头像链接
     */
    @TableField("shop_avatar_url")
    private String shopAvatarUrl;

    /**
     * 后台分类id
     */
    @TableField("cat_back_id")
    private String catBackId;

    /**
     * 产地地区ID
     */
    @TableField("origin_region_id")
    private String originRegionId;

    /**
     * 产地文本
     */
    @TableField("origin_region")
    private String originRegion;

    /**
     * 月销量
     */
    @TableField("monthly_sales")
    private Integer monthlySales;

    /**
     * 总销量
     */
    @TableField("total_sales")
    private Integer totalSales;

    /**
     * 规格
     */
    @TableField("specification")
    private String specification;

    /**
     * 单价
     */
    @TableField("unit_price")
    private BigDecimal unitPrice;

    /**
     * 原价
     */
    @TableField("origin_price")
    private BigDecimal originPrice;

    /**
     * 库存量
     */
    @TableField("stock")
    private Integer stock;

    /**
     * 好评率
     */
    @TableField("favorable_rate")
    private Integer favorableRate;

    /**
     * 轮播图链接的json数组
     */
    @TableField("image_url_json")
    private String imageUrlJson;

    /**
     * 商品介绍页链接
     */
    @TableField("content_url_json")
    private String contentUrlJson;

    /**
     * 评价数
     */
    @TableField("comment_num")
    private Integer commentNum;

}
