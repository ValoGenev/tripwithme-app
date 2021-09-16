package app.service.application;

import app.dto.application.ApplicationAllPropertiesDto;
import app.entity.TripApplicationEntity;
import app.exception.declarations.application.ApplicationNotFoundException;
import app.exception.declarations.common.ServiceException;

import java.util.List;

public interface IApplicationService {

    List<ApplicationAllPropertiesDto> findAll() throws ServiceException;

    ApplicationAllPropertiesDto findOneById(String id) throws ServiceException, ApplicationNotFoundException;

    void delete(String id) throws ServiceException;

    ApplicationAllPropertiesDto create(ApplicationAllPropertiesDto application) throws  ServiceException;

    ApplicationAllPropertiesDto update(ApplicationAllPropertiesDto application, String id) throws ServiceException,ApplicationNotFoundException;

    TripApplicationEntity findApplicationById(String id) throws ApplicationNotFoundException;
}
