package shop.daijian.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import shop.daijian.common.enums.IdentityTypeEnum;
import shop.daijian.user.entity.AuthInfo;
import shop.daijian.user.entity.Jwt;
import shop.daijian.user.form.*;

/**
 * <p>
 * 认证信息服务类
 * </p>
 *
 * @author qiyubing
 * @since 2019-07-31
 */
public interface AuthInfoService extends IService<AuthInfo> {

    /**
     * 手机号+密码登录
     *
     * @param form 手机号+密码登录表单
     * @return 用户ID
     */
    String loginByPassword(PasswordLoginForm form);

    /**
     * 手机号+验证码登录
     *
     * @param form 手机号+验证码登录表单
     * @return 用户ID
     */
    String loginByVerifyCode(VerifyCodeLoginForm form);

    /**
     * 注册
     *
     * @param form 注册表单
     * @return 用户ID
     */
    String register(UserRegisterForm form);

    /**
     * 生成并保存Jwt（若有缓存则从缓存中获取）
     *
     * @param userId   用户ID
     * @param refresh 是否刷新缓存中Token
     * @return Jwt
     */
    Jwt createAndSaveToken(String userId, Boolean refresh);

    /**
     * 重置手机号
     *
     * @param userId 用户ID
     * @param form   重置手机号表单
     */
    void resetMobile(String userId, ResetMobileForm form);

    /**
     * 重置密码
     *
     * @param userId 用户ID
     * @param form   重置密码表单
     */
    void resetPassword(String userId, ResetPasswordForm form);

    AuthInfo getAuthInfoByUserId(String userId, IdentityTypeEnum identityTypeEnum);
}
