package app.validations.ratings;

import app.validations.time.ValidTimeFrameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidTimeFrameValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface AssertDifferentUsers {

    String message() default "fromUser and toUser should be different users"; //TODO add regex
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
