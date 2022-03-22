package shop.daijian.common.hanlder;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.daijian.common.wrapper.ResultWrapper;

/**
 * 请求错误处理器(不进入控制器)
 *
 * @author qiyubing
 * @since 2019-01-23
 */
@RestController
@RequestMapping(value = "/error")
public class ErrorHandler implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping
    public ResultWrapper error() {
        return ResultWrapper.failure(404, "该路径不存在");
    }
}
