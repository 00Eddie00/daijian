package shop.daijian.common.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author qiyubing
 * @since 2019-07-25
 */
@ApiModel(description = "分页查询表单")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageForm implements Serializable {

    private static final long serialVersionUID = 5661414995233388414L;

    @ApiModelProperty("开始页数")
    @Min(value = 1, message = "请传入正确的page参数")
    @NotNull(message = "请传入page参数")
    private Integer page;

    @ApiModelProperty("分页大小")
    @Min(value = 1, message = "请传入正确的size参数")
    @NotNull(message = "请传入size参数")
    private Integer size;

}