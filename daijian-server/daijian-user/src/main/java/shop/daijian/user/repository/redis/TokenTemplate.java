package shop.daijian.user.repository.redis;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.daijian.common.enums.RedisKeyEnum;
import shop.daijian.common.support.BaseRedisTemplate;
import shop.daijian.user.entity.Jwt;

import java.util.concurrent.TimeUnit;

/**
 * Token Redis数据访问层
 *
 * @author qiyubing
 * @since 2019-07-31
 */
@Repository
@AllArgsConstructor
public class TokenTemplate {

    private BaseRedisTemplate<Jwt> template;

    private static final Long EXPIRE = 30L;

    private static final TimeUnit TIME_UNIT = TimeUnit.DAYS;

    public void set(String userId, Jwt token) {
        template.set(RedisKeyEnum.TOKEN.buildKey(userId),
                token, EXPIRE, TIME_UNIT);
    }

    public Jwt get(String userId) {
        return template.get(RedisKeyEnum.TOKEN.buildKey(userId), Jwt.class);
    }

    public Boolean delete(String userId) {
        return template.delete(RedisKeyEnum.TOKEN.buildKey(userId));
    }
}
