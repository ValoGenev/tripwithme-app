package app.controller;

import app.dto.notification.NotificationAllPropertiesDto;
import app.dto.rating.RatingAllPropertiesDto;
import app.service.notification.INotificationService;
import app.service.rating.IRatingService;
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
@RequestMapping("/config/api/v1/ratings")
public class RatingController {

    private static final Logger LOGGER = getLogger(RatingController.class);
    private final IRatingService ratingService;

    @Autowired
    public RatingController(IRatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RatingAllPropertiesDto>> getAll() {
        LOGGER.info(GET_ALL_RATINGS_MESSAGE);
        return ok(ratingService.findAll());
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RatingAllPropertiesDto> getOne(@PathVariable("id") String id) {
        LOGGER.info(format(FIND_RATING_BY_ID_MESSAGE, id));
        return ok(ratingService.findOneById(id));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RatingAllPropertiesDto> create(@Valid @RequestBody RatingAllPropertiesDto rating) {
        LOGGER.info(format(CREATE_RATING_MESSAGE, rating.getComment()));
        return status(CREATED).body(ratingService.create(rating));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        LOGGER.info(format(DELETE_RATING_BY_ID_MESSAGE, id));
        ratingService.delete(id);
        return status(NO_CONTENT).build();
    }

    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RatingAllPropertiesDto> update(@Valid @RequestBody RatingAllPropertiesDto rating, @PathVariable("id") String id) {
        LOGGER.info(format(UPDATE_RATING_BY_ID_MESSAGE,id));
        return ok(ratingService.update(rating, id));
    }
}
