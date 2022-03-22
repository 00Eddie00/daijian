package shop.daijian.user.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author qiyubing
 * @since 2019-08-11
 */
@ApiModel(description = "庄园详情")
@Data
@Accessors(chain = true)
public class ManorDetailVO {

    @ApiModelProperty("庄园主用户ID")
    @JsonProperty("manorUserId")
    private String userId;

    @ApiModelProperty("庄园名")
    private String name;

    @ApiModelProperty("logo链接")
    private String avatarUrl;

    @ApiModelProperty("介绍")
    private String introduction;

    @ApiModelProperty("粉丝数")
    private Integer fansNum;

    @ApiModelProperty("手记总数")
    private Integer notesNum;

    @ApiModelProperty("人气")
    private Integer hotNum;

    @ApiModelProperty("是否关注")
    private Boolean follow;

}
