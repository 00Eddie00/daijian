package shop.daijian.trade.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import shop.daijian.trade.enums.OrderStateEnum;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author guandongwei
 * 2019/8/8
 */
@ApiModel(description = "订单简略信息")
@Data
public class OrderBriefVO {

    @ApiModelProperty("订单id")
    private String orderId;

    @ApiModelProperty(value = "用户收货地址ID")
    private String userAddressId;

    @ApiModelProperty("店铺id")
    private String shopId;

    @ApiModelProperty("店铺名称")
    private String shopName;

    @ApiModelProperty("总价")
    private BigDecimal totalPrice;

    @ApiModelProperty("订单状态")
    private OrderStateEnum state;

    @ApiModelProperty("订单商品列表")
    private List<OrderGoodsVO> orderGoodsVOList;

}
