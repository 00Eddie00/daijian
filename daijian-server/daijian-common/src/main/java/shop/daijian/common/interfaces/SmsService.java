package shop.daijian.common.interfaces;

import shop.daijian.common.enums.SmsTypeEnum;

/**
 * 短信服务
 *
 * @author qiyubing
 * @since 2019-07-26
 */
public interface SmsService {

    /**
     * 发送验证码
     *
     * @param mobile      手机号
     * @param smsTypeEnum 验证码类型
     */
    void sendVerifyCode(String mobile, SmsTypeEnum smsTypeEnum);

    /**
     * 校验验证码并删除
     *
     * @param mobile      手机号
     * @param verifyCode  验证码
     * @param smsTypeEnum 验证码类型
     */
    void verifyAndDeleteCode(String mobile, String verifyCode, SmsTypeEnum smsTypeEnum);

}
