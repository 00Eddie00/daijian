package shop.daijian.user.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author hanshizhou
 * @since 2019-08-15
 */
@ApiModel(description = "商品包裹表单")
@Data
public class GoodsPackForm implements Serializable {

    @ApiModelProperty("商品id")
    @NotNull
    private String goodsId;

    @ApiModelProperty("商品个数")
    @NotNull
    @Range(min = 1, max = 99, message = "商品数量应在1-99直接")
    private Integer num;

}
