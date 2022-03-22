package shop.daijian.support.service.impl;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import shop.daijian.common.constant.GlobalConstant;
import shop.daijian.common.enums.IdentityTypeEnum;
import shop.daijian.common.enums.SmsTypeEnum;
import shop.daijian.common.exception.BizException;
import shop.daijian.common.interfaces.AuthService;
import shop.daijian.common.interfaces.SmsService;
import shop.daijian.support.enums.SmsStatusEnum;
import shop.daijian.support.repository.redis.VerifyCodeTemplate;

import javax.annotation.Resources;
import java.util.Random;

/**
 * 短信服务实现类
 *
 * @author qiyubing
 * @since 2019-01-15
 */
@Slf4j
@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private IAcsClient acsClient;

    @Autowired
    private VerifyCodeTemplate verifyCodeTemplate;

    @Reference
    private AuthService authService;

    @Override
    public void sendVerifyCode(String mobile, SmsTypeEnum smsTypeEnum) {
        // 注册-已注册
        if (smsTypeEnum == SmsTypeEnum.REGISTER || smsTypeEnum == SmsTypeEnum.RESET_MOBILE) {
            Boolean exist = authService.exist(mobile, IdentityTypeEnum.MOBILE);
            if (exist) {
                throw new BizException(SmsStatusEnum.MOBILE_ALREADY_REGISTER);
            }
        }
        // 其他-未注册
        if (smsTypeEnum == SmsTypeEnum.LOGIN || smsTypeEnum == SmsTypeEnum.RESET_PASSWORD) {
            Boolean exist = authService.exist(mobile, IdentityTypeEnum.MOBILE);
            if (!exist) {
                throw new BizException(SmsStatusEnum.MOBILE_NOT_REGISTER);
            }
        }
        try {
            // 生成验证码
            String verifyCode = generateVerifyCode();
            verifyCodeTemplate.set(mobile, smsTypeEnum, verifyCode);
            //组装请求对象-具体描述见控制台-文档部分内容
            SendSmsRequest request = new SendSmsRequest();
            fillSmsRequest(request, mobile, verifyCode, smsTypeEnum);
            // TODO 解锁可以发出验证码
            // 此处可能会抛出异常，注意catch
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            if (!sendSmsResponse.getCode().equals(GlobalConstant.OK)) {
                throw new BizException(SmsStatusEnum.SEND_ERROR);
            }
            log.debug("手机号：{},验证码：{}", mobile, verifyCode);

        } catch (Exception e) {
            System.out.println(e);
            throw new BizException(SmsStatusEnum.TOO_FREQUENTLY);
        }
    }

    @Override
    public void verifyAndDeleteCode(String mobile, String verifyCode, SmsTypeEnum smsTypeEnum) {
        String inputCode = verifyCode;
//        String realVerifyCode = verifyCodeTemplate.get(mobile, smsTypeEnum);
        if (verifyCodeTemplate.get(mobile, smsTypeEnum).equals(inputCode)) {
            verifyCodeTemplate.delete(mobile, smsTypeEnum);
        }else {
            throw new BizException(SmsStatusEnum.VERIFY_CODE_ERROR);
        }
    }

    /**
     * 生成六位随机数验证码
     */
    private String generateVerifyCode() {
        Random random = new Random();
        int r = random.nextInt(999999);
        return Integer.toString((r > 100000 ? r : r + 100000));
    }

    /**
     * 填充阿里云短信接口请求
     */
    private void fillSmsRequest(SendSmsRequest request, String mobile, String verifyCode, SmsTypeEnum smsTypeEnum) {
        request.setPhoneNumbers(mobile);
        request.setSignName(GlobalConstant.PROJECT_NAME_CN);
        request.setTemplateCode(smsTypeEnum.getTemplateCode());
        request.setTemplateParam(String.format(smsTypeEnum.getMsgTemplate(), verifyCode));
    }
}
