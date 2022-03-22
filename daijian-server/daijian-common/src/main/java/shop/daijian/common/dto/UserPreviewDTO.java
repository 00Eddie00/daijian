package shop.daijian.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author qiyubing
 * @since 2019-08-26
 */
@ApiModel(description = "用户简略信息")
@Data
public class UserPreviewDTO implements Serializable {

    private static final long serialVersionUID = -93699479989625102L;

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("头像链接")
    private String avatarUrl;

    @ApiModelProperty("庄园关注数")
    private Integer followNum;

    @ApiModelProperty("商品收藏数")
    private Integer favoriteNum;

}
