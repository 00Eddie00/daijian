package shop.daijian.common.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 店铺搜索信息
 * 排序规则：综合、信用、销量
 * 索引：店铺名、所属地区
 *
 * @author qiyubing
 * @since 2019-08-13
 */
@Data
@Accessors(chain = true)
public class ShopSearchDTO implements Serializable {

    private static final long serialVersionUID = 6706838943442731723L;

    /**
     * 店铺id
     */
    private String shopId;

    /**
     * 店铺名
     */
    private String name;

    /**
     * logo链接
     */
    private String avatarUrl;

    /**
     * 所属地区文本
     */
    private String region;

    /**
     * 月销量
     */
    private Integer monthlySales;

    /**
     * 信用值
     */
    private Integer credit;

}
