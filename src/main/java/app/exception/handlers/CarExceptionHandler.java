package app.exception.handlers;

import app.controller.CarController;
import app.controller.SearchController;
import app.exception.declarations.car.CarNotFoundException;
import app.exception.declarations.common.AlreadyExistingResourceException;
import app.exception.declarations.common.ConflictException;
import app.exception.declarations.common.ServiceException;
import app.exception.declarations.user.AlreadyExistingEmailException;
import app.exception.declarations.user.EmailNotFoundException;
import app.exception.declarations.user.UserNotFoundException;
import app.exception.utils.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

import static app.util.Constants.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.ResponseEntity.status;

@ControllerAdvice(assignableTypes = CarController.class)
public class CarExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarExceptionHandler.class);

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleCarNotFoundException(CarNotFoundException exception) {
        LOGGER.error(NOT_FOUND_MESSAGE, exception);

        return status(NOT_FOUND).body(new ErrorMessage(exception.getMessage(), NOT_FOUND.value()));
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorMessage> handleServiceException(ServiceException exception) {
        LOGGER.error(DATABASE_ERROR_MESSAGE, exception);

        return status(INTERNAL_SERVER_ERROR).body(new ErrorMessage(exception.getMessage(), INTERNAL_SERVER_ERROR.value()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        LOGGER.error(BAD_REQUEST_MESSAGE, exception);

        String errors = exception.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));

        return status(BAD_REQUEST).body(new ErrorMessage(errors, BAD_REQUEST.value()));
    }

    @ExceptionHandler(AlreadyExistingResourceException.class)
    public ResponseEntity<ErrorMessage> handleAlreadyExistingResourceException(AlreadyExistingResourceException exception) {
        LOGGER.error(EXISTING_RESOURCE_MESSAGE, exception);

        return status(CONFLICT).body(new ErrorMessage(exception.getMessage(), CONFLICT.value()));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorMessage> handleConflictException(ConflictException exception) {
        LOGGER.error(EXISTING_RESOURCE_MESSAGE, exception);

        return status(CONFLICT).body(new ErrorMessage(exception.getMessage(), CONFLICT.value()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleUserNotFoundException(UserNotFoundException exception) {
        LOGGER.error(NOT_FOUND_MESSAGE, exception);

        return status(NOT_FOUND).body(new ErrorMessage(exception.getMessage(), NOT_FOUND.value()));
    }


}
