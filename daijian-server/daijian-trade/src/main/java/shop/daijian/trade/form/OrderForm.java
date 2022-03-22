package shop.daijian.trade.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import shop.daijian.trade.enums.PaymentTypeEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 由前端传给后台的订单信息
 *
 * @author guandongwei
 * 2019/8/4
 */
@ApiModel(description = "订单表单")
@Data
public class OrderForm {

    @ApiModelProperty(value = "用户收货地址ID")
    @NotEmpty
    private String userAddressId;

    @ApiModelProperty("支付类型")
    @NotNull
    private PaymentTypeEnum paymentTypeEnum;

    @ApiModelProperty("已经生成的订单id列表")
    @NotEmpty(message = "订单不能为空")
    private List<String> orderIdList;

}
