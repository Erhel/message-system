package com.epam.vitebsk.dao;

import java.util.List;

import com.epam.vitebsk.entity.Message;

public interface MessageDao extends CrudDao<Long, Message>{
    
    List<Message> readBySenderId(Long id);

	List<Message> readByRecipientId(Long id);

}
