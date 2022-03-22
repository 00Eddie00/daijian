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
@NotBlank(message = "姓名不能为空")
@Length(min = 2, max = 20, message = "姓名应在2-20位之间")
@Constraint(validatedBy = {})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface Name {

    String message() default "姓名格式错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
