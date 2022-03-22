package shop.daijian.common.support;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import shop.daijian.common.util.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Redis数据访问层基类
 *
 * @author qiyubing
 * @since 2019-01-21
 */
@Slf4j
@Repository
@ConditionalOnBean(StringRedisTemplate.class)
@AllArgsConstructor
public class BaseRedisTemplate<T> {

    private StringRedisTemplate template;

    public Boolean exist(String key) {
        log.debug("BaseRedisTemplate <exist> --> key = {}", key);
        Boolean hasKey = template.hasKey(key);
        log.debug("BaseRedisTemplate <exist> --> hasKey = {}", hasKey);
        return hasKey;
    }

    public void set(String key, T value) {
        String redisValue = JsonUtils.toJson(value);
        log.debug("BaseRedisTemplate <set> --> key = {}, value = {}", key, redisValue);
        template.opsForValue().set(key, JsonUtils.toJson(value));
    }

    public void set(String key, T value, Long expire, TimeUnit unit) {
        String redisValue = JsonUtils.toJson(value);
        log.debug("BaseRedisTemplate <set timeout> --> key = {}, value = {}, expire = {}, unit = {}",
                key, redisValue, expire, unit.name());
        template.opsForValue().set(key, redisValue, expire, unit);
    }

    public T get(String key, Class<T> clazz) {
        log.debug("BaseRedisTemplate <get> --> key = {}", key);
        String value = template.opsForValue().get(key);
        log.debug("BaseRedisTemplate <get> --> value = {}", value);
        T t = value == null ? null : JsonUtils.toObject(value, clazz);
        return t;
    }

    /**
     * 多key获取
     *
     * @param keyList key列表
     * @param clazz   存储类型
     * @return 对象List，若空则返回空集合
     */
    public List<T> multiGet(List<String> keyList, Class<T> clazz) {
        log.debug("BaseRedisTemplate <get> --> keyList = {}", keyList);
        List<String> stringList = template.opsForValue().multiGet(keyList);
        log.debug("BaseRedisTemplate <get> --> valueList = {}", stringList);
        System.out.println(stringList);
        // 为null 或包含null
        if (stringList == null || stringList.contains(null)) {
            return new ArrayList<>();
        }
        List<T> objectList = new ArrayList<>();
        for (String str : stringList) {
            T t = JsonUtils.toObject(str, clazz);
            objectList.add(t);
        }
        return objectList;
    }

    public Long delete(List<String> keyList) {
        log.debug("BaseRedisTemplate <delete> --> keyList = {}", keyList);
        Long affect = template.delete(keyList);
        log.debug("BaseRedisTemplate <delete> --> affect = {}", affect);
        return affect;
    }

    public Boolean delete(String key) {
        log.debug("BaseRedisTemplate <delete> --> key = {}", key);
        Boolean delete = template.delete(key);
        log.debug("BaseRedisTemplate <delete> --> delete = {}", delete);
        return delete;
    }

    public Long getExpire(String key, TimeUnit timeUnit) {
        log.debug("BaseRedisTemplate <getExpire> --> key = {}", key);
        Long expire = template.getExpire(key, timeUnit);
        log.debug("BaseRedisTemplate <getExpire> --> expire = {}", expire);
        return expire;
    }
}
