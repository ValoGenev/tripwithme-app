package app.service.trip;

import app.dto.message.MessageAllPropertiesDto;
import app.dto.trip.CreateTripDto;
import app.dto.trip.TripAllPropertiesDto;
import app.dto.trip.UpdateTripDto;
import app.dto.user.UserAllPropertiesDto;
import app.entity.TripEntity;
import app.exception.declarations.common.ServiceException;
import app.exception.declarations.trip.TripNotFoundException;

import java.util.List;

public interface ITripService {

    List<TripAllPropertiesDto> findAll() throws ServiceException;

    TripAllPropertiesDto findOneById(String id) throws ServiceException, TripNotFoundException;

    void delete(String id) throws ServiceException;

    TripAllPropertiesDto create(CreateTripDto trip) throws  ServiceException;

    TripAllPropertiesDto update(UpdateTripDto trip, String id) throws ServiceException,TripNotFoundException;

    TripEntity findTripById(String id);

    List<UserAllPropertiesDto> getApplicants(String id);

    List<UserAllPropertiesDto> getPassengers(String id);

    void addPassenger(String tripId,String userId);

    void removePassenger(String tripId,String userId);

    List<MessageAllPropertiesDto> getMessages(String id);


}


