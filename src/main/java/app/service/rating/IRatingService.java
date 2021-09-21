package app.service.rating;

import app.dto.notification.NotificationAllPropertiesDto;
import app.dto.rating.RatingAllPropertiesDto;
import app.dto.rating.RatingDto;
import app.entity.RatingEntity;
import app.exception.declarations.common.ServiceException;
import app.exception.declarations.notifcation.NotificationNotFoundException;
import app.exception.declarations.rating.RatingNotFoundException;

import java.util.List;

public interface IRatingService {
    List<RatingAllPropertiesDto> findAll() throws ServiceException;

    RatingAllPropertiesDto findOneById(String id) throws ServiceException, RatingNotFoundException;

    void delete(String id) throws ServiceException;

    RatingAllPropertiesDto create(RatingAllPropertiesDto rating) throws  ServiceException;

    RatingAllPropertiesDto update(RatingDto rating, String id) throws ServiceException,RatingNotFoundException;

    RatingEntity findRatingByID(String id) throws RatingNotFoundException;
}
