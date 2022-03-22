package shop.daijian.user.form;

import lombok.Data;
import shop.daijian.common.constraints.Mobile;
import shop.daijian.common.constraints.Password;
import shop.daijian.common.constraints.VerifyCode;

/**
 * 用户注册表单
 *
 * @author qiyubing
 * @since 2019-07-31
 */
@Data
public class UserRegisterForm {

    /**
     * 手机号
     */
    @Mobile
    private String mobile;

    /**
     * 验证码
     */
    @VerifyCode
    private String verifyCode;

    /**
     * 密码
     */
    @Password
    private String password;

}
