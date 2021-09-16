package app.service.search;

import app.dto.message.MessageAllPropertiesDto;
import app.entity.SearchEntity;
import app.exception.declarations.common.AlreadyExistingResourceException;
import app.exception.declarations.common.ConflictException;
import app.exception.declarations.common.ServiceException;
import app.exception.declarations.search.SearchNotFoundException;
import app.repository.ISearchRepository;

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

public class SearchService implements ISearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchService.class);

    private final ISearchRepository searchRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SearchService(ISearchRepository searchRepository, ModelMapper modelMapper) {
        this.searchRepository = searchRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<SearchEntity> findAll() throws ServiceException {
        LOGGER.info(GET_ALL_SEARCHES_MESSAGE);

        try {
            return  searchRepository
                    .findAll()
                    .stream()
                    .map(search -> modelMapper.map(search, SearchEntity.class))
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }

    @Override
    public SearchEntity findOneById(String id) throws ServiceException, SearchNotFoundException {
        LOGGER.info(format(FIND_SEARCH_BY_ID_MESSAGE, id));

        return modelMapper.map(findSearchById(id), SearchEntity.class);
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
    public SearchEntity create(SearchEntity search) throws ServiceException {
        LOGGER.info(format(CREATE_SEARCH_MESSAGE, search.getDescription()));

        return createSearch(search);
    }

    @Override
    public SearchEntity update(SearchEntity search, String id) throws ServiceException, SearchNotFoundException {
        LOGGER.info(format(UPDATE_SEARCH_BY_ID_MESSAGE, search.getId()));


        SearchEntity searchInDb  = findSearchById(id);
        search.setId(search.getId());

        return createSearch(search);
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


    private SearchEntity findSearchById(String id) {
        try {
            return searchRepository
                    .findById(id)
                    .orElseThrow(() -> new SearchNotFoundException(format(SEARCH_NOT_FOUND_MESSAGE, id)));
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }
}
