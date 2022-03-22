package shop.daijian.common.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static shop.daijian.common.constant.RegexConstant.PASSWORD_REGEX;

/**
 * @author qiyubing
 * @since 2019-02-15
 */
@NotBlank(message = "密码不能为空")
@Pattern(regexp = PASSWORD_REGEX, message = "密码至少为6-20位字母数字组合")
@Constraint(validatedBy = {})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface Password {

    String message() default "密码应是6-20位字母数字组合";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
