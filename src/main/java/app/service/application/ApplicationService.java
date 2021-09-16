package app.service.application;

import app.dto.application.ApplicationAllPropertiesDto;
import app.dto.car.CarAllPropertiesDto;
import app.dto.message.MessageAllPropertiesDto;
import app.dto.notification.NotificationAllPropertiesDto;
import app.entity.*;
import app.exception.declarations.application.ApplicationNotFoundException;
import app.exception.declarations.car.CarNotFoundException;
import app.exception.declarations.common.AlreadyExistingResourceException;
import app.exception.declarations.common.ConflictException;
import app.exception.declarations.common.ServiceException;
import app.repository.IApplicationRepository;
import app.repository.ITripRepository;
import app.service.car.CarService;
import app.service.trip.ITripService;
import app.service.user.IUserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.stream.Collectors;

import static app.util.Constants.*;
import static java.lang.String.format;

public class ApplicationService implements IApplicationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationService.class);

    private final IApplicationRepository applicationRepository;
    private final IUserService userService;
    private final ITripService tripService;
    private final ModelMapper modelMapper;

    public ApplicationService(IApplicationRepository applicationRepository, IUserService userService, ITripService tripService, ModelMapper modelMapper) {
        this.applicationRepository = applicationRepository;
        this.userService = userService;
        this.tripService = tripService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ApplicationAllPropertiesDto> findAll() throws ServiceException {
        LOGGER.info(GET_ALL_TRIP_APPLICATIONS_MESSAGE);

        try {
            return applicationRepository
                    .findAll()
                    .stream()
                    .map(message -> modelMapper.map(message, ApplicationAllPropertiesDto.class))
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }

    @Override
    public ApplicationAllPropertiesDto findOneById(String id) throws ServiceException, ApplicationNotFoundException {
        LOGGER.info(format(FIND_TRIP_APPLICATION_BY_ID_MESSAGE, id));

        return modelMapper.map(findApplicationById(id), ApplicationAllPropertiesDto.class);
    }

    @Override
    public void delete(String id) throws ServiceException {
        LOGGER.info(format(DELETE_TRIP_APPLICATION_BY_ID_MESSAGE, id));

        try {
            applicationRepository.findById(id).ifPresent(applicationRepository::delete);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(CONFLICT_DELETE_MESSAGE);
            throw new ConflictException(CONFLICT_DELETE_MESSAGE);
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }

    @Override
    public ApplicationAllPropertiesDto create(ApplicationAllPropertiesDto application) throws ServiceException {
        LOGGER.info(format(CREATE_TRIP_APPLICATION_MESSAGE, application.getInformation()));

        TripApplicationEntity applicationToBeCreated = modelMapper.map(application,TripApplicationEntity.class);

        UserEntity userInDb = userService.findUserById(application.getUser().getId());
        TripEntity tripInDb = tripService.findTripById(application.getTrip().getId());

        applicationToBeCreated.setUser(userInDb);
        applicationToBeCreated.setTrip(tripInDb);

        return modelMapper.map(createTripApplication(applicationToBeCreated), ApplicationAllPropertiesDto.class);
    }

    @Override
    public ApplicationAllPropertiesDto update(ApplicationAllPropertiesDto application, String id) throws ServiceException, ApplicationNotFoundException {
        LOGGER.info(format(UPDATE_TRIP_APPLICATION_BY_ID_MESSAGE, application.getInformation()));

        TripApplicationEntity applicationInDb = findApplicationById(id);

        TripApplicationEntity applicationToBeCreated = modelMapper.map(application,TripApplicationEntity.class);

        applicationToBeCreated.setId(applicationInDb.getId());
        applicationToBeCreated.setUser(applicationInDb.getUser());
        applicationToBeCreated.setTrip(applicationInDb.getTrip());

        return modelMapper.map(createTripApplication(applicationToBeCreated), ApplicationAllPropertiesDto.class);
    }

    @Override
    public TripApplicationEntity findApplicationById(String id) throws ApplicationNotFoundException {
        try {
            return applicationRepository
                    .findById(id)
                    .orElseThrow(() -> new CarNotFoundException(format(TRIP_APPLICATION_NOT_FOUND_MESSAGE, id)));
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }

    private TripApplicationEntity createTripApplication(TripApplicationEntity tripApplicationEntity) {
        try {
            return applicationRepository.save(tripApplicationEntity);

        } catch (DataIntegrityViolationException e) {
            LOGGER.error(CONFLICT_CREATE_MESSAGE);
            throw new AlreadyExistingResourceException(EXISTING_RESOURCE_MESSAGE);
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE, e);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }
}
