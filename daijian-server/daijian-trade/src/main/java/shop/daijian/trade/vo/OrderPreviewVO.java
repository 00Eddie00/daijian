package shop.daijian.trade.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author guandongwei
 * 2019/8/5
 */
@ApiModel(description = "生成订单展示信息")
@Data
public class OrderPreviewVO {

    @ApiModelProperty("订单id")
    private String orderId;

    @ApiModelProperty("店铺id")
    private String shopId;

    @ApiModelProperty("店铺名称")
    private String shopName;

    @ApiModelProperty("店铺头像链接")
    private String shopAvatarUrl;

    @ApiModelProperty("商品数量")
    private Integer num;

    @ApiModelProperty("总价")
    private BigDecimal totalPrice ;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("订单商品列表")
    private List<OrderGoodsVO> orderGoodsVO;
}
