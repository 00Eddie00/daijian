package shop.daijian.common.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import shop.daijian.common.support.IStatusEnum;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求结果包装类
 *
 * @author qiyubing
 * @since 2019-01-20
 */
@Data
@AllArgsConstructor
public class ResultWrapper<T> implements Serializable {

    private static final long serialVersionUID = 4602750558426910274L;

    public static final Integer CODE_SUCCESS = 200;

    public static final String MESSAGE_SUCCESS = "操作成功";

    public static final Integer CODE_FAILURE = 500;

    public static final String MESSAGE_FAILURE = "操作失败";

    private Integer code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    private String message;

    private ResultWrapper() {
    }

    public static <T> ResultWrapper<T> success() {
        return new ResultWrapper<>(CODE_SUCCESS, null, MESSAGE_SUCCESS);
    }

    public static <T> ResultWrapper<T> success(T data) {
        return new ResultWrapper<>(CODE_SUCCESS, data, MESSAGE_SUCCESS);
    }

    public static <T> ResultWrapper<Map<String, T>> success(String key, T value) {
        Map<String, T> data = new HashMap<>(1);
        data.put(key, value);
        return new ResultWrapper<>(CODE_SUCCESS, data, MESSAGE_SUCCESS);
    }

    public static <T> ResultWrapper<T> success(Integer code, String msg) {
        return new ResultWrapper<>(code, null, msg);
    }

    public static <T> ResultWrapper<T> failure() {
        return new ResultWrapper<>(CODE_FAILURE, null, MESSAGE_FAILURE);
    }

    public static <T> ResultWrapper<T> failure(String msg) {
        return new ResultWrapper<>(CODE_FAILURE, null, msg);
    }

    public static <T> ResultWrapper<T> failure(Integer code, String msg) {
        return new ResultWrapper<>(code, null, msg);
    }

    public static <T> ResultWrapper<T> failure(IStatusEnum statusEnum, Object... args) {
        return new ResultWrapper<>(statusEnum.getCode(), null, String.format(statusEnum.getMsg(), args));
    }

    @JsonIgnore
    public Boolean isSuccess() {
        return CODE_SUCCESS.equals(this.code);
    }
}
