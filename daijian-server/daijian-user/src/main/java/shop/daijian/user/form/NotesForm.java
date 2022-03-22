package shop.daijian.user.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import shop.daijian.common.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author qiyubing
 * @since 2019-08-11
 */
@ApiModel(description = "用户手记表单")
@Data
public class NotesForm {

    @Length(min = 5, max = 40)
    @ApiModelProperty("标题")
    private String title;

    @URL
    @NotNull
    @ApiModelProperty("封面图链接")
    private String coverImageUrl;

    @NotBlank
    @ApiModelProperty("手记正文HTML代码")
    private String content;

    @ApiModelProperty("商品ID")
    @NotNull
    private String goodsId;

}
