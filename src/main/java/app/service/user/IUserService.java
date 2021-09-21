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
import app.entity.NotificationEntity;
import app.entity.UserEntity;
import app.exception.declarations.common.ServiceException;
import app.exception.declarations.user.UserNotFoundException;

import java.util.List;

public interface IUserService {

    List<UserAllPropertiesDto> findAll() throws ServiceException;

    UserAllPropertiesDto findOneById(String id) throws ServiceException, UserNotFoundException;

    void delete(String id) throws ServiceException;

    UserAllPropertiesDto create(UserDto user) throws  ServiceException;

    UserAllPropertiesDto update(UserDto user, String id) throws ServiceException,UserNotFoundException;

    UserEntity findUserById(String id) throws UserNotFoundException;

    UserAllPropertiesDto createBot(CreateBotUserDto user) throws ServiceException;

    List<TripAllPropertiesDto> getUserTripsAsPassenger(String id);

    List<TripAllPropertiesDto> getUserTripsAsDriver(String id);

    List<CarAllPropertiesDto> getUserCars(String id);

    List<RatingAllPropertiesDto> getUserRatings(String id);

    List<NotificationAllPropertiesDto> getUserNotifications(String id);

    List<SearchAllPropertiesDto> getUserSearches(String id);

    List<ApplicationAllPropertiesDto> getUserApplications(String id);

}
