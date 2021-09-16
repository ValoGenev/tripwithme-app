package app.validations.time;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidTimeFrameValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTimeFrame {

    String message() default "Start date must be set before end date"; //TODO add regex
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
