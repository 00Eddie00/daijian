package shop.daijian.common.util;

import shop.daijian.common.enums.ThreadLocalMapKeyEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 本地线程变量封装
 *
 * @author qiyubing
 * @since 2019-01-20
 */
public class ThreadLocalMap {

    /**
     * ThreadLocal Map类型上下文
     */
    private final static ThreadLocal<Map<ThreadLocalMapKeyEnum, Object>> THREAD_CONTEXT = new MapThreadLocal();

    /**
     * 添加
     *
     * @param key   键
     * @param value 值
     */
    public static void put(ThreadLocalMapKeyEnum key, Object value) {
        getContextMap().put(key, value);
    }

    /**
     * 获取
     *
     * @param key 键
     * @return 对应的值
     */
    public static Object get(ThreadLocalMapKeyEnum key) {
        return getContextMap().get(key);
    }

    /**
     * 删除
     *
     * @param key 键
     * @return 对应的值
     */
    public static Object remove(ThreadLocalMapKeyEnum key) {
        return getContextMap().remove(key);
    }

    /**
     * 获取thread context中的Map
     *
     * @return the Map
     */
    private static Map<ThreadLocalMapKeyEnum, Object> getContextMap() {
        return THREAD_CONTEXT.get();
    }

    /**
     * 释放本地线程变量
     */
    public static void release() {
        THREAD_CONTEXT.remove();
    }

    /**
     * 存放Map<ThreadLocalMapKeyEnum, Object>的ThreadLocal子类
     */
    private static class MapThreadLocal extends ThreadLocal<Map<ThreadLocalMapKeyEnum, Object>> {

        /**
         * 重写初始化
         *
         * @return MapThreadLocal
         */
        @Override
        protected Map<ThreadLocalMapKeyEnum, Object> initialValue() {
            return new HashMap<ThreadLocalMapKeyEnum, Object>(8);
        }
    }
}
