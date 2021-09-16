package app.validations.route;

import app.validations.email.ValidEmailForRealUsersValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidFullRouteValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFullRoute {

    String message() default "Full route should contain routeStartingPoint at first index and routeEndPoint at last index and unique cities"; //TODO add regex
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};


}
