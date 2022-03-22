package shop.daijian.common.interfaces;

import shop.daijian.common.enums.IdentityTypeEnum;

/**
 * 认证服务
 *
 * @author qiyubing
 * @since 2019-08-01
 */
public interface AuthService {

    /**
     * 判断登录标识是否已存在
     *
     * @param identifier       标识
     * @param identityTypeEnum 登录类型
     */
    Boolean exist(String identifier, IdentityTypeEnum identityTypeEnum);

    /**
     * 验证Token并获取用户ID
     *
     * @param token Token
     * @return 用户ID
     */
    String verifyAndGetUserId(String token);
}
