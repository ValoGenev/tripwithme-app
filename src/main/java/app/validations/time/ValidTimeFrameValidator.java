package app.validations.time;

import app.dto.trip.TripDto;
import app.validations.time.ValidTimeFrame;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidTimeFrameValidator implements ConstraintValidator<ValidTimeFrame, TripDto> {

    @Override
    public boolean isValid(TripDto tripDto, ConstraintValidatorContext constraintValidatorContext) {
        return tripDto.getStartTime().isBefore(tripDto.getEndTime());
    }
}
