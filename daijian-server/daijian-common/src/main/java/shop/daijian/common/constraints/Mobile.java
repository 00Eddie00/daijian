package shop.daijian.common.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static shop.daijian.common.constant.RegexConstant.MOBILE_REGEX;

/**
 * @author qiyubing
 * @since 2019-02-15
 */
@NotBlank(message = "手机号不能为空")
@Pattern(regexp = MOBILE_REGEX, message = "请输入正确的手机号")
@Constraint(validatedBy = {})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface Mobile {

    String message() default "手机号格式错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
