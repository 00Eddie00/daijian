package shop.daijian.user.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import shop.daijian.common.constraints.Mobile;
import shop.daijian.common.constraints.Name;

import javax.validation.constraints.NotNull;

/**
 * @author suyutong
 * @since 2019/8/8 16:55
 */
@ApiModel(description = "用户地址表单")
@Data
public class UserAddressForm {


    /**
     * 所在地区ID
     */
    @ApiModelProperty(value = "所在地区ID")
    private String regionId;

    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址")
    @Length(max = 255, min = 5, message = "详细地址应在5到255位之内")
    private String detail;

    /**
     * 收货人姓名
     */
    @Name
    @ApiModelProperty(value = "收货人姓名")
    private String receiver;

    /**
     * 手机号
     */
    @Mobile
    @ApiModelProperty(value = "手机号")
    private String mobile;

    /**
     * 是否为默认
     */
    @NotNull
    @ApiModelProperty(value = "是否为默认")
    private Boolean defaultAddress;

}
