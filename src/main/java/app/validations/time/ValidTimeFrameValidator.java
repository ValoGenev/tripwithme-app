package app.validations.time;

import app.dto.common.SearchTripCommonProperties;
import app.dto.rating.RatingAllPropertiesDto;
import app.dto.trip.TripDto;
import app.validations.ratings.AssertDifferentUsers;
import app.validations.time.ValidTimeFrame;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidTimeFrameValidator implements ConstraintValidator<AssertDifferentUsers, RatingAllPropertiesDto>  {

    @Override
    public boolean isValid(RatingAllPropertiesDto ratingAllPropertiesDto, ConstraintValidatorContext constraintValidatorContext) {
        return !ratingAllPropertiesDto.getFromUser().getId().equals(ratingAllPropertiesDto.getToUser().getId());
    }
}
