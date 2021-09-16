package app.controller;

import app.entity.SearchEntity;
import app.entity.TripEntity;
import app.service.search.ISearchService;
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
@RequestMapping("/config/api/v1/searches")
public class SearchController {

    private static final Logger LOGGER = getLogger(SearchController.class);
    private final ISearchService searchService;

    @Autowired
    public SearchController(ISearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SearchEntity>> getAll() {
        LOGGER.info(GET_ALL_SEARCHES_MESSAGE);
        return ok(searchService.findAll());
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<SearchEntity> getOne(@PathVariable("id") String id) {
        LOGGER.info(format(FIND_SEARCH_BY_ID_MESSAGE, id));
        return ok(searchService.findOneById(id));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<SearchEntity> create(@Valid @RequestBody SearchEntity search) {
        LOGGER.info(format(CREATE_SEARCH_MESSAGE, search.getDescription()));
        return status(CREATED).body(searchService.create(search));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        LOGGER.info(format(DELETE_SEARCH_BY_ID_MESSAGE, id));
        searchService.delete(id);
        return status(NO_CONTENT).build();
    }

    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<SearchEntity> update(@Valid @RequestBody SearchEntity search, @PathVariable("id") String id) {
        LOGGER.info(format(UPDATE_SEARCH_BY_ID_MESSAGE,id));
        return ok(searchService.update(search, id));
    }
}
