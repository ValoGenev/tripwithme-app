package app.controller;


import app.dto.message.MessageAllPropertiesDto;
import app.dto.notification.NotificationAllPropertiesDto;
import app.service.message.IMessageService;
import app.service.notification.INotificationService;
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
@RequestMapping("/config/api/v1/notifications")
public class NotificationController {

    private static final Logger LOGGER = getLogger(NotificationController.class);
    private final INotificationService notificationService;

    @Autowired
    public NotificationController(INotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NotificationAllPropertiesDto>> getAll() {
        LOGGER.info(GET_ALL_NOTIFICATIONS_MESSAGE);
        return ok(notificationService.findAll());
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationAllPropertiesDto> getOne(@PathVariable("id") String id) {
        LOGGER.info(format(FIND_NOTIFICATION_BY_ID_MESSAGE, id));
        return ok(notificationService.findOneById(id));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationAllPropertiesDto> create(@Valid @RequestBody NotificationAllPropertiesDto notification) {
        LOGGER.info(format(CREATE_NOTIFICATION_MESSAGE, notification.getMessage()));
        return status(CREATED).body(notificationService.create(notification));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        LOGGER.info(format(DELETE_NOTIFICATION_BY_ID_MESSAGE, id));
        notificationService.delete(id);
        return status(NO_CONTENT).build();
    }

    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationAllPropertiesDto> update(@Valid @RequestBody NotificationAllPropertiesDto notification, @PathVariable("id") String id) {
        LOGGER.info(format(UPDATE_MESSAGE_BY_ID_MESSAGE,id));
        return ok(notificationService.update(notification, id));
    }
}
