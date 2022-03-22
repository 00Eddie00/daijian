package shop.daijian.user.util;




import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import shop.daijian.common.enums.BaseStatusEnum;
import shop.daijian.common.exception.BizException;
import shop.daijian.user.entity.Jwt;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * JWT工具类
 *
 * @author qiyubing
 * @since 2019/07/19
 */
public class JwtUtil {

    /**
     * 私钥
     */
    private static final String SECRET_KEY = "xK1z?#65!d.2&2bd";

    /**
     * Token存活时间
     */
    private static final long TTL = 30;

    /**
     * 时间单位
     */
    private static final TimeUnit TIME_UNIT = TimeUnit.DAYS;

    /**
     * 创建Token
     *
     * @param userId 用户ID
     * @return Token传输类
     */
    public static Jwt create(String userId) {
        long expiresAt = System.currentTimeMillis() + TIME_UNIT.toMillis(TTL);
        String token = JWT.create()
                .withClaim("userId", userId)
                .withExpiresAt(new Date(expiresAt))
                .sign(Algorithm.HMAC256(SECRET_KEY));
        return new Jwt(token, expiresAt);
    }

    /**
     * 验证并获取token中存储的userId
     *
     * @param token JWT Token
     * @return 用户id
     */
    public static String verifyAndGetUserId(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt;
        try {
            jwt = verifier.verify(token);
        } catch (Exception e) {
            throw new BizException(BaseStatusEnum.TOKEN_ERROR);
        }
        return jwt.getClaim("userId").asString();
    }

}