package shop.daijian.common.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import shop.daijian.common.enums.BaseStatusEnum;
import shop.daijian.common.exception.BizException;

import java.util.Map;

/**
 * 请求工具类
 *
 * @author qiyubing
 * @since 2019-08-23
 */
@Slf4j
public class HttpUtils {

    /**
     * GET请求
     *
     * @param url           请求路径
     * @param parameterMap  请求参数
     * @param responseClazz 响应类型class
     */
    public static <T> T get(String url, Map<String, Object> parameterMap, Class<T> responseClazz) {
        String newUrl = addParameter(url, parameterMap);
        log.debug("GET URL : {}", newUrl);
        Request request = new Request.Builder().addHeader("Authorization", "token").url(newUrl).get().build();
        OkHttpClient client = new OkHttpClient();
        try {
            Response response = client.newCall(request).execute();
            if (response.body() == null) {
                return null;
            } else {
                String responseJson = response.body().string();
                log.debug("GET RESPONSE : {}", responseJson);
                return JsonUtils.toObject(responseJson, responseClazz);
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new BizException(BaseStatusEnum.HTTP_CALL);
        }
    }

    /**
     * 在URL后填充请求参数
     *
     * @param url       原路径
     * @param parameter 参数
     * @return xxx?k1=v2&k2=v2...
     */
    private static String addParameter(String url, Map<String, Object> parameter) {
        StringBuffer buffer = new StringBuffer(url);
        if (parameter != null) {
            buffer.append("?");
            parameter.forEach((k, v) -> {
                buffer.append(k).append("=").append(v).append("&");
            });
            buffer.replace(buffer.length() - 1, buffer.length(), "");
        }
        return buffer.toString();
    }

}
