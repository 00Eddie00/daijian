package shop.daijian.common.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author liuxin
 * @since 2019/8/4 20:12
 */
@ApiModel(description = "商品简要信息")
@Data
@Accessors(chain = true)
public class GoodsBriefDTO implements Serializable {

    private static final long serialVersionUID = 6865573085058287520L;

    @ApiModelProperty("商品ID")
    private String goodsId;

    @ApiModelProperty("商品名")
    private String name;

    @ApiModelProperty("商品头像链接")
    private String avatarUrl;

    @ApiModelProperty("规格")
    private String specification;

    @ApiModelProperty("单价")
    private BigDecimal unitPrice;

    @ApiModelProperty("店铺ID")
    private String shopId;

    @ApiModelProperty("店铺名称")
    private String shopName;

    @ApiModelProperty("店铺头像链接")
    private String shopAvatarUrl;

    @ApiModelProperty("月销量")
    private Integer monthlySales;

    @ApiModelProperty("产地文本")
    private String originRegion;

    @ApiModelProperty("原价")
    private BigDecimal originPrice;

}
