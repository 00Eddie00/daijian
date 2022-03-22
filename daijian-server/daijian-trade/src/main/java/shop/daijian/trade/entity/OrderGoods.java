package shop.daijian.trade.entity;

import com.baomidou.mybatisplus.annotation.*;
import shop.daijian.common.support.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import shop.daijian.trade.enums.OrderStateEnum;

import java.math.BigDecimal;

/**
 * <p>
 * 订单商品
 * </p>
 *
 * @author guandongwei
 * @since 2019-08-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("order_goods")
public class OrderGoods extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 订单商品id
     */
    @TableId(value = "order_goods_id", type = IdType.ID_WORKER_STR)
    private String orderGoodsId;

    /**
     * 订单id
     */
    @TableField("order_id")
    private String orderId;

    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 商品id
     */
    @TableField("goods_id")
    private String goodsId;


    /**
     * 商品数量
     */
    @TableField("num")
    private Integer num;

    /**
     * 商品单价
     */
    @TableField("unit_price")
    private BigDecimal unitPrice;

    /**
     * 订单状态
     */
    @TableField(value = "state")
    private OrderStateEnum state;

}
