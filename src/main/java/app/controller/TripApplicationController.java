package app.controller;

import app.dto.application.ApplicationAllPropertiesDto;
import app.dto.trip.TripAllPropertiesDto;
import app.service.application.IApplicationService;
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
@RequestMapping("/config/api/v1/applications")
public class TripApplicationController {

    private static final Logger LOGGER = getLogger(TripApplicationController.class);
    private final IApplicationService applicationService;

    @Autowired
    public TripApplicationController(IApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ApplicationAllPropertiesDto>> getAll() {
        LOGGER.info(GET_ALL_TRIP_APPLICATIONS_MESSAGE);
        return ok(applicationService.findAll());
    }

    @GetMapping(value = "/{id}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ApplicationAllPropertiesDto> getOne(@PathVariable("id") String id) {
        LOGGER.info(format(FIND_TRIP_APPLICATION_BY_ID_MESSAGE, id));
        return ok(applicationService.findOneById(id));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ApplicationAllPropertiesDto> create(@Valid @RequestBody ApplicationAllPropertiesDto application) {
        LOGGER.info(format(CREATE_TRIP_APPLICATION_MESSAGE, application.getInformation()));
        return status(CREATED).body(applicationService.create(application));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        LOGGER.info(format(DELETE_TRIP_APPLICATION_BY_ID_MESSAGE, id));
        applicationService.delete(id);
        return status(NO_CONTENT).build();
    }

    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ApplicationAllPropertiesDto> update(@Valid @RequestBody ApplicationAllPropertiesDto application, @PathVariable("id") String id) {
        LOGGER.info(format(UPDATE_TRIP_APPLICATION_BY_ID_MESSAGE,id));
        return ok(applicationService.update(application, id));
    }
}
