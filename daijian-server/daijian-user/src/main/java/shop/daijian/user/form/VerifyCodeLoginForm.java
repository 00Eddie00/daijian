package shop.daijian.user.form;

import lombok.Data;
import shop.daijian.common.constraints.Mobile;
import shop.daijian.common.constraints.VerifyCode;

/**
 * 手机号+验证码登录表单
 *
 * @author qiyubing
 * @since 2019-08-02
 */
@Data
public class VerifyCodeLoginForm {

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
}
