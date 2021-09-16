package app.service.car;

import app.dto.car.CarAllPropertiesDto;

import app.entity.CarEntity;
import app.entity.UserEntity;
import app.exception.declarations.car.CarNotFoundException;
import app.exception.declarations.common.AlreadyExistingResourceException;
import app.exception.declarations.common.ConflictException;
import app.exception.declarations.common.ServiceException;
import app.exception.declarations.user.UserNotFoundException;
import app.repository.ICarRepository;
import app.service.user.IUserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.stream.Collectors;

import static app.util.Constants.*;
import static java.lang.String.format;

public class CarService implements ICarService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarService.class);

    private ICarRepository carRepository;
    private IUserService userService;
    private ModelMapper modelMapper;


    @Autowired
    public CarService(ICarRepository carRepository, IUserService userService, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CarAllPropertiesDto> findAll() throws ServiceException {
        LOGGER.info(GET_ALL_CARS_MESSAGE);

        try {
            return carRepository
                    .findAll()
                    .stream()
                    .map(car -> modelMapper.map(car, CarAllPropertiesDto.class))
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }

    @Override
    public CarAllPropertiesDto findOneById(String id) throws ServiceException, CarNotFoundException {
        LOGGER.info(format(FIND_CAR_BY_ID_MESSAGE, id));

        return modelMapper.map(findCarById(id), CarAllPropertiesDto.class);
    }

    @Override
    public void delete(String id) throws ServiceException {
        try {
            carRepository.findById(id).ifPresent(carRepository::delete);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(CONFLICT_DELETE_MESSAGE);
            throw new ConflictException(CONFLICT_DELETE_MESSAGE);
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }

    @Override
    public CarAllPropertiesDto create(CarAllPropertiesDto car) throws ServiceException {
        LOGGER.info(format(CREATE_CAR_MESSAGE, car.getModel()));

        UserEntity userInDb = userService.findUserById(car.getOwner().getId());

        CarEntity carToBeCreated = modelMapper.map(car,CarEntity.class);
        carToBeCreated.setOwner(userInDb);

        return modelMapper.map(createCar(carToBeCreated),CarAllPropertiesDto.class);
    }

    @Override
    public CarAllPropertiesDto update(CarAllPropertiesDto car, String id) throws ServiceException, CarNotFoundException {
        LOGGER.info(format(UPDATE_CAR_BY_ID_MESSAGE, car.getId()));

        CarEntity carInDb = findCarById(id);

        CarEntity carToBeUpdated = modelMapper.map(car,CarEntity.class);

        carToBeUpdated.setId(carInDb.getId());
        carToBeUpdated.setOwner(carInDb.getOwner());

        return modelMapper.map(createCar(carToBeUpdated),CarAllPropertiesDto.class);
    }

    private CarEntity createCar(CarEntity car) {
        try {
            return carRepository.save(car);

        } catch (DataIntegrityViolationException e) {
            LOGGER.error(CONFLICT_CREATE_MESSAGE);
            throw new AlreadyExistingResourceException(EXISTING_RESOURCE_MESSAGE);
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE, e);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }


    @Override
    public CarEntity findCarById(String id) throws CarNotFoundException {
        try {
            return carRepository
                    .findById(id)
                    .orElseThrow(() -> new CarNotFoundException(format(CAR_NOT_FOUND_MESSAGE, id)));
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }
}
