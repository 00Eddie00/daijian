package shop.daijian.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author suyutong
 * @since 2019/8/5 9:26
 */
@Data
public class UserAddressDTO {

    /**
     * 用户收货地址ID
     */
    @ApiModelProperty(value = "用户收货地址ID")
    private String userAddressId;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private String userId;

    /**
     * 所在地区
     */
    @ApiModelProperty(value = "所在地区")
    private String region;

    /**
     * 所在地区ID
     */
    @ApiModelProperty(value = "所在地区ID")
    private String regionId;

    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址")
    private String detail;

    /**
     * 收货人姓名
     */
    @ApiModelProperty(value = "收货人姓名")
    private String receiver;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String mobile;

    /**
     * 是否为默认
     */
    @ApiModelProperty(value = "是否为默认")
    private Boolean defaultAddress;

}
