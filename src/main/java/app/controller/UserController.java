package app.controller;

import app.dto.application.ApplicationAllPropertiesDto;
import app.dto.car.CarAllPropertiesDto;
import app.dto.notification.NotificationAllPropertiesDto;
import app.dto.rating.RatingAllPropertiesDto;
import app.dto.search.SearchAllPropertiesDto;
import app.dto.trip.TripAllPropertiesDto;
import app.dto.user.CreateBotUserDto;
import app.dto.user.CreateUserDto;
import app.dto.user.UserAllPropertiesDto;
import app.dto.user.UserDto;
import app.entity.UserEntity;
import app.service.user.IUserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

import static app.util.Constants.*;
import static java.lang.String.format;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/config/api/v1/users")
public class UserController {

    private static final Logger LOGGER = getLogger(UserController.class);
    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserAllPropertiesDto>> getAll() {
        LOGGER.info(GET_ALL_USERS_MESSAGE);
        return ok(userService.findAll());
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserAllPropertiesDto> getOne(@PathVariable("id") String id) {
        LOGGER.info(format(FIND_USER_BY_ID_MESSAGE, id));
        return ok(userService.findOneById(id));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserAllPropertiesDto> create(@Valid @RequestBody UserDto user) {
        LOGGER.info(format(CREATE_USER_MESSAGE, user.getFullName()));
        return status(CREATED).body(userService.create(user));
    }

    @PostMapping(value = "/bot",consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserAllPropertiesDto> createBotUser(@Valid @RequestBody CreateBotUserDto user) {
        LOGGER.info(format(CREATE_USER_BOT_MESSAGE, user.getFullName()));
        return status(CREATED).body(userService.createBot(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        LOGGER.info(format(DELETE_USER_BY_ID_MESSAGE, id));
        userService.delete(id);
        return status(NO_CONTENT).build();
    }

    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserAllPropertiesDto> update(@Valid @RequestBody UserDto user, @PathVariable("id") String id) {
        LOGGER.info(format(UPDATE_USER_BY_ID_MESSAGE,id));
        return ok(userService.update(user, id));
    }

    @GetMapping(value = "/{id}/cars", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CarAllPropertiesDto>> getCars(@PathVariable("id") String id) {
        LOGGER.info(format(GET_USER_CARS_MESSAGE, id));
        return ok(userService.getUserCars(id));
    }

    @GetMapping(value = "/{id}/applications", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ApplicationAllPropertiesDto>> getApplications(@PathVariable("id") String id) {
        LOGGER.info(format(GET_USER_APPLICATIONS_MESSAGE, id));
        return ok(userService.getUserApplications(id));
    }

    @GetMapping(value = "/{id}/trips/passenger", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TripAllPropertiesDto>> getTripsAsPassenger(@PathVariable("id") String id) {
        LOGGER.info(format(GET_USER_TRIPS_AS_PASSENGER_MESSAGE, id));
        return ok(userService.getUserTripsAsPassenger(id));
    }

    @GetMapping(value = "/{id}/trips/driver", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TripAllPropertiesDto>> getTripsAsDriver(@PathVariable("id") String id) {
        LOGGER.info(format(GET_USER_TRIPS_AS_DRIVER_MESSAGE, id));
        return ok(userService.getUserTripsAsDriver(id));
    }

    @GetMapping(value = "/{id}/ratings", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RatingAllPropertiesDto>> getRatings(@PathVariable("id") String id) {
        LOGGER.info(format(GET_USER_RATINGS_MESSAGE, id));
        return ok(userService.getUserRatings(id));
    }

    @GetMapping(value = "/{id}/notifications", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NotificationAllPropertiesDto>> getNotifications(@PathVariable("id") String id) {
        LOGGER.info(format(GET_USER_NOTIFICATIONS_MESSAGE, id));
        return ok(userService.getUserNotifications(id));
    }

    @GetMapping(value = "/{id}/searches", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SearchAllPropertiesDto>> getSearches(@PathVariable("id") String id) {
        LOGGER.info(format(GET_USER_SEARCHES_MESSAGE, id));
        return ok(userService.getUserSearches(id));
    }

}