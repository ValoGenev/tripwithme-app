package app.service.user;

import app.dto.application.ApplicationAllPropertiesDto;
import app.dto.car.CarAllPropertiesDto;
import app.dto.notification.NotificationAllPropertiesDto;
import app.dto.rating.RatingAllPropertiesDto;
import app.dto.search.SearchAllPropertiesDto;
import app.dto.trip.TripAllPropertiesDto;
import app.dto.user.CreateBotUserDto;
import app.dto.user.CreateUserDto;
import app.dto.user.UserAllPropertiesDto;
import app.dto.user.UserDto;
import app.entity.UserEntity;
import app.exception.declarations.common.AlreadyExistingResourceException;
import app.exception.declarations.common.ConflictException;
import app.exception.declarations.common.ServiceException;
import app.exception.declarations.user.UserNotFoundException;
import app.model.UserRole;
import app.model.UserType;
import app.repository.IUserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static app.util.Constants.*;
import static java.lang.String.format;


public class UserService implements IUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(IUserRepository userRepository,ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public List<UserAllPropertiesDto> findAll() throws ServiceException {
        LOGGER.info(GET_ALL_USERS_MESSAGE);

        try {
            return  userRepository
                    .findAll()
                    .stream()
                    .map(user -> modelMapper.map(user, UserAllPropertiesDto.class))
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }

    @Override
    public UserAllPropertiesDto findOneById(String id) throws ServiceException, UserNotFoundException {
        LOGGER.info(format(FIND_USER_BY_ID_MESSAGE, id));

        return modelMapper.map(findUserById(id), UserAllPropertiesDto.class);
    }

    @Override
    //TODO only admin
    public void delete(String id) throws ServiceException {
        LOGGER.info(format(DELETE_USER_BY_ID_MESSAGE, id));

        try {
            userRepository.findById(id).ifPresent(userRepository::delete);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(CONFLICT_DELETE_MESSAGE);
            throw new ConflictException(CONFLICT_DELETE_MESSAGE);
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }

    @Override
    //TODO only admin
    public UserAllPropertiesDto create(UserDto user) throws ServiceException {
        LOGGER.info(format(CREATE_USER_MESSAGE, user.getFullName()));

        UserEntity userToBeCreated = modelMapper.map(user,UserEntity.class);

        configureNewUserProperties(UserType.TEST,UserRole.USER,userToBeCreated);

        return modelMapper.map(createUser(userToBeCreated),UserAllPropertiesDto.class);
    }

    @Override
    //TODO check if user id is loggedInUser
    public UserAllPropertiesDto update(UserDto user, String id) throws ServiceException, UserNotFoundException {
        LOGGER.info(format(UPDATE_USER_BY_ID_MESSAGE, id));

        UserEntity userInDb = findUserById(id);

        modelMapper.map(user,userInDb);

        return modelMapper.map(createUser(userInDb),UserAllPropertiesDto.class);
    }

    @Override
    //TODO only admin
    public UserAllPropertiesDto createBot(CreateBotUserDto user) throws ServiceException {
        LOGGER.info(format(CREATE_USER_BOT_MESSAGE, user.getFullName()));

        UserEntity userToBeCreated = modelMapper.map(user,UserEntity.class);

        configureNewUserProperties(UserType.FACEBOOK,UserRole.BOT,userToBeCreated);

        return modelMapper.map(createUser(userToBeCreated),UserAllPropertiesDto.class);
    }


    @Override
    public List<TripAllPropertiesDto> getUserTripsAsPassenger(String id) {
        LOGGER.info(format(GET_USER_TRIPS_AS_PASSENGER_MESSAGE,id));

        return  findUserById(id)
                .getTripsAsPassenger()
                .stream()
                .map(trip -> modelMapper.map(trip,TripAllPropertiesDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TripAllPropertiesDto> getUserTripsAsDriver(String id) {
        LOGGER.info(format(GET_USER_TRIPS_AS_DRIVER_MESSAGE,id));

        return  findUserById(id)
                .getTripsAsDriver()
                .stream()
                .map(trip -> modelMapper.map(trip,TripAllPropertiesDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CarAllPropertiesDto> getUserCars(String id){
        LOGGER.info(format(GET_USER_CARS_MESSAGE,id));

        return  findUserById(id)
                .getCars()
                .stream()
                .map(car -> modelMapper.map(car,CarAllPropertiesDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<RatingAllPropertiesDto> getUserRatings(String id) {
        LOGGER.info(format(GET_USER_RATINGS_MESSAGE,id));

        return  findUserById(id)
                .getReceivedRatings()
                .stream()
                .map(rating -> modelMapper.map(rating,RatingAllPropertiesDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationAllPropertiesDto> getUserNotifications(String id) {
        LOGGER.info(format(GET_USER_NOTIFICATIONS_MESSAGE,id));

        return  findUserById(id)
                .getNotifications()
                .stream()
                .map(notification -> modelMapper.map(notification,NotificationAllPropertiesDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<SearchAllPropertiesDto> getUserSearches(String id) {
        LOGGER.info(format(GET_USER_SEARCHES_MESSAGE,id));

        return  findUserById(id)
                .getSearches()
                .stream()
                .map(search -> modelMapper.map(search,SearchAllPropertiesDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ApplicationAllPropertiesDto> getUserApplications(String id) {
        LOGGER.info(format(GET_USER_APPLICATIONS_MESSAGE,id));

        return  findUserById(id)
                .getApplications()
                .stream()
                .map(application -> modelMapper.map(application,ApplicationAllPropertiesDto.class))
                .collect(Collectors.toList());
    }


    @Override
    public UserEntity findUserById(String id) {
        try {
            return userRepository
                    .findById(id)
                    .orElseThrow(() -> new UserNotFoundException(format(USER_NOT_FOUND_MESSAGE, id)));
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }

    private UserEntity createUser(UserEntity user) {

        try {
            return userRepository.save(user);

        } catch (DataIntegrityViolationException e) {

            LOGGER.error(CONFLICT_CREATE_MESSAGE);
            throw new AlreadyExistingResourceException(EXISTING_RESOURCE_MESSAGE);
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE, e);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }

    private void configureNewUserProperties(UserType userType,UserRole userRole,UserEntity userToBeConfigured){
        userToBeConfigured.setType(userType);
        userToBeConfigured.setRole(userRole);
        userToBeConfigured.setRegistrationTime(LocalDateTime.now().withNano(0));
        userToBeConfigured.setSearches(new ArrayList<>());
        userToBeConfigured.setTripsAsDriver(new ArrayList<>());
        userToBeConfigured.setTripsAsPassenger(new ArrayList<>());
        userToBeConfigured.setApplications(new ArrayList<>());
        userToBeConfigured.setReceivedRatings(new ArrayList<>());
        userToBeConfigured.setGivenRatings(new ArrayList<>());
        userToBeConfigured.setNotifications(new ArrayList<>());
        userToBeConfigured.setCars(new ArrayList<>());
        userToBeConfigured.setMessages(new ArrayList<>());
    }
}
