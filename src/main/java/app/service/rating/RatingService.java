package app.service.rating;

import app.dto.notification.NotificationAllPropertiesDto;
import app.dto.rating.RatingAllPropertiesDto;
import app.dto.rating.RatingDto;
import app.entity.RatingEntity;
import app.entity.SearchEntity;
import app.entity.UserEntity;
import app.exception.declarations.common.AlreadyExistingResourceException;
import app.exception.declarations.common.ConflictException;
import app.exception.declarations.common.ServiceException;
import app.exception.declarations.rating.RatingNotFoundException;
import app.exception.declarations.search.SearchNotFoundException;
import app.repository.IRatingRepository;
import app.service.notification.NotificationService;
import app.service.user.IUserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.stream.Collectors;

import static app.util.Constants.*;
import static java.lang.String.format;

public class RatingService implements IRatingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RatingService.class);

    private final IRatingRepository ratingRepository;
    private final IUserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public RatingService(IRatingRepository ratingRepository, IUserService userService, ModelMapper modelMapper) {
        this.ratingRepository = ratingRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<RatingAllPropertiesDto> findAll() throws ServiceException {
        LOGGER.info(GET_ALL_RATINGS_MESSAGE);

        try {
            return ratingRepository
                    .findAll()
                    .stream()
                    .map(message -> modelMapper.map(message, RatingAllPropertiesDto.class))
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }

    @Override
    public RatingAllPropertiesDto findOneById(String id) throws ServiceException, RatingNotFoundException {
        LOGGER.info(format(FIND_RATING_BY_ID_MESSAGE, id));

        return modelMapper.map(findRatingByID(id), RatingAllPropertiesDto.class);
    }

    @Override
    public void delete(String id) throws ServiceException {
        LOGGER.info(format(DELETE_RATING_BY_ID_MESSAGE, id));

        try {
            ratingRepository.findById(id).ifPresent(ratingRepository::delete);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(CONFLICT_DELETE_MESSAGE);
            throw new ConflictException(CONFLICT_DELETE_MESSAGE);
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }

    @Override
    //TODO check if fromuser is equal loggedInUser
    public RatingAllPropertiesDto create(RatingAllPropertiesDto rating) throws ServiceException {
        LOGGER.info(format(CREATE_RATING_MESSAGE, rating.getComment()));

        RatingEntity ratingToBeCreated = modelMapper.map(rating,RatingEntity.class);

        UserEntity fromUser = userService.findUserById(rating.getFromUser().getId());
        UserEntity toUser = userService.findUserById(rating.getToUser().getId());

        ratingToBeCreated.setFromUser(fromUser);
        ratingToBeCreated.setToUser(toUser);


        //TODO create and send notification to toUser

        return modelMapper.map(createRating(ratingToBeCreated),RatingAllPropertiesDto.class);
    }

    @Override
    public RatingAllPropertiesDto update(RatingDto rating, String id) throws ServiceException, RatingNotFoundException {
        LOGGER.info(format(UPDATE_RATING_BY_ID_MESSAGE, id));

        RatingEntity ratingToBeUpdated = modelMapper.map(rating,RatingEntity.class);

        RatingEntity ratingInDb = findRatingByID(id);

        ratingToBeUpdated.setId(ratingInDb.getId());
        ratingToBeUpdated.setFromUser(ratingInDb.getFromUser());
        ratingToBeUpdated.setToUser(ratingToBeUpdated.getToUser());

        return modelMapper.map(createRating(ratingInDb),RatingAllPropertiesDto.class);
    }

    @Override
    public RatingEntity findRatingByID(String id) throws RatingNotFoundException {
        try {
            return ratingRepository
                    .findById(id)
                    .orElseThrow(() -> new RatingNotFoundException(format(RATING_NOT_FOUND_MESSAGE, id)));
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }

    private RatingEntity createRating(RatingEntity rating) {
        try {
            return ratingRepository.save(rating);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(CONFLICT_CREATE_MESSAGE);
            throw new AlreadyExistingResourceException(EXISTING_RESOURCE_MESSAGE);
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE, e);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }


}
