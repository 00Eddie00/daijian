package shop.daijian.common.constraints;

import org.hibernate.validator.constraints.Length;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author qiyubing
 * @since 2019-02-15
 */
@NotBlank(message = "验证码不能为空")
@Length(min = 6, max = 6, message = "验证码长度为6位")
@Constraint(validatedBy = {})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface VerifyCode {

    String message() default "验证码格式错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
