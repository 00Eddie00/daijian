package shop.daijian.trade.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import shop.daijian.common.support.BaseEntity;

/**
 * <p>
 * 支付信息表
 * </p>
 *
 * @author 关栋伟
 * @since 2019-08-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("payment_info")
public class PaymentInfo extends BaseEntity {


    /**
     * 编号
     */
    @TableId(value = "payment_id")
    private String paymentId;

    /**
     * 支付宝交易编号
     */
    @TableField("trade_no")
    private String tradeNo;

    /**
     * 支付金额
     */
    @TableField("total_price")
    private String totalPrice;

    /**
     * 交易内容
     */
    @TableField("order_id_list")
    private String orderIdList;


    /**
     * 支付类型
     */
    @TableField("type")
    private String type;


}
