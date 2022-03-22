package shop.daijian.common.hanlder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import shop.daijian.common.enums.BaseStatusEnum;
import shop.daijian.common.exception.BizException;
import shop.daijian.common.wrapper.ResultWrapper;

/**
 * 统一异常处理(进入控制器)
 *
 * @author qiyubing
 * @since 2018-10-16
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResultWrapper<?> exceptionHandler(Exception e) {

        logException(e);

        if (e instanceof BizException) {
            // 业务异常
            return ResultWrapper.failure(((BizException) e).getCode(), e.getMessage());
        } else if (e instanceof HttpMessageNotReadableException) {
            // 参数格式错误
            return ResultWrapper.failure(BaseStatusEnum.PARAM_FORMAT_ERROR);
        } else if (e instanceof MissingServletRequestParameterException) {
            // 缺少参数
            return ResultWrapper.failure(BaseStatusEnum.MISSING_PARAM, ((MissingServletRequestParameterException) e).getParameterName());
        } else if (e instanceof MissingRequestHeaderException && "userId".equals(((MissingRequestHeaderException) e).getHeaderName())) {
            // 缺少userId请求头
            return ResultWrapper.failure(BaseStatusEnum.TOKEN_ERROR);
        } else if (e instanceof MaxUploadSizeExceededException) {
            // 超出最大上传大小
            return ResultWrapper.failure(BaseStatusEnum.MAX_FILE_UPLOAD);
        } else {
            // 未知异常
            return ResultWrapper.failure(BaseStatusEnum.UN_KNOW_EXCEPTION.getCode(), BaseStatusEnum.UN_KNOW_EXCEPTION.getMsg());
        }
    }

    /**
     * 记录异常
     *
     * @param e 异常对象
     */
    private static void logException(Exception e) {
        String stackTrace = (e instanceof BizException ? "" : "\nStackTrace : " + getStackTrace(e));
        String sb = "\n**************** Exception ****************" +
                "\nclass : " + e.getClass() +
                "\nmessage : " + e.getMessage() +
                stackTrace +
                "\n*******************************************\n";
        log.warn(sb);
    }

    /**
     * 获取异常方法栈
     *
     * @param e 异常对象
     * @return 异常方法栈信息
     */
    private static String getStackTrace(Exception e) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement traceElement : e.getStackTrace()) {
            sb.append("\n\t").append(traceElement);
        }
        return sb.toString();
    }
}
