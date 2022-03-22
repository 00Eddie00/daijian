package shop.daijian.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import shop.daijian.common.enums.BaseStatusEnum;
import shop.daijian.common.exception.BizException;

import java.util.List;

/**
 * JSON转换工具类
 *
 * @author qiyubing
 * @since 2019-07-20
 */
public class JsonUtils {

    /**
     * 对象转为JSON字符串
     *
     * @param o 对象
     * @return JSON字符串
     */
    public static String toJson(Object o) {
        try {
            return o instanceof String ? o.toString() : JSON.toJSONString(o);
        } catch (Exception e) {
            throw new BizException(BaseStatusEnum.JSON_TRANS_ERROR);
        }
    }

    /**
     * JSON字符串转为对象
     *
     * @param json  JSON字符串
     * @param clazz 对象class类
     * @param <T>   对象类型
     * @return 对象
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        try {
            return clazz.equals(String.class) ? (T) json : JSON.parseObject(json, clazz);
        } catch (Exception e) {
            throw new BizException(BaseStatusEnum.JSON_TRANS_ERROR);
        }
    }

    /**
     * JSONArray字符串转为List
     *
     * @param jsonArray JSON字符串
     * @param clazz     List内对象类型
     * @param <T>       对象类型
     * @return 对象
     */
    public static <T> List<T> toList(String jsonArray, Class<T> clazz) {
        try {
            return JSONArray.parseArray(jsonArray, clazz);
        } catch (Exception e) {
            throw new BizException(BaseStatusEnum.JSON_TRANS_ERROR);
        }
    }

}
