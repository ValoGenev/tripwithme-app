package app.service.search;

import app.dto.search.SearchAllPropertiesDto;
import app.entity.SearchEntity;
import app.exception.declarations.search.SearchNotFoundException;
import app.exception.declarations.common.ServiceException;

import java.util.List;

public interface ISearchService {

    List<SearchAllPropertiesDto> findAll() throws ServiceException;

    SearchAllPropertiesDto findOneById(String id) throws ServiceException, SearchNotFoundException;

    void delete(String id) throws ServiceException;

    SearchAllPropertiesDto create(SearchAllPropertiesDto search) throws  ServiceException;

    SearchAllPropertiesDto update(SearchAllPropertiesDto search, String id) throws ServiceException,SearchNotFoundException;

    SearchEntity findSearchById(String id) throws SearchNotFoundException;
}
