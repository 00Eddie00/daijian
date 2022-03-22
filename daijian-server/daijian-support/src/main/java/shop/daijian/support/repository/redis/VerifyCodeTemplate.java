package shop.daijian.support.repository.redis;


import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import shop.daijian.common.support.BaseRedisTemplate;
import shop.daijian.common.enums.RedisKeyEnum;
import shop.daijian.common.enums.SmsTypeEnum;

import java.util.concurrent.TimeUnit;

/**
 * 验证码Redis数据访问层
 *
 * @author qiyubing
 * @since 2019-07-26
 */
@Repository
@AllArgsConstructor
public class VerifyCodeTemplate {

    @Autowired
    private BaseRedisTemplate<String> template;

    public VerifyCodeTemplate() {}

    private static final Long EXPIRE = 5L;

    private static final TimeUnit TIME_UNIT = TimeUnit.MINUTES;

    public Boolean exist(String mobile, SmsTypeEnum smsTypeEnum) {
        return template.exist(RedisKeyEnum.VERIFY_CODE.buildKey(smsTypeEnum.name(), mobile));
    }

    public void set(String mobile, SmsTypeEnum smsTypeEnum, String verifyCode) {
            template.set(RedisKeyEnum.VERIFY_CODE.buildKey(smsTypeEnum.name(), mobile),
                    verifyCode, EXPIRE, TIME_UNIT);
    }

    public String get(String mobile, SmsTypeEnum smsTypeEnum) {
        return template.get(RedisKeyEnum.VERIFY_CODE.buildKey(smsTypeEnum.name(), mobile), String.class);
    }

    public Boolean delete(String mobile, SmsTypeEnum smsTypeEnum) {
        return template.delete(RedisKeyEnum.VERIFY_CODE.buildKey(smsTypeEnum.name(), mobile));
    }

}
