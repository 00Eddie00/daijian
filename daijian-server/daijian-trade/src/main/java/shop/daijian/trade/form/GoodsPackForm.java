package shop.daijian.trade.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author guandongwei
 * @since 2019-08-04
 */
@ApiModel(description = "商品包裹表单")
@Data
public class GoodsPackForm implements Serializable {

    private static final long serialVersionUID = -8096861523940348930L;

    @ApiModelProperty("商品id")
    @NotNull
    private String goodsId;

    @ApiModelProperty("商品个数")
    @NotNull
    @Range(min = 1, max = 999)
    private Integer num;
}
