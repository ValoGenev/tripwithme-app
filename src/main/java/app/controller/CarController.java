package app.controller;


import app.dto.car.CarAllPropertiesDto;

import app.service.car.ICarService;

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
@RequestMapping("/config/api/v1/cars")
public class CarController {

    private static final Logger LOGGER = getLogger(CarController.class);
    private final ICarService carService;

    @Autowired
    public CarController(ICarService carService) {
        this.carService = carService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CarAllPropertiesDto>> getAll() {
        LOGGER.info(GET_ALL_CARS_MESSAGE);
        return ok(carService.findAll());
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CarAllPropertiesDto> getOne(@PathVariable("id") String id) {
        LOGGER.info(format(FIND_CAR_BY_ID_MESSAGE, id));
        return ok(carService.findOneById(id));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CarAllPropertiesDto> create(@Valid @RequestBody CarAllPropertiesDto car) {
        LOGGER.info(format(CREATE_CAR_MESSAGE, car.getModel()));
        return status(CREATED).body(carService.create(car));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        LOGGER.info(format(DELETE_CAR_BY_ID_MESSAGE, id));
        carService.delete(id);
        return status(NO_CONTENT).build();
    }

    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CarAllPropertiesDto> update(@Valid @RequestBody CarAllPropertiesDto car, @PathVariable("id") String id) {
        LOGGER.info(format(UPDATE_CAR_BY_ID_MESSAGE,id));
        return ok(carService.update(car, id));
    }

}
