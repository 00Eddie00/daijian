package shop.daijian.trade.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import shop.daijian.trade.enums.OrderStateEnum;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author guandongwei
 * 2019/8/5
 */
@ApiModel(description = "订单商品展示")
@Data
public class OrderGoodsVO implements Serializable {

    private static final long serialVersionUID = 6870321824403532578L;

    @ApiModelProperty("订单商品id")
    private String orderGoodsId;

    @ApiModelProperty("商品id")
    private String goodsId;

    @ApiModelProperty("商品名")
    private String name;

    @ApiModelProperty("商品头像链接")
    private String avatarUrl;

    @ApiModelProperty("规格")
    private String specification;

    @ApiModelProperty("单价")
    private BigDecimal unitPrice;

    @ApiModelProperty("数量")
    private Integer num;

    @ApiModelProperty("总价")
    private BigDecimal totalPrice;

    @ApiModelProperty("状态")
    private OrderStateEnum state;

    @ApiModelProperty("产地文本")
    private String originRegion;

    @ApiModelProperty("原价")
    private BigDecimal originPrice;

}
