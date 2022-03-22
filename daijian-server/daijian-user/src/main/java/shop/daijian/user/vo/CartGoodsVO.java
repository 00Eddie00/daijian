package shop.daijian.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author hanshizhou
 * @since 2019/8/14
 **/
@Data
@ApiModel(description = "购物车商品")
@Accessors(chain = true)
public class CartGoodsVO {

    @ApiModelProperty("商品ID")
    private String goodsId;

    @ApiModelProperty("商品名")
    private String name;

    @ApiModelProperty("商品单价")
    private BigDecimal unitPrice;

    @ApiModelProperty("商品头像")
    private String avatarUrl;

    @ApiModelProperty("规格")
    private String specification;

    @ApiModelProperty("产地文本")
    private String originRegion;

    @ApiModelProperty("商品数量")
    private Integer goodsNum;

    @ApiModelProperty("原价")
    private BigDecimal originPrice;

}
