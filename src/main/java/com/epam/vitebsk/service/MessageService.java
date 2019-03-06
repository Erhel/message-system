package com.epam.vitebsk.service;

import java.util.List;

import com.epam.vitebsk.dao.MessageDao;
import com.epam.vitebsk.entity.Message;

public class MessageService extends EntityService<Long, Message> {

    public List<Message> findMessagesBySenderId(Long id) {
        MessageDao dao = getDao();

        return dao.readBySenderId(id);
    }

    public void send(Message message) {
        save(message);
        // TODO: send by mail
    }

}
