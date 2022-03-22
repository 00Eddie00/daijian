package shop.daijian.seller.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import shop.daijian.common.support.BaseEntity;

import java.io.Serializable;

/**
 * <p>
 * 店铺信息
 * </p>
 *
 * @author hsz
 * @since 2019-08-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("shop_info")
public class ShopInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -1213877818424968192L;

    /**
     * 店铺id
     */
    @TableId(value = "shop_id", type = IdType.ID_WORKER_STR)
    private String shopId;

    /**
     * 店铺名
     */
    @TableField("name")
    private String name;

    /**
     * logo链接
     */
    @TableField("avatar_url")
    private String avatarUrl;

    /**
     * 简介
     */
    @TableField("introduction")
    private String introduction;

    /**
     * 所属地区ID
     */
    @TableField("region_id")
    private String regionId;

    /**
     * 所属地区文本
     */
    @TableField("region")
    private String region;

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
     * 信用值
     */
    @TableField("credit")
    private Integer credit;

    /**
     * 粉丝数
     */
    @TableField("fans_num")
    private Integer fansNum;

    /**
     * 店铺介绍页链接
     */
    @TableField("content_url")
    private String contentUrl;

}
