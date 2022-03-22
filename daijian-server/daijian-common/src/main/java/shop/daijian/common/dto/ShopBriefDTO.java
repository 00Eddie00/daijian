package shop.daijian.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author hanshizhou
 * @since 2019-08-08
 */
@ApiModel(description = "查询店铺简要信息结果")
@Accessors(chain = true)
@Data
public class ShopBriefDTO implements Serializable {

    private static final long serialVersionUID = -515696431960759562L;

    @ApiModelProperty("店铺ID")
    private String shopId;

    @ApiModelProperty("店铺名")
    private String name;

    @ApiModelProperty("logo链接")
    private String avatarUrl;

    @ApiModelProperty("月销量")
    private Integer monthlySales;

    @ApiModelProperty("信用值")
    private Integer credit;


    @ApiModelProperty("粉丝数")
    private Integer fansNum;


    @ApiModelProperty("是否收藏")
    private Boolean favorite;

    @ApiModelProperty("商品列表")
    private List<GoodsBriefDTO> goodsBriefDTOList;

}
