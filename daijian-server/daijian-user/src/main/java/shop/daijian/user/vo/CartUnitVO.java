package shop.daijian.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author hanshizhou
 * @since 2019/8/12
 **/
@Data
@ApiModel(description = "购物车列表单体")
@Accessors(chain = true)
public class CartUnitVO {

    @ApiModelProperty("店铺ID")
    private String shopId;

    @ApiModelProperty("店铺名")
    private String shopName;

    @ApiModelProperty("店铺头像链接")
    private String shopAvatarUrl;

    @ApiModelProperty("商品列表")
    private List<CartGoodsVO> cartGoodsVOList;

}
