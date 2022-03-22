package shop.daijian.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import shop.daijian.common.dto.GoodsBriefDTO;

import java.time.LocalDateTime;

/**
 * @author qiyubing
 * @since 2019-08-11
 */
@ApiModel(description = "手记详情")
@Data
@Accessors(chain = true)
public class NotesDetailVO {

    @ApiModelProperty("用户手记ID")
    private String notesId;

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("封面图")
    private String coverImageUrl;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("手记内容")
    private String content;

    @ApiModelProperty("点赞数")
    private Integer likeNum;

    @ApiModelProperty("浏览量")
    private Integer viewNum;

    @ApiModelProperty("评论数")
    private Integer commentNum;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("庄园信息")
    private ManorDetailVO manorDetailVO;

    @ApiModelProperty("商品简略信息")
    private GoodsBriefDTO goodsBriefDTO;

    @ApiModelProperty("是否已点赞")
    private Boolean like;

}
