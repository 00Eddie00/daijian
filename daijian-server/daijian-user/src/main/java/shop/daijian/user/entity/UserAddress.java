package shop.daijian.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import shop.daijian.common.support.BaseEntity;

/**
 * <p>
 * 用户收货地址
 * </p>
 *
 * @author suyutong
 * @since 2019-08-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("user_address")
public class UserAddress extends BaseEntity {

    /**
     * 用户收货地址ID
     */
    @TableId(value = "user_address_id", type = IdType.ID_WORKER_STR)
    private String userAddressId;

    /**
     * 用户ID
     */
    @TableField(value = "user_id",updateStrategy = FieldStrategy.IGNORED)
    private String userId;

    /**
     * 所在地区
     */
    @TableField("region")
    private String region;

    /**
     * 所在地区ID
     */
    @TableField("region_id")
    private String regionId;

    /**
     * 详细地址
     */
    @TableField("detail")
    private String detail;

    /**
     * 收货人姓名
     */
    @TableField("receiver")
    private String receiver;

    /**
     * 手机号
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 是否为默认
     */
    @TableField("is_default_address")
    private Boolean defaultAddress;

}
