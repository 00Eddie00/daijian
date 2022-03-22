package shop.daijian.common.support;

import org.springframework.validation.BindingResult;
import shop.daijian.common.enums.BaseStatusEnum;
import shop.daijian.common.exception.BizException;

/**
 * Controller基类
 *
 * @author qiyubing
 * @since 2019-07-31
 */
public class BaseController {

    /**
     * 参数校验
     */
    protected void validateParams(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BizException(BaseStatusEnum.PARAM_VALID_ERROR, bindingResult.getFieldError().getDefaultMessage());
        }
    }

}
