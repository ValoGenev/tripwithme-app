package app.service.notification;

import app.dto.message.MessageAllPropertiesDto;
import app.dto.notification.NotificationAllPropertiesDto;
import app.entity.MessageEntity;
import app.entity.NotificationEntity;
import app.entity.UserEntity;
import app.exception.declarations.common.AlreadyExistingResourceException;
import app.exception.declarations.common.ConflictException;
import app.exception.declarations.common.ServiceException;
import app.exception.declarations.message.MessageNotFoundException;
import app.exception.declarations.notifcation.NotificationNotFoundException;
import app.repository.INotificationRepository;
import app.service.car.CarService;
import app.service.user.IUserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.stream.Collectors;

import static app.util.Constants.*;
import static java.lang.String.format;

public class NotificationService implements INotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);

    private INotificationRepository notificationRepository;
    private IUserService userService;
    private ModelMapper modelMapper;

    @Autowired
    public NotificationService(INotificationRepository notificationRepository, IUserService userService, ModelMapper modelMapper) {
        this.notificationRepository = notificationRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<NotificationAllPropertiesDto> findAll() throws ServiceException {
        LOGGER.info(GET_ALL_NOTIFICATIONS_MESSAGE);

        try {
            return notificationRepository
                    .findAll()
                    .stream()
                    .map(message -> modelMapper.map(message, NotificationAllPropertiesDto.class))
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }

    @Override
    public NotificationAllPropertiesDto findOneById(String id) throws ServiceException, NotificationNotFoundException {
        LOGGER.info(format(FIND_NOTIFICATION_BY_ID_MESSAGE, id));

        return modelMapper.map(findNotificationById(id), NotificationAllPropertiesDto.class);
    }

    @Override
    public void delete(String id) throws ServiceException {
        LOGGER.info(format(DELETE_NOTIFICATION_BY_ID_MESSAGE, id));

        try {
            notificationRepository.findById(id).ifPresent(notificationRepository::delete);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(CONFLICT_DELETE_MESSAGE);
            throw new ConflictException(CONFLICT_DELETE_MESSAGE);
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }

    @Override
    public NotificationAllPropertiesDto create(NotificationAllPropertiesDto notification) throws ServiceException {
        LOGGER.info(format(CREATE_NOTIFICATION_MESSAGE, notification.getMessage()));

        NotificationEntity notificationToBeCreated = modelMapper.map(notification,NotificationEntity.class);

        UserEntity userInDb = userService.findUserById(notification.getUser().getId());

        notificationToBeCreated.setUser(userInDb);

        return modelMapper.map(createNotification(notificationToBeCreated),NotificationAllPropertiesDto.class);
    }

    @Override
    public NotificationAllPropertiesDto update(NotificationAllPropertiesDto notification, String id) throws ServiceException, NotificationNotFoundException {
        LOGGER.info(format(UPDATE_NOTIFICATION_BY_ID_MESSAGE, notification.getId()));

        NotificationEntity notificationInDb = findNotificationById(id);

        NotificationEntity notificationToBeUpdated = modelMapper.map(notification,NotificationEntity.class);

        notificationToBeUpdated.setUser(notificationInDb.getUser());

        return modelMapper.map(createNotification(notificationToBeUpdated),NotificationAllPropertiesDto.class);
    }

    private NotificationEntity createNotification(NotificationEntity notificationEntity) {

        try {
            return notificationRepository.save(notificationEntity);

        } catch (DataIntegrityViolationException e) {

            LOGGER.error(CONFLICT_CREATE_MESSAGE);
            throw new AlreadyExistingResourceException(EXISTING_RESOURCE_MESSAGE);
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE, e);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }

    @Override
    public NotificationEntity findNotificationById(String id) throws NotificationNotFoundException {
        try {
            return notificationRepository
                    .findById(id)
                    .orElseThrow(() -> new NotificationNotFoundException(format(NOTIFICATION_NOT_FOUND_MESSAGE, id)));
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }
}
