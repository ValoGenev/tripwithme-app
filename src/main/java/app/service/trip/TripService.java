package app.service.trip;

import app.dto.message.MessageAllPropertiesDto;
import app.dto.trip.CreateTripDto;
import app.dto.trip.TripAllPropertiesDto;
import app.dto.trip.TripDto;
import app.dto.trip.UpdateTripDto;
import app.dto.user.UserAllPropertiesDto;
import app.entity.CarEntity;
import app.entity.TripEntity;
import app.entity.UserEntity;
import app.exception.declarations.car.CarNotFoundException;
import app.exception.declarations.common.AlreadyExistingResourceException;
import app.exception.declarations.common.ConflictException;
import app.exception.declarations.common.ServiceException;
import app.exception.declarations.trip.*;
import app.repository.ITripRepository;

import app.service.user.IUserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static app.util.Constants.*;
import static app.util.Constants.DATABASE_ERROR_MESSAGE;
import static java.lang.String.format;

public class TripService implements ITripService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TripService.class);

    private final ITripRepository tripRepository;
    private final IUserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public TripService(ITripRepository tripRepository, IUserService userService, ModelMapper modelMapper) {
        this.tripRepository = tripRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TripAllPropertiesDto> findAll() throws ServiceException {
        LOGGER.info(GET_ALL_TRIPS_MESSAGE);

        try {
            return tripRepository
                    .findAll()
                    .stream()
                    .map(trip -> modelMapper.map(trip, TripAllPropertiesDto.class))
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }

    @Override
    public TripAllPropertiesDto findOneById(String id) throws ServiceException, TripNotFoundException {
        LOGGER.info(format(FIND_TRIP_BY_ID_MESSAGE, id));

        return modelMapper.map(findTripById(id), TripAllPropertiesDto.class);
    }

    @Override
    public void delete(String id) throws ServiceException {
        LOGGER.info(format(DELETE_TRIP_BY_ID_MESSAGE, id));

        try {
            tripRepository.findById(id).ifPresent(tripRepository::delete);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(CONFLICT_DELETE_MESSAGE);
            throw new ConflictException(CONFLICT_DELETE_MESSAGE);
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }

    @Override
    public TripAllPropertiesDto create(CreateTripDto trip) throws ServiceException {
        LOGGER.info(format(CREATE_TRIP_MESSAGE, trip.getDescription()));

        UserEntity driver = userService.findUserById(trip.getDriver().getId());
        CarEntity car = findExistingCar(trip, driver);
        List<UserEntity> passengers = findExistingPassengers(trip);

        assertDriverNotAsPassenger(passengers, driver.getId());
        assertDistinctPassengers(passengers);

        //TODO In a new thread -> get the trip full_route ,time and seats left
        //TODO and check if they match any searches. if yes -> notify searchers

        TripEntity tripToBeCreated = modelMapper.map(trip, TripEntity.class);

        tripToBeCreated.setCar(car);
        tripToBeCreated.setDriver(driver);
        tripToBeCreated.setPassengers(passengers);
        tripToBeCreated.setApplicants(new ArrayList<>());
        tripToBeCreated.setMessages(new ArrayList<>());

        tripToBeCreated.setTimePosted(LocalDateTime.now().withNano(0));

        return modelMapper.map(createTrip(tripToBeCreated), TripAllPropertiesDto.class);
    }

    @Override
    public TripAllPropertiesDto update(UpdateTripDto trip, String id) throws ServiceException, TripNotFoundException {
        LOGGER.info(format(UPDATE_TRIP_BY_ID_MESSAGE, trip.getId()));

        TripEntity tripInDb = findTripById(id);
        CarEntity carInDb = findExistingCar(trip, tripInDb.getDriver());
        UserEntity driver = tripInDb.getDriver();

        TripEntity tripToBeUpdated = modelMapper.map(trip, TripEntity.class);

        tripToBeUpdated.setId(tripInDb.getId());
        tripToBeUpdated.setCar(carInDb);
        tripToBeUpdated.setDriver(driver);
        tripToBeUpdated.setPassengers(tripInDb.getPassengers());
        tripToBeUpdated.setApplicants(tripInDb.getApplicants());
        tripToBeUpdated.setMessages(tripInDb.getMessages());

        return modelMapper.map(createTrip(tripToBeUpdated), TripAllPropertiesDto.class);
    }


    private TripEntity createTrip(TripEntity trip) {

        try {
            return tripRepository.save(trip);

        } catch (DataIntegrityViolationException e) {

            LOGGER.error(CONFLICT_CREATE_MESSAGE);
            throw new AlreadyExistingResourceException(EXISTING_RESOURCE_MESSAGE);
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE, e);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }


    @Override
    public TripEntity findTripById(String id) {
        try {
            return tripRepository
                    .findById(id)
                    .orElseThrow(() -> new TripNotFoundException(format(TRIP_NOT_FOUND_MESSAGE, id)));
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }

    @Override
    public List<UserAllPropertiesDto> getApplicants(String id) {
        LOGGER.info(format(GET_TRIP_APPLICATIONS_MESSAGE, id));

        return findTripById(id)
                .getApplicants()
                .stream()
                .map(u -> modelMapper.map(u, UserAllPropertiesDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserAllPropertiesDto> getPassengers(String id) {
        LOGGER.info(format(GET_TRIP_PASSENGERS_MESSAGE, id));

        return findTripById(id)
                .getPassengers()
                .stream()
                .map(u -> modelMapper.map(u, UserAllPropertiesDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void addPassenger(String tripId, String userId) {
        LOGGER.info(format(ADD_PASSENGER_MESSAGE, userId, tripId));

        TripEntity tripInDb = findTripById(tripId);
        UserEntity userInDb = userService.findUserById(userId);

        assertNotExistingPassengerSeats(tripInDb);

        tripInDb.getPassengers().add(userInDb);
        tripInDb.setSeatsLeft(tripInDb.getSeatsLeft() - 1);

        assertDriverNotAsPassenger(tripInDb.getPassengers(), tripInDb.getDriver().getId());
        assertDistinctPassengers(tripInDb.getPassengers());

        createTrip(tripInDb);
    }

    @Override
    public void removePassenger(String tripId, String userId) {
        LOGGER.info(format(REMOVE_PASSENGER_MESSAGE, userId, tripId));

        TripEntity tripInDb = findTripById(tripId);
        UserEntity userInDb = userService.findUserById(userId);

        if (tripInDb.getPassengers().remove(userInDb)) {
            tripInDb.setSeatsLeft(tripInDb.getSeatsLeft() + 1);
            createTrip(tripInDb);
        }
    }

    @Override
    public List<MessageAllPropertiesDto> getMessages(String id) {
        LOGGER.info(format(GET_TRIP_MESSAGES_MESSAGE, id));

        return findTripById(id)
                .getMessages()
                .stream()
                .map(m -> modelMapper.map(m, MessageAllPropertiesDto.class))
                .collect(Collectors.toList());
    }

    private CarEntity findExistingCar(UpdateTripDto trip, UserEntity driver) {
        return Objects.isNull(trip.getCar()) ? null : driver
                .getCars()
                .stream()
                .filter(c -> c.getId().equals(trip.getCar().getId()))
                .findFirst().orElseThrow(() -> new CarNotFoundException(format(CAR_NOT_FOUND_MESSAGE, trip.getCar().getId())));
    }

    private List<UserEntity> findExistingPassengers(CreateTripDto trip) {
        return Objects.isNull(trip.getPassengers())
                ? new ArrayList<>()
                : trip.getPassengers().stream()
                .map(u -> userService.findUserById(u.getId()))
                .collect(Collectors.toList());
    }

    private void assertDriverNotAsPassenger(List<UserEntity> passengers, String driverId) {
        passengers
                .stream()
                .map(UserEntity::getId)
                .filter(id -> id.equals(driverId))
                .findFirst().ifPresent(s -> {
            throw new DriverCannotBePassengerException(DRIVER_AS_PASSENGER_MESSAGE);
        });
    }

    private void assertNotExistingPassengerSeats(TripEntity trip) {
        if (trip.getSeatsLeft() == 0) {
            throw new NoMoreSeatsAvailableException(format(NO_SEATS_AVAILABLE_MESSAGE, trip.getId()));
        }
    }

    private void assertDriverNotChanged(String driver1Id, String driver2Id) {
        if (!driver1Id.equals(driver2Id)) {
            throw new DriverCannotBeChangedException(DRIVER_CANNOT_BE_CHANGED_MESSAGE);
        }
    }

    private void assertDistinctPassengers(List<UserEntity> users) {
        if (users.stream().map(UserEntity::getId).distinct().count() != users.size()) {
            throw new PassengerAlreadyOnTheTripException(PASSENGER_ALREADY_EXISTING_MESSAGE);
        }
    }
}
