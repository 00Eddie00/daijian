package shop.daijian.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author qiyubing
 * @since 2019-08-11
 */
@ApiModel(description = "手记简略信息")
@Data
@Accessors(chain = true)
public class NotesBriefDTO implements Serializable {

    private static final long serialVersionUID = 8169914374349792503L;

    @ApiModelProperty("手记ID")
    private String notesId;

    @ApiModelProperty("封面图")
    private String coverImageUrl;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("点赞数")
    private Integer likeNum;

    @ApiModelProperty("浏览量")
    private Integer viewNum;

    @ApiModelProperty("评论数")
    private Integer commentNum;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("庄园主用户ID")
    private String userId;

    @ApiModelProperty("庄园头像链接")
    private String manorAvatarUrl;

    @ApiModelProperty("庄园名")
    private String manorName;

}
