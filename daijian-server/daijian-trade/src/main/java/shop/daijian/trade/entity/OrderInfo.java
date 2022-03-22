package shop.daijian.trade.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import shop.daijian.common.support.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import shop.daijian.trade.enums.OrderStateEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单信息
 * </p>
 *
 * @author 关栋伟
 * @since 2019-08-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("order_info")
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo extends BaseEntity implements Serializable {

    /**
     * 订单id
     */
    @TableId(value = "order_id", type = IdType.ID_WORKER_STR)
    private String orderId;

    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 用户收货地址ID
     */
    @TableField("user_address_id")
    private String userAddressId;

    /**
     * 店铺id
     */
    @TableField("shop_id")
    private String shopId;

    /**
     * 店铺名称
     */
    @TableField("shop_name")
    private String shopName;

    /**
     * 店铺logo链接
     */
    @TableField("shop_logo_url")
    private String shopLogoUrl;

    /**
     * 商品数量
     */
    @TableField("num")
    private Integer num;

    /**
     * 总价
     */
    @TableField("total_price")
    private BigDecimal totalPrice;


    /**
     * 支付类型
     */
    @TableField("pay_type")
    private String payType;

    /**
     * 交易流水号
     */
    @TableField("trade_num")
    private String tradeNum;

    /**
     * 快递单号
     */
    @TableField("tracking_num")
    private String trackingNum;

    /**
     * 订单状态
     */
    @TableField("state")
    private OrderStateEnum state;

    /**
     * 是否被逻辑删除
     */
    @TableLogic
    @TableField("is_deleted")
    private Boolean deleted;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
