package app.validations.route;

import app.dto.trip.TripDto;
import app.dto.user.CreateUserDto;
import app.model.City;
import app.validations.email.ValidEmailForRealUsers;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ValidFullRouteValidator implements ConstraintValidator<ValidFullRoute, TripDto> {


    @Override
    public boolean isValid(TripDto tripDto, ConstraintValidatorContext constraintValidatorContext) {

        List<City> fullRoute = tripDto.getFullRoute();

        if(fullRoute.size() < 2){
            return false;
        }else{
            return fullRoute.get(0).equals(tripDto.getRouteStartingPoint())
                    && fullRoute.get(fullRoute.size() - 1).equals(tripDto.getRouteEndPoint())
                    && fullRoute.stream().filter(s->s.equals(tripDto.getRouteStartingPoint())).count() == 1
                    && fullRoute.stream().filter(s->s.equals(tripDto.getRouteEndPoint())).count() == 1;
        }
    }
}