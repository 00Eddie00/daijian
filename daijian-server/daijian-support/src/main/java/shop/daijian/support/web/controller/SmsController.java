package shop.daijian.support.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import shop.daijian.common.interfaces.SmsService;
import shop.daijian.common.support.BaseController;
import shop.daijian.common.wrapper.ResultWrapper;

import shop.daijian.support.form.VerifyCodeForm;

import javax.validation.Valid;

/**
 * @author qiyubing
 * @since 2019-07-26
 */
@Api(tags = "短信服务接口")
@RestController
@RequestMapping("/")
public class SmsController extends BaseController {

    @Autowired
    private SmsService smsService;

    @ApiOperation("发送验证码")
    @PostMapping("/verify-code")
    public ResultWrapper sendVerifyCode(@Valid @RequestBody VerifyCodeForm verifyCodeForm, BindingResult bindingResult) {
        validateParams(bindingResult);
        // TODO 如果为注册验证码，验证是否已注册
        smsService.sendVerifyCode(verifyCodeForm.getMobile(), verifyCodeForm.getSmsTypeEnum());
        return ResultWrapper.success();
    }

}
