package shop.daijian.common.constraints;

import org.hibernate.validator.constraints.Length;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author qiyubing
 * @since 2019-02-15
 */
@Length(max = 1024, message = "链接长度应小于1024位")
@org.hibernate.validator.constraints.URL
@Constraint(validatedBy = {})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface URL {

    String message() default "链接格式错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
