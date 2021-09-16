package app.service.message;

import app.dto.car.CarAllPropertiesDto;
import app.dto.message.MessageAllPropertiesDto;
import app.dto.trip.TripAllPropertiesDto;
import app.entity.MessageEntity;
import app.entity.TripEntity;
import app.entity.UserEntity;
import app.exception.declarations.car.CarNotFoundException;
import app.exception.declarations.common.AlreadyExistingResourceException;
import app.exception.declarations.common.ConflictException;
import app.exception.declarations.common.ServiceException;
import app.exception.declarations.message.MessageNotFoundException;
import app.exception.declarations.user.UserNotFoundException;
import app.repository.ICarRepository;
import app.repository.IMessageRepository;
import app.repository.IUserRepository;
import app.service.car.CarService;
import app.service.trip.ITripService;
import app.service.user.IUserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

import static app.util.Constants.*;
import static java.lang.String.format;

public class MessageService implements IMessageService{

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);

    private IMessageRepository messageRepository;
    private ITripService tripService;
    private IUserService userService;
    private ModelMapper modelMapper;

    @Autowired
    public MessageService(IMessageRepository messageRepository, ITripService tripService, IUserService userService, ModelMapper modelMapper) {
        this.messageRepository = messageRepository;
        this.tripService = tripService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<MessageAllPropertiesDto> findAll() throws ServiceException {
        LOGGER.info(GET_ALL_MESSAGES_MESSAGE);

        try {
            return messageRepository
                    .findAll()
                    .stream()
                    .map(message -> modelMapper.map(message, MessageAllPropertiesDto.class))
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }

    @Override
    public MessageAllPropertiesDto findOneById(String id) throws ServiceException, MessageNotFoundException {
        LOGGER.info(format(FIND_MESSAGE_BY_ID_MESSAGE, id));

        return modelMapper.map(findMessageById(id), MessageAllPropertiesDto.class);
    }

    @Override
    public void delete(String id) throws ServiceException {
        LOGGER.info(format(DELETE_MESSAGE_BY_ID_MESSAGE, id));

        try {
            messageRepository.findById(id).ifPresent(messageRepository::delete);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(CONFLICT_DELETE_MESSAGE);
            throw new ConflictException(CONFLICT_DELETE_MESSAGE);
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }

    @Override
    public MessageAllPropertiesDto create(MessageAllPropertiesDto message) throws ServiceException {
        LOGGER.info(format(CREATE_MESSAGE_MESSAGE, message.getMessage()));

        MessageEntity messageToBeCreated = modelMapper.map(message,MessageEntity.class);

        UserEntity userInDb = userService.findUserById(message.getUser().getId());
        TripEntity tripInDb = tripService.findTripById(message.getTrip().getId());

        messageToBeCreated.setUser(userInDb);
        messageToBeCreated.setTrip(tripInDb);

        return modelMapper.map(createMessage(messageToBeCreated), MessageAllPropertiesDto.class);
    }

    @Override
    public MessageAllPropertiesDto update(MessageAllPropertiesDto message, String id) throws ServiceException, MessageNotFoundException {
        LOGGER.info(format(UPDATE_MESSAGE_BY_ID_MESSAGE, message.getMessage()));

        MessageEntity messageInDb = findMessageById(id);

        MessageEntity messageToBeUpdated = modelMapper.map(message,MessageEntity.class);

        messageToBeUpdated.setId(messageInDb.getId());
        messageToBeUpdated.setTrip(messageInDb.getTrip());
        messageToBeUpdated.setUser(messageInDb.getUser());

        return modelMapper.map(createMessage(messageToBeUpdated),MessageAllPropertiesDto.class);
    }

    private MessageEntity createMessage(MessageEntity messageEntity) {

        try {
            return messageRepository.save(messageEntity);

        } catch (DataIntegrityViolationException e) {

            LOGGER.error(CONFLICT_CREATE_MESSAGE);
            throw new AlreadyExistingResourceException(EXISTING_RESOURCE_MESSAGE);
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE, e);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }


    @Override
    public MessageEntity findMessageById(String id) throws MessageNotFoundException {
        try {
            return messageRepository
                    .findById(id)
                    .orElseThrow(() -> new MessageNotFoundException(format(MESSAGE_NOT_FOUND_MESSAGE, id)));
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }
}
