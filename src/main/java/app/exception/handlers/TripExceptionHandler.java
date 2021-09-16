package app.exception.handlers;

import app.controller.TripController;
import app.exception.declarations.car.CarNotFoundException;
import app.exception.declarations.common.AlreadyExistingResourceException;
import app.exception.declarations.common.ConflictException;
import app.exception.declarations.common.InvalidDatePatternException;
import app.exception.declarations.common.ServiceException;
import app.exception.declarations.trip.*;
import app.exception.declarations.user.UserNotFoundException;
import app.exception.utils.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.util.stream.Collectors;

import static app.util.Constants.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;


@ControllerAdvice(assignableTypes = TripController.class)
public class TripExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TripExceptionHandler.class);

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorMessage> handleServiceException(ServiceException exception) {
        LOGGER.error(DATABASE_ERROR_MESSAGE, exception);

        return status(INTERNAL_SERVER_ERROR).body(new ErrorMessage(exception.getMessage(), INTERNAL_SERVER_ERROR.value()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleUserNotFoundException(UserNotFoundException exception) {
        LOGGER.error(NOT_FOUND_MESSAGE, exception);

        return status(NOT_FOUND).body(new ErrorMessage(exception.getMessage(), NOT_FOUND.value()));
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

    @ExceptionHandler(TripNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleResourceNotFoundException(TripNotFoundException exception) {
        LOGGER.error(NOT_FOUND_MESSAGE, exception);

        return status(NOT_FOUND).body(new ErrorMessage(exception.getMessage(), NOT_FOUND.value()));
    }

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleCarNotFoundException(CarNotFoundException exception) {
        LOGGER.error(NOT_FOUND_MESSAGE, exception);

        return status(NOT_FOUND).body(new ErrorMessage(exception.getMessage(), NOT_FOUND.value()));
    }

    @ExceptionHandler(DriverCannotBePassengerException.class)
    public ResponseEntity<ErrorMessage> handleDriverCannotBePassengerException(DriverCannotBePassengerException exception) {
        LOGGER.error(BAD_REQUEST_MESSAGE, exception);

        return status(BAD_REQUEST).body(new ErrorMessage(exception.getMessage(), BAD_REQUEST.value()));
    }

    @ExceptionHandler(NoMoreSeatsAvailableException.class)
    public ResponseEntity<ErrorMessage> handleNoMoreSeatsAvailableException(NoMoreSeatsAvailableException exception) {
        LOGGER.error(BAD_REQUEST_MESSAGE, exception);

        return status(BAD_REQUEST).body(new ErrorMessage(exception.getMessage(), BAD_REQUEST.value()));
    }

    @ExceptionHandler(PassengerAlreadyOnTheTripException.class)
    public ResponseEntity<ErrorMessage> handlePassengerAlreadyOnTheTripException(PassengerAlreadyOnTheTripException exception) {
        LOGGER.error(BAD_REQUEST_MESSAGE, exception);

        return status(BAD_REQUEST).body(new ErrorMessage(exception.getMessage(), BAD_REQUEST.value()));
    }

    @ExceptionHandler(InvalidDatePatternException.class)
    public ResponseEntity<ErrorMessage> handleInvalidDatePatternException(InvalidDatePatternException exception) {
        LOGGER.error(BAD_REQUEST_MESSAGE, exception);

        return status(BAD_REQUEST).body(new ErrorMessage(exception.getMessage(), BAD_REQUEST.value()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        LOGGER.error(BAD_REQUEST_MESSAGE, exception);

        return status(BAD_REQUEST).body(new ErrorMessage(exception.getMessage(), BAD_REQUEST.value()));
    }

    @ExceptionHandler(CannotUpdateTripWithPassengersException.class)
    public ResponseEntity<ErrorMessage> handleCannotUpdateWithPassengersException(CannotUpdateTripWithPassengersException exception) {
        LOGGER.error(BAD_REQUEST_MESSAGE, exception);

        return status(BAD_REQUEST).body(new ErrorMessage(exception.getMessage(), BAD_REQUEST.value()));
    }

}
