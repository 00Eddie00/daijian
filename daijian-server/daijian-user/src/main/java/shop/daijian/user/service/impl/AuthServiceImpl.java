package shop.daijian.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import shop.daijian.user.entity.AuthInfo;
import shop.daijian.user.entity.Jwt;
import shop.daijian.user.repository.redis.TokenTemplate;
import shop.daijian.user.service.AuthInfoService;
import shop.daijian.user.util.JwtUtil;
import shop.daijian.common.enums.BaseStatusEnum;
import shop.daijian.common.enums.IdentityTypeEnum;
import shop.daijian.common.exception.BizException;
import shop.daijian.common.interfaces.AuthService;

/**
 * 认证服务实现类
 *
 * @author qiyubing
 * @since 2019-08-01
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private TokenTemplate tokenTemplate;

    @Autowired
    private AuthInfoService authInfoService;

    @Override
    public Boolean exist(String identifier, IdentityTypeEnum identityTypeEnum) {
        QueryWrapper<AuthInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("identifier", identifier).eq("identity_type", identityTypeEnum);
        return authInfoService.count(wrapper) != 0;
    }

    @Override
    public String verifyAndGetUserId(String token) {
        String userId = JwtUtil.verifyAndGetUserId(token);
        Jwt jwt = tokenTemplate.get(userId);
        if (jwt != null && jwt.getToken().equals(token)) {
            return userId;
        }
        throw new BizException(BaseStatusEnum.TOKEN_ERROR);
    }
}
