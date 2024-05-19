package fit.nlu.cnpmbookshopweb.utils.annotation;

import jakarta.validation.Constraint;

import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
public @interface Email {
    String name() default "email";
    String regex();
    String message() default "{name} must match {regex}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
