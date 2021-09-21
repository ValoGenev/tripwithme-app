package app.service.search;

import app.dto.common.SearchTripCommonProperties;
import app.dto.message.MessageAllPropertiesDto;
import app.dto.search.SearchAllPropertiesDto;
import app.entity.SearchEntity;
import app.entity.TripEntity;
import app.entity.UserEntity;
import app.exception.declarations.common.AlreadyExistingResourceException;
import app.exception.declarations.common.ConflictException;
import app.exception.declarations.common.ServiceException;
import app.exception.declarations.search.OneSearchPerTimeIntervalException;
import app.exception.declarations.search.SearchNotFoundException;
import app.exception.declarations.trip.OneTripPerTimePeriodException;
import app.repository.ISearchRepository;

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
import java.util.stream.Collectors;

import static app.util.Constants.*;
import static java.lang.String.format;

public class SearchService implements ISearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchService.class);

    private final ISearchRepository searchRepository;
    private final IUserService userService;

    private final ModelMapper modelMapper;

    @Autowired
    public SearchService(ISearchRepository searchRepository, IUserService userService, ModelMapper modelMapper) {
        this.searchRepository = searchRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<SearchAllPropertiesDto> findAll() throws ServiceException {
        LOGGER.info(GET_ALL_SEARCHES_MESSAGE);

        try {
            return  searchRepository
                    .findAll()
                    .stream()
                    .map(search -> modelMapper.map(search, SearchAllPropertiesDto.class))
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }

    @Override
    public SearchAllPropertiesDto findOneById(String id) throws ServiceException, SearchNotFoundException {
        LOGGER.info(format(FIND_SEARCH_BY_ID_MESSAGE, id));

        return modelMapper.map(findSearchById(id), SearchAllPropertiesDto.class);
    }

    @Override
    public void delete(String id) throws ServiceException {
        LOGGER.info(format(DELETE_SEARCH_BY_ID_MESSAGE, id));

        try {
            searchRepository.findById(id).ifPresent(searchRepository::delete);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(CONFLICT_DELETE_MESSAGE);
            throw new ConflictException(CONFLICT_DELETE_MESSAGE);
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }

    @Override
    //TODO check if search.getId() is authenticated User
    public SearchAllPropertiesDto create(SearchAllPropertiesDto search) throws ServiceException {
        LOGGER.info(format(CREATE_SEARCH_MESSAGE, search.getDescription()));

        UserEntity searcher = userService.findUserById(search.getSearcher().getId());

        SearchEntity searchToBeCreated = modelMapper.map(search,SearchEntity.class);

        assertSearcherHasOneSearchAtTheSameTimePeriod(search,searcher);

        searchToBeCreated.setApplications(new ArrayList<>());
        searchToBeCreated.setTimePosted(LocalDateTime.now().withNano(0));
        searchToBeCreated.setSearcher(searcher);

        SearchEntity createdSearch = createSearch(searchToBeCreated);

        //TODO notify searcher if any trips matches this conditional

        return modelMapper.map(createdSearch,SearchAllPropertiesDto.class);
    }


    @Override
    //TODO check if search.getUser().getId() is authenticated User
    public SearchAllPropertiesDto update(SearchAllPropertiesDto search, String id) throws ServiceException, SearchNotFoundException {
        LOGGER.info(format(UPDATE_SEARCH_BY_ID_MESSAGE, search.getId()));

        SearchEntity searchInDb  = findSearchById(id);
        UserEntity searcher = searchInDb.getSearcher();

        SearchEntity searchToBeUpdated = modelMapper.map(search,SearchEntity.class);

        searchToBeUpdated.setId(searchInDb.getId());
        searchToBeUpdated.setTimePosted(searchInDb.getTimePosted());
        searchToBeUpdated.setApplications(searchInDb.getApplications());
        searchToBeUpdated.setSearcher(searcher);

        assertSearcherHasOneSearchAtTheSameTimePeriod(search,searcher);

        SearchEntity updatedSearch = createSearch(searchToBeUpdated);

        //TODO remove applications that don't match anymore this conditions
        // create new notification if any trips matches

        return modelMapper.map(updatedSearch,SearchAllPropertiesDto.class);
    }

    private SearchEntity createSearch(SearchEntity search) {
        try {
            return searchRepository.save(search);
        } catch (DataIntegrityViolationException e) {

            LOGGER.error(CONFLICT_CREATE_MESSAGE);
            throw new AlreadyExistingResourceException(EXISTING_RESOURCE_MESSAGE);
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE, e);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }


    @Override
    public SearchEntity findSearchById(String id) {
        try {
            return searchRepository
                    .findById(id)
                    .orElseThrow(() -> new SearchNotFoundException(format(SEARCH_NOT_FOUND_MESSAGE, id)));
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }

    private void assertSearcherHasOneSearchAtTheSameTimePeriod(SearchTripCommonProperties search, UserEntity searcher) {

        List<SearchEntity> searches = searcher.getSearches();

        LocalDateTime startDate = search.getStartTime();
        LocalDateTime endDate = search.getEndTime();

        searches.forEach(s->{
            LocalDateTime sStartTime = s.getStartTime();
            LocalDateTime sEndTime = s.getEndTime();

            if((startDate.compareTo(sStartTime) >= 0 && startDate.compareTo(sEndTime) <= 0) ||
                    (endDate.compareTo(sStartTime) >= 0 && endDate.compareTo(sEndTime) <=0)){
                if (!search.getId().equals(s.getId())) {
                    throw new OneSearchPerTimeIntervalException(ONE_SEARCH_PER_TIME_PERIOD_MESSAGE);
                }
            }
        });

    }
}
