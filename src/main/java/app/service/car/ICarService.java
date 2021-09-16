package app.service.car;

import app.dto.application.ApplicationAllPropertiesDto;
import app.dto.car.CarAllPropertiesDto;
import app.entity.CarEntity;
import app.exception.declarations.application.ApplicationNotFoundException;
import app.exception.declarations.car.CarNotFoundException;
import app.exception.declarations.common.ServiceException;

import java.util.List;

public interface ICarService {

    List<CarAllPropertiesDto> findAll() throws ServiceException;

    CarAllPropertiesDto findOneById(String id) throws ServiceException, CarNotFoundException;

    void delete(String id) throws ServiceException;

    CarAllPropertiesDto create(CarAllPropertiesDto car) throws  ServiceException;

    CarAllPropertiesDto update(CarAllPropertiesDto car, String id) throws ServiceException,CarNotFoundException;

    CarEntity findCarById(String id) throws CarNotFoundException;

}
