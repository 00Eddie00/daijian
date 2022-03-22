package shop.daijian.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author qiyubing
 * @since 2019-08-11
 */
@ApiModel(description = "庄园简略信息")
@Data
@Accessors(chain = true)
public class ManorBriefDTO {

    @ApiModelProperty("庄园主用户ID")
    private String userId;

    @ApiModelProperty("庄园名")
    private String name;

    @ApiModelProperty("logo链接")
    private String avatarUrl;

    @ApiModelProperty("粉丝数")
    private Integer fansNum;

    @ApiModelProperty("手记总数")
    private Integer notesNum;

    @ApiModelProperty("人气")
    private Integer hotNum;

    @ApiModelProperty("是否关注")
    private Boolean follow;

    @ApiModelProperty("手记预览列表")
    private List<NotesBriefDTO> notesBriefDTOList;

}
