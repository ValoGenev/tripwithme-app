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
import org.hibernate.sql.Update;
import org.joda.time.Interval;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.time.Period;
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
    //TODO check if trip belongs to loggedInUser
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
    //TODO driver is loggedInUser
    public TripAllPropertiesDto create(CreateTripDto trip) throws ServiceException {
        LOGGER.info(format(CREATE_TRIP_MESSAGE, trip.getDescription()));

        UserEntity driver = userService.findUserById(trip.getDriver().getId());
        CarEntity car = findExistingCar(trip, driver);
        List<UserEntity> passengers = findExistingPassengers(trip);

        assertDriverNotAsPassenger(passengers, driver.getId());
        assertDistinctPassengers(passengers);
        assertDriverHasOneTripAtTheSameTimePeriod(trip, driver);

        TripEntity tripToBeCreated = modelMapper.map(trip, TripEntity.class);

        tripToBeCreated.setCar(car);
        tripToBeCreated.setDriver(driver);
        tripToBeCreated.setPassengers(passengers);
        tripToBeCreated.setApplicants(new ArrayList<>());
        tripToBeCreated.setMessages(new ArrayList<>());

        tripToBeCreated.setTimePosted(LocalDateTime.now().withNano(0));

        TripAllPropertiesDto createdTrip = modelMapper.map(createTrip(tripToBeCreated), TripAllPropertiesDto.class);


        //TODO In a new thread -> get the trip full_route ,time and seats left
        // and check if they match any searches. if yes -> notify searchers

        return createdTrip;
    }

    private void assertDriverHasOneTripAtTheSameTimePeriod(TripDto trip, UserEntity driver) {
        List<TripEntity> driverTrips = new ArrayList<>();
        driverTrips.addAll(driver.getTripsAsPassenger());
        driverTrips.addAll(driver.getTripsAsDriver());


        LocalDateTime startDate = trip.getStartTime();
        LocalDateTime endTDate = trip.getEndTime();

        driverTrips.forEach(t -> {
            LocalDateTime tripsStartDate = t.getStartTime();
            LocalDateTime tripsEndDate = t.getEndTime();


            if ((startDate.compareTo(tripsStartDate) >= 0 && startDate.compareTo(tripsEndDate) <= 0) ||
                    (endTDate.compareTo(tripsStartDate) >= 0 && endTDate.compareTo(tripsEndDate) <= 0)) {

                if (!trip.getId().equals(t.getId())) {
                    throw new OneTripPerTimePeriodException(ONE_TRIP_PER_TIME_PERIOD_MESSAGE);
                }
            }
        });
    }

    @Override
    //TODO check if trip belongs to loggedInUser
    public TripAllPropertiesDto update(UpdateTripDto trip, String id) throws ServiceException, TripNotFoundException {
        LOGGER.info(format(UPDATE_TRIP_BY_ID_MESSAGE, trip.getId()));

        TripEntity tripInDb = findTripById(id);
        CarEntity carInDb = findExistingCar(trip, tripInDb.getDriver());
        UserEntity driver = tripInDb.getDriver();

        TripEntity tripToBeUpdated = modelMapper.map(trip, TripEntity.class);

        assertMandatoryValuesWithExistingPassengersNotChanged(tripInDb, tripToBeUpdated);

        tripToBeUpdated.setId(tripInDb.getId());

        assertDriverHasOneTripAtTheSameTimePeriod(trip,driver);

        tripToBeUpdated.setCar(carInDb);
        tripToBeUpdated.setDriver(driver);
        tripToBeUpdated.setPassengers(tripInDb.getPassengers());
        tripToBeUpdated.setApplicants(tripInDb.getApplicants());
        tripToBeUpdated.setMessages(tripInDb.getMessages());
        tripToBeUpdated.setTimePosted(tripInDb.getTimePosted());

        TripAllPropertiesDto updatedTrip = modelMapper.map(createTrip(tripToBeUpdated), TripAllPropertiesDto.class);

        //TODO In a new thread -> get the trip full_route ,time and seats left
        // and check if they match any searches. if yes -> notify searchers
        // recheck applicants and filter them out those who dont match new conditions
        // ?? not sure if i have to delete notifications

        return updatedTrip;
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
    //TODO check if trip driver is loggedInUser
    public void addPassenger(String tripId, String userId) {
        LOGGER.info(format(ADD_PASSENGER_MESSAGE, userId, tripId));

        TripEntity tripInDb = findTripById(tripId);
        UserEntity userInDb = userService.findUserById(userId);

        assertNotExistingPassengerSeats(tripInDb);

        tripInDb.getPassengers().add(userInDb);
        tripInDb.setSeats(tripInDb.getSeats() - 1);

        assertDriverNotAsPassenger(tripInDb.getPassengers(), tripInDb.getDriver().getId());
        assertDistinctPassengers(tripInDb.getPassengers());

        createTrip(tripInDb);

        //TODO send notification to applicant that he was approved
    }

    @Override
    //TODO check if tripId.getDriver() is equal to loggedInUser
    public void removePassenger(String tripId, String userId) {

        LOGGER.info(format(REMOVE_PASSENGER_MESSAGE, userId, tripId));

        TripEntity tripInDb = findTripById(tripId);
        UserEntity userInDb = userService.findUserById(userId);

        if (tripInDb.getPassengers().remove(userInDb)) {
            tripInDb.setSeats(tripInDb.getSeats() + 1);
            createTrip(tripInDb);
            //TODO send notification to passenger for being removed
        }


    }

    public void leaveTrip(String tripId, String userId) {
        //TODO check if userId is equal to loggedInUser

        LOGGER.info(format("User [%s] leaving trip with id [%s]", userId, tripId));

        TripEntity tripInDb = findTripById(tripId);
        UserEntity userInDb = userService.findUserById(userId);

        if (tripInDb.getPassengers().remove(userInDb)) {
            tripInDb.setSeats(tripInDb.getSeats() + 1);
            createTrip(tripInDb);
            //TODO send notification to driver
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

    private void assertMandatoryValuesWithExistingPassengersNotChanged(TripEntity tripInDb, TripEntity tripToBeUpdated) {

        if (!tripInDb.getStartTime().equals(tripToBeUpdated.getStartTime())
                || !tripInDb.getEndTime().equals(tripToBeUpdated.getEndTime())
                || tripInDb.getPrice() != tripToBeUpdated.getPrice()
                || tripInDb.getFullRoute().size() != tripToBeUpdated.getFullRoute().size()
                || !tripInDb.getRouteStartingPoint().equals(tripToBeUpdated.getRouteStartingPoint())
                || !tripInDb.getRouteEndPoint().equals(tripToBeUpdated.getRouteEndPoint())) {

            throw new CannotUpdateTripWithPassengersException(format(CANNOT_UPDATE_TRIP_WITH_PASSENGERS_MESSAGE, tripInDb.getId()));
        }
    }

    private void assertNotExistingPassengerSeats(TripEntity trip) {
        if (trip.getSeats() == 0) {
            throw new NoMoreSeatsAvailableException(format(NO_SEATS_AVAILABLE_MESSAGE, trip.getId()));
        }
    }

    private void assertDistinctPassengers(List<UserEntity> users) {
        if (users.stream().map(UserEntity::getId).distinct().count() != users.size()) {
            throw new PassengerAlreadyOnTheTripException(PASSENGER_ALREADY_EXISTING_MESSAGE);
        }
    }
}
