package app.service.notification;

import app.dto.message.MessageAllPropertiesDto;
import app.dto.notification.NotificationAllPropertiesDto;
import app.entity.NotificationEntity;
import app.exception.declarations.common.ServiceException;
import app.exception.declarations.message.MessageNotFoundException;
import app.exception.declarations.notifcation.NotificationNotFoundException;

import java.util.List;

public interface INotificationService {
    List<NotificationAllPropertiesDto> findAll() throws ServiceException;

    NotificationAllPropertiesDto findOneById(String id) throws ServiceException, NotificationNotFoundException;

    void delete(String id) throws ServiceException;

    NotificationAllPropertiesDto create(NotificationAllPropertiesDto notification) throws  ServiceException;

    NotificationAllPropertiesDto update(NotificationAllPropertiesDto notification, String id) throws ServiceException,NotificationNotFoundException;

    NotificationEntity findNotificationById(String id) throws NotificationNotFoundException;

}
