package shop.daijian.trade.vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import shop.daijian.trade.enums.OrderStateEnum;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author guandongwei
 * 2019/8/6
 */
@ApiModel(description = "订单详细信息")
@Data
public class OrderDetailVO {

    @ApiModelProperty("订单id")
    private String orderId;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty(value = "用户收货地址ID")
    private String userAddressId;

    @ApiModelProperty("店铺id")
    private String shopId;

    @ApiModelProperty("店铺名称")
    private String shopName;

    @ApiModelProperty("店铺logo链接")
    private String shopLogoUrl;

    @ApiModelProperty("商品数量")
    private Integer num;

    @ApiModelProperty("总价")
    private BigDecimal totalPrice;

    @ApiModelProperty("支付类型")
    private String payType;

    @ApiModelProperty("交易流水号")
    private String tradeNum;

    @ApiModelProperty("快递单号")
    private String trackingNum;

    @ApiModelProperty("订单状态")
    private OrderStateEnum state;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("订单商品列表")
    private List<OrderGoodsVO> orderGoodsVOList;

}
