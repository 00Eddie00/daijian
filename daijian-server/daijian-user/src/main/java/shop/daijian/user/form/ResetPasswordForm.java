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
 * @author suyutong
 * @since 2019/8/12 9:54
 */
@ApiModel(description = "重置密码表单")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordForm {

    @Mobile
    @ApiModelProperty(value = "手机号")
    private String mobile;

    @VerifyCode
    @ApiModelProperty(value = "验证码")
    private String verifyCode;

    @Password
    @ApiModelProperty(value = "新密码")
    private String newPassword;
}
