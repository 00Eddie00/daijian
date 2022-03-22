package shop.daijian.trade.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 *订单
 *
 * @author guandongwei
 * 2019/8/4
 */
@Data
@Accessors(chain = true)
public class Order implements Serializable {

    private static final long serialVersionUID = -4279216513093425478L;

    @ApiModelProperty("订单信息")
    private OrderInfo orderInfo;

    @ApiModelProperty("订单商品列表")
    private List<OrderGoods> orderGoodsList;
}
