package shop.daijian.trade.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author liuxin
 * @since 2019/8/14
 */
@ApiModel(description = "商品评论表单")
@Data
public class GoodsCommentForm {

    @ApiModelProperty("订单商品Id")
    @NotBlank
    private String orderGoodsId;

    @ApiModelProperty("星级")
    @NotNull
    @Range(min = 1, max = 5)
    private Integer star;

    @ApiModelProperty("评论文本")
    @Length(max = 225, message = "文本长度应小于225位")
    private String content;

    @ApiModelProperty("配图链接列表")
    private List<String> imageUrlList;
}



