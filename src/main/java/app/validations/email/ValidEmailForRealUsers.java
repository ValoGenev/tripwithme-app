package app.validations.email;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = ValidEmailForRealUsersValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmailForRealUsers {

    String message() default "Email should not be null or empty"; //TODO add regex
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};


}

