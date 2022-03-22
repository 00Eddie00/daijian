package shop.daijian.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.daijian.user.entity.AuthInfo;
import shop.daijian.user.entity.Jwt;
import shop.daijian.user.enums.AuthStatusEnum;
import shop.daijian.user.form.*;
import shop.daijian.user.repository.mybatis.AuthInfoMapper;
import shop.daijian.user.repository.redis.TokenTemplate;
import shop.daijian.common.enums.IdentityTypeEnum;
import shop.daijian.common.enums.SmsTypeEnum;
import shop.daijian.common.exception.BizException;
import shop.daijian.common.interfaces.SmsService;
import shop.daijian.user.service.AuthInfoService;
import shop.daijian.user.service.UserService;
import shop.daijian.user.util.JwtUtil;

/**
 * <p>
 * 认证信息服务实现类
 * </p>
 *
 * @author qiyubing
 * @author suyutong
 * @since 2019-07-31
 */
@Service
public class AuthInfoServiceImpl extends ServiceImpl<AuthInfoMapper, AuthInfo> implements AuthInfoService {

    @Autowired
    TokenTemplate tokenTemplate;

    @Reference
    private SmsService smsService;

    @Autowired
    private UserService userService;

    @Override
    public String loginByPassword(PasswordLoginForm form) {
        AuthInfo authInfo = getAuthInfo(form.getMobile(), IdentityTypeEnum.MOBILE);
        if (authInfo.getCredential().equals(form.getPassword())) {
            return authInfo.getUserId();
        }
        throw new BizException(AuthStatusEnum.AUTH_FAILURE);
    }

    @Override
    public String loginByVerifyCode(VerifyCodeLoginForm form) {
        // 校验验证码
        smsService.verifyAndDeleteCode(form.getMobile(), form.getVerifyCode(), SmsTypeEnum.LOGIN);
        AuthInfo authInfo = getAuthInfo(form.getMobile(), IdentityTypeEnum.MOBILE);
        return authInfo.getUserId();
    }

    @Override
    public String register(UserRegisterForm form) {
        // 验证是否被注册
        checkNotRegister(form.getMobile(), IdentityTypeEnum.MOBILE);
        String userId = IdWorker.getIdStr();
        smsService.verifyAndDeleteCode(form.getMobile(), form.getVerifyCode(), SmsTypeEnum.REGISTER);
        // 创建用户信息
        userService.createNewUser(userId);
        // 创建认证信息
        AuthInfo authInfo = new AuthInfo()
                .setUserId(userId)
                .setIdentifier(form.getMobile())
                .setCredential(form.getPassword())
                .setIdentityTypeEnum(IdentityTypeEnum.MOBILE);
        save(authInfo);
        return authInfo.getUserId();
    }

    @Override
    public Jwt createAndSaveToken(String userId, Boolean refresh) {
        Jwt jwt = null;
        // 若不刷新，从缓存中获取
        if (!refresh) {
            jwt = tokenTemplate.get(userId);
        }
        // 若需要刷新或者缓存中没有jwt，则重新创建
        if (jwt == null) {
            jwt = JwtUtil.create(userId);
        }
        // 保存token到缓存
        tokenTemplate.set(userId, jwt);
        return jwt;
    }

    @Override
    public void resetMobile(String userId, ResetMobileForm form) {
        //查找出改手机号的用户
        AuthInfo authInfo = getAuthInfoByUserId(userId, IdentityTypeEnum.MOBILE);
        //验证密码
        if (authInfo != null && authInfo.getCredential().equals(form.getPassword())) {
            //验证验证码
            smsService.verifyAndDeleteCode(form.getNewMobile(), form.getVerifyCode(), SmsTypeEnum.RESET_MOBILE);
            // 验证是否被注册
            checkNotRegister(form.getNewMobile(), IdentityTypeEnum.MOBILE);
            //更新手机号
            UpdateWrapper<AuthInfo> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("user_id", userId)
                    .set("identifier", form.getNewMobile());
            update(updateWrapper);
        } else {
            throw new BizException(AuthStatusEnum.AUTH_FAILURE);
        }
    }

    @Override
    public void resetPassword(String userId, ResetPasswordForm form) {
        //查出要更改密码的对象
        AuthInfo authInfo = getAuthInfoByUserId(userId, IdentityTypeEnum.MOBILE);
        //验证验证码
        smsService.verifyAndDeleteCode(form.getMobile(), form.getVerifyCode(), SmsTypeEnum.RESET_PASSWORD);
        //判断原密码与新密码是否相同
        if (form.getNewPassword().equals(authInfo.getCredential())) {
            throw new BizException(AuthStatusEnum.PASSWORD_SHOULD_DIFFERENT);
        }
        //更新密码
        UpdateWrapper<AuthInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", userId)
                .set("credential", form.getNewPassword());
        update(updateWrapper);
    }

    @Override
    public AuthInfo getAuthInfoByUserId(String userId, IdentityTypeEnum identityTypeEnum) {
        QueryWrapper<AuthInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("identity_type", identityTypeEnum);
        return getOne(queryWrapper);
    }

    /**
     * 获取认证信息
     */
    private AuthInfo getAuthInfo(String identifier, IdentityTypeEnum identityTypeEnum) {
        QueryWrapper<AuthInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("identifier", identifier).eq("identity_type", identityTypeEnum);
        AuthInfo authInfo = getOne(wrapper);
        if (authInfo == null) {
            throw new BizException(AuthStatusEnum.NOT_REGISTER);
        }
        return authInfo;
    }

    /**
     * 检查账号没有被注册
     */
    private void checkNotRegister(String identifier, IdentityTypeEnum identityTypeEnum) {
        QueryWrapper<AuthInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("identifier", identifier).eq("identity_type", identityTypeEnum);
        int count = count(wrapper);
        if (count != 0) {
            throw new BizException(AuthStatusEnum.MOBILE_ALREADY_REGISTER);
        }
    }

}
