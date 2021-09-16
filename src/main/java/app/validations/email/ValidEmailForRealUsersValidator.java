package app.validations.email;

import app.dto.user.CreateUserDto;
import app.validations.email.ValidEmailForRealUsers;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidEmailForRealUsersValidator implements ConstraintValidator<ValidEmailForRealUsers, CreateUserDto> {


    @Override
    public void initialize(ValidEmailForRealUsers constraintAnnotation) {

    }

    @Override
    public boolean isValid(CreateUserDto createUserDto, ConstraintValidatorContext constraintValidatorContext) {

        return false;
//        if(Objects.isNull(createUserDto.getRole())){
//            return true;
//        }
//
//        if(createUserDto.getRole().equals(UserRole.BOT)) {
//            return createUserDto.getEmail().isBlank();
//        }else {
//            return !createUserDto.getEmail().isBlank();
//        }
    }
}
