package app.service.search;

import app.entity.SearchEntity;
import app.exception.declarations.search.SearchNotFoundException;
import app.exception.declarations.common.ServiceException;

import java.util.List;

public interface ISearchService {
    List<SearchEntity> findAll() throws ServiceException;

    SearchEntity findOneById(String id) throws ServiceException, SearchNotFoundException;

    void delete(String id) throws ServiceException;

    SearchEntity create(SearchEntity search) throws  ServiceException;

    SearchEntity update(SearchEntity search, String id) throws ServiceException,SearchNotFoundException;
}
