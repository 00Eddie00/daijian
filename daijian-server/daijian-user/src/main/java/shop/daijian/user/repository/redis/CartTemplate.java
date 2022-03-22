package shop.daijian.user.repository.redis;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import shop.daijian.user.dto.CartDTO;
import shop.daijian.common.enums.RedisKeyEnum;
import shop.daijian.common.support.BaseRedisTemplate;

/**
 * 对CartTemplate进行二次封装
 *
 * @author hanshizhou
 * @since 2019/8/13
 **/
@Slf4j
@Repository
@AllArgsConstructor
public class CartTemplate {

    @Autowired
    private BaseRedisTemplate<CartDTO> redisTemplate;

    /**
     * Redis Key 模板
     */
    private static final RedisKeyEnum TEMPLATE = RedisKeyEnum.CART;

    /**
     * 查询key是否存在
     * 我认为此方法可能不用写
     * @param userId 用户ID
     * @return TRUE-已存在 FALSE-不存在
     */
    public Boolean exist(String userId) {
        return redisTemplate.exist(RedisKeyEnum.CART.buildKey(userId));
    }

    /**
     * 存键值对，KEY值已存在会覆盖
     * @param userId 用户ID
     * @param cartDTO 购物车实体
     */
    public void set(String userId, CartDTO cartDTO) {
        redisTemplate.set(RedisKeyEnum.CART.buildKey(userId), cartDTO);
    }

    /**
     * 根据用户ID获取购物车实体
     * @param userId 用户ID
     * @return 购物车实体
     */
    public CartDTO get(String userId) {
        return redisTemplate.get(RedisKeyEnum.CART.buildKey(userId), CartDTO.class);
    }

    /**
     * 根据用户ID删除购物车
     * @param userId
     */
    public void delete(String userId) {
        redisTemplate.delete(RedisKeyEnum.CART.buildKey(userId));
    }

}
