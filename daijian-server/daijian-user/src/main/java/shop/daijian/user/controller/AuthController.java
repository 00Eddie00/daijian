package shop.daijian.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.daijian.common.support.BaseController;
import shop.daijian.common.dto.UserBriefDTO;
import shop.daijian.common.enums.IdentityTypeEnum;
import shop.daijian.common.interfaces.SmsService;
import shop.daijian.common.util.MobileUtils;
import shop.daijian.common.wrapper.ResultWrapper;
import shop.daijian.user.entity.AuthInfo;
import shop.daijian.user.entity.Jwt;
import shop.daijian.user.form.*;
import shop.daijian.user.service.AuthInfoService;
import shop.daijian.user.service.UserService;
import shop.daijian.user.vo.LoginVO;

import javax.validation.Valid;

/**
 * @author qiyubing
 * @author suyutong
 * @since 2019-07-19
 */
@Api(tags = "认证接口")
@RestController
@RequestMapping("/")
public class AuthController extends BaseController {

    @Autowired
    private AuthInfoService authInfoService;

    @Autowired
    private UserService userService;

    @Reference
    private SmsService smsService;

    @ApiOperation("手机号+密码登录")
    @PostMapping("/login/password")
    public ResultWrapper<LoginVO> loginByPassword(@Valid @RequestBody PasswordLoginForm form, BindingResult bindingResult) {
        validateParams(bindingResult);
        String userId = authInfoService.loginByPassword(form);
        LoginVO loginVO = generateLoginVO(userId, false);
        return ResultWrapper.success(loginVO);
    }

    @ApiOperation("手机号+验证码登录")
    @PostMapping("/login/verify-code")
    public ResultWrapper<LoginVO> loginByVerifyCode(@Valid @RequestBody VerifyCodeLoginForm form, BindingResult bindingResult) {
        validateParams(bindingResult);
        String userId = authInfoService.loginByVerifyCode(form);
        LoginVO loginVO = generateLoginVO(userId, false);
        return ResultWrapper.success(loginVO);
    }

    @ApiOperation("刷新Token")
    @GetMapping("/refreshToken")
    public ResultWrapper<Jwt> refreshToken(@RequestHeader String userId) {
        return ResultWrapper.success(authInfoService.createAndSaveToken(userId, true));
    }

    @ApiOperation("注册")
    @PostMapping("/register")
    public ResultWrapper<LoginVO> register(@Valid @RequestBody UserRegisterForm form, BindingResult bindingResult) {
        validateParams(bindingResult);
        String userId = authInfoService.register(form);
        LoginVO loginVO = generateLoginVO(userId, true);
        return ResultWrapper.success(loginVO);
    }

    @ApiOperation("重置手机号")
    @PutMapping("/update/mobile")
    public ResultWrapper updateMobile(@RequestHeader String userId, @Valid @RequestBody ResetMobileForm resetMobileForm, BindingResult bindingResult) {
        validateParams(bindingResult);
        authInfoService.resetMobile(userId, resetMobileForm);
        return ResultWrapper.success();
    }

    @ApiOperation("重置密码")
    @PutMapping("/update/password")
    public ResultWrapper<LoginVO> updatePassword(@RequestHeader String userId, @Valid @RequestBody ResetPasswordForm resetPasswordForm, BindingResult bindingResult) {
        validateParams(bindingResult);
        authInfoService.resetPassword(userId, resetPasswordForm);
        LoginVO loginVO = generateLoginVO(userId, false);
        return ResultWrapper.success(loginVO);
    }

    @ApiOperation("获取手机号")
    @GetMapping("/mobile")
    public ResultWrapper<String> getMobile(@RequestHeader String userId) {
        AuthInfo authInfo = authInfoService.getAuthInfoByUserId(userId, IdentityTypeEnum.MOBILE);
        String mobile = authInfo.getIdentifier();
        return ResultWrapper.success(MobileUtils.encryptMobile(mobile));
    }

    /**
     * 生成登录信息
     *
     * @param userId  用户ID
     * @param refresh 是否刷新缓存中Token
     */
    private LoginVO generateLoginVO(String userId, Boolean refresh) {
        Jwt jwt = authInfoService.createAndSaveToken(userId, refresh);
        UserBriefDTO userBriefDTO = userService.getUserBrief(userId);
        LoginVO loginVO = new LoginVO();
        loginVO.setJwt(jwt);
        loginVO.setUserBriefDTO(userBriefDTO);
        return loginVO;
    }

}
