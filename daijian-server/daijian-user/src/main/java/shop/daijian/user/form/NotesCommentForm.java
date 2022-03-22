package shop.daijian.user.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author liuxin
 * @since 2019/8/26 20:55
 */
@ApiModel(description = "手记评论表单")
@Data
public class NotesCommentForm {

    @ApiModelProperty("手记Id")
    @NotBlank
    private String notesId;


    @ApiModelProperty("评论文本")
    @Length(max = 225, message = "文本长度应小于225位")
    private String content;
}



