package shop.daijian.seller.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author qiyubing
 * @since 2019/8/13
 **/

@Data
@Accessors(chain = true)
@ApiModel(description = "店铺主页")
public class ShopDetailVO implements Serializable {

    private static final long serialVersionUID = 7378907354148933035L;

    @ApiModelProperty("店铺id")
    private String shopId;

    @ApiModelProperty("店铺名")
    private String name;

    @ApiModelProperty("logo链接")
    private String avatarUrl;

    @ApiModelProperty("所在地")
    private String region;

    @ApiModelProperty("简介")
    private String introduction;

    @ApiModelProperty("信用值")
    private Integer credit;

    @ApiModelProperty("月销量")
    private Integer monthlySales;

    @ApiModelProperty("粉丝数")
    private Integer fansNum;

    @ApiModelProperty("开店时间")
    private LocalDate createTime;

    @ApiModelProperty("店铺介绍页链接")
    private String contentUrl;

    @ApiModelProperty("是否收藏")
    private Boolean favorite;

}
