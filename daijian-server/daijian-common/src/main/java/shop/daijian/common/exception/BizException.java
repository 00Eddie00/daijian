package shop.daijian.common.exception;

import lombok.Getter;
import shop.daijian.common.support.IStatusEnum;

/**
 * 业务基础异常
 *
 * @author qiyubing
 * @since 2019-01-20
 */
@Getter
public class BizException extends RuntimeException {

    private static final long serialVersionUID = -6677843589094887909L;

    protected Integer code = 500;

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(int code, String format, Object... args) {
        super(String.format(format, args));
        this.code = code;
    }

    public BizException(IStatusEnum statusEnum, Object... args) {
        this(statusEnum.getCode(), statusEnum.getMsg(), args);
    }
}
