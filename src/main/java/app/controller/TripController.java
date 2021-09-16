package app.controller;

import app.dto.message.MessageAllPropertiesDto;
import app.dto.trip.CreateTripDto;
import app.dto.trip.TripAllPropertiesDto;
import app.dto.trip.UpdateTripDto;
import app.dto.user.UserAllPropertiesDto;
import app.entity.TripEntity;
import app.service.trip.ITripService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static app.util.Constants.*;
import static java.lang.String.format;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/config/api/v1/trips")
public class TripController {

    private static final Logger LOGGER = getLogger(TripController.class);
    private final ITripService tripService;

    @Autowired
    public TripController(ITripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TripAllPropertiesDto>> getAll() {
        LOGGER.info(GET_ALL_TRIPS_MESSAGE);
        return ok(tripService.findAll());
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<TripAllPropertiesDto> getOne(@PathVariable("id") String id) {
        LOGGER.info(format(FIND_TRIP_BY_ID_MESSAGE, id));
        return ok(tripService.findOneById(id));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<TripAllPropertiesDto> create(@Valid @RequestBody CreateTripDto trip) {
        LOGGER.info(format(CREATE_TRIP_MESSAGE, trip.getDescription()));
        return status(CREATED).body(tripService.create(trip));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        LOGGER.info(format(DELETE_TRIP_BY_ID_MESSAGE, id));
        tripService.delete(id);
        return status(NO_CONTENT).build();
    }

    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<TripAllPropertiesDto> update(@Valid @RequestBody UpdateTripDto trip, @PathVariable("id") String id) {
        LOGGER.info(format(UPDATE_TRIP_BY_ID_MESSAGE,id));
        return ok(tripService.update(trip, id));
    }

    @GetMapping(value = "/{id}/passengers", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserAllPropertiesDto>> getPassengers(@PathVariable("id") String id) {
        LOGGER.info(format(GET_TRIP_PASSENGERS_MESSAGE, id));
        return ok(tripService.getPassengers(id));
    }

    @GetMapping(value = "/{id}/applicants", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserAllPropertiesDto>> getApplicants(@PathVariable("id") String id) {
        LOGGER.info(format(GET_TRIP_APPLICATIONS_MESSAGE, id));
        return ok(tripService.getApplicants(id));
    }

    @GetMapping(value = "/{id}/messages", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MessageAllPropertiesDto>> getMesssages(@PathVariable("id") String id) {
        LOGGER.info(format(GET_TRIP_MESSAGES_MESSAGE, id));
        return ok(tripService.getMessages(id));
    }

    @PatchMapping(value = "/{tripId}/add/{userId}")
    public ResponseEntity<Void> addPassenger(@PathVariable("tripId") String tripId, @PathVariable("userId") String userId) {
        LOGGER.info(format(ADD_PASSENGER_MESSAGE, tripId,userId));
        tripService.addPassenger(tripId,userId);
        return status(NO_CONTENT).build();
    }

    @PatchMapping(value = "/{tripId}/remove/{userId}")
    public ResponseEntity<List<MessageAllPropertiesDto>> removePassenger(@PathVariable("tripId") String tripId, @PathVariable("userId") String userId) {
        LOGGER.info(format(ADD_PASSENGER_MESSAGE, tripId,userId));
        tripService.removePassenger(tripId,userId);
        return status(NO_CONTENT).build();
    }


}
