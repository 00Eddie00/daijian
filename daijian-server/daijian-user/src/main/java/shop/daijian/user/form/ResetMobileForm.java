package shop.daijian.user.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.daijian.common.constraints.Mobile;
import shop.daijian.common.constraints.Password;
import shop.daijian.common.constraints.VerifyCode;

/**
 *@author suyutong
 *@since 2019/8/12 9:52
 */
@ApiModel(description = "重置手机号表单")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetMobileForm {

    @Mobile
    @ApiModelProperty(value = "新手机号")
    private String newMobile;

    @VerifyCode
    @ApiModelProperty(value = "新手机号收到的验证码")
    private String verifyCode;

    @Password
    @ApiModelProperty(value = "密码")
    private String password;
}
