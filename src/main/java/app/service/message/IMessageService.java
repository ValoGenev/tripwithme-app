package app.service.message;

import app.dto.message.MessageAllPropertiesDto;

import app.entity.MessageEntity;
import app.exception.declarations.common.ServiceException;
import app.exception.declarations.message.MessageNotFoundException;

import java.util.List;

public interface IMessageService {

    List<MessageAllPropertiesDto> findAll() throws ServiceException;

    MessageAllPropertiesDto findOneById(String id) throws ServiceException, MessageNotFoundException;

    void delete(String id) throws ServiceException;

    MessageAllPropertiesDto create(MessageAllPropertiesDto message) throws  ServiceException;

    MessageAllPropertiesDto update(MessageAllPropertiesDto message, String id) throws ServiceException,MessageNotFoundException;

    MessageEntity findMessageById(String id) throws MessageNotFoundException;
}
