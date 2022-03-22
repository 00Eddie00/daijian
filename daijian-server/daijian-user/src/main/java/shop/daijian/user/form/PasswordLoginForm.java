package shop.daijian.user.form;

import lombok.Data;
import shop.daijian.common.constraints.Mobile;
import shop.daijian.common.constraints.Password;

/**
 * 手机号+密码登录表单
 *
 * @author qiyubing
 * @since 2019-08-02
 */
@Data
public class PasswordLoginForm {

    /**
     * 手机号
     */
    @Mobile
    private String mobile;

    /**
     * 密码
     */
    @Password
    private String password;
}
