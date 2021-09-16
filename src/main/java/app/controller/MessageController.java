package app.controller;

import app.dto.car.CarAllPropertiesDto;
import app.dto.message.MessageAllPropertiesDto;
import app.service.car.ICarService;
import app.service.message.IMessageService;
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
@RequestMapping("/config/api/v1/messages")
public class MessageController {

    private static final Logger LOGGER = getLogger(MessageController.class);
    private final IMessageService messageService;

    @Autowired
    public MessageController(IMessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MessageAllPropertiesDto>> getAll() {
        LOGGER.info(GET_ALL_MESSAGES_MESSAGE);
        return ok(messageService.findAll());
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageAllPropertiesDto> getOne(@PathVariable("id") String id) {
        LOGGER.info(format(FIND_MESSAGE_BY_ID_MESSAGE, id));
        return ok(messageService.findOneById(id));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageAllPropertiesDto> create(@Valid @RequestBody MessageAllPropertiesDto message) {
        LOGGER.info(format(CREATE_MESSAGE_MESSAGE, message.getMessage()));
        return status(CREATED).body(messageService.create(message));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        LOGGER.info(format(DELETE_MESSAGE_BY_ID_MESSAGE, id));
        messageService.delete(id);
        return status(NO_CONTENT).build();
    }

    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageAllPropertiesDto> update(@Valid @RequestBody MessageAllPropertiesDto message, @PathVariable("id") String id) {
        LOGGER.info(format(UPDATE_MESSAGE_BY_ID_MESSAGE,id));
        return ok(messageService.update(message, id));
    }
}
