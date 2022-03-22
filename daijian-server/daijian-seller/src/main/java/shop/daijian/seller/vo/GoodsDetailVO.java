package shop.daijian.seller.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import shop.daijian.common.dto.ShopBriefDTO;


import java.math.BigDecimal;
import java.util.List;

/**
 * @author liuxin
 * @date 2019/8/4 11:39
 */
@Data
@ApiModel(description = "获取商品详情")
@Accessors(chain = true)
public class GoodsDetailVO {

    @ApiModelProperty("商品ID")
    private String goodsId;

    @ApiModelProperty("商品名")
    private String name;

    @ApiModelProperty("商品头像链接")
    private String avatarUrl;

    @ApiModelProperty("产地地区")
    private String originRegion;

    @ApiModelProperty("月销量")
    private Integer monthlySales;

    @ApiModelProperty("规格")
    private String specification;

    @ApiModelProperty("单价")
    private BigDecimal unitPrice;

    @ApiModelProperty("原价")
    private BigDecimal originPrice;

    @ApiModelProperty("库存量")
    private Integer stock;

    @ApiModelProperty("好评率")
    private Integer favorableRate;

    @ApiModelProperty("评价数")
    private Integer commentNum;

    @ApiModelProperty("店铺信息")
    private ShopBriefDTO shopBriefDTO;

    @ApiModelProperty("轮播图链接的json数组")
    private List<String> imageUrlList;

    @ApiModelProperty("商品介绍图片链接的json数组")
    private List<String> contentUrlList;

    @ApiModelProperty("是否收藏")
    private Boolean favorite;

}



