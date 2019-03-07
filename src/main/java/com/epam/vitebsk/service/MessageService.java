package com.epam.vitebsk.service;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.epam.vitebsk.dao.MessageDao;
import com.epam.vitebsk.dao.UserDao;
import com.epam.vitebsk.entity.Message;
import com.epam.vitebsk.entity.User;

public class MessageService extends EntityService<Long, Message> {

    private UserDao userDao;

    public List<Message> findMessagesBySenderId(Long id) {
        MessageDao dao = getDao();
        
        List<Message> messages = dao.readBySenderId(id);
        
        for (Message message : messages) {
            User recipient = userDao.read(message.getRecipient().getId());
            message.setRecipient(recipient);
        }
        
        return messages;
    }

    public void send(Message message) {
        save(message);
        // TODO: do in another thread send by mail
    }
    
    public void send() {
        
    }

    @Override
    public void save(Message message) {
        User recipient = userDao.readByUsername(message.getRecipient().getUsername());
        MessageDao dao = getDao();
        message.setRecipient(recipient);
        
        if (message.getId() == null) {
            dao.create(message);
        } else {
            dao.update(message);
        }
    }
    
    public void schedulingRepeatedTask() {
        TimerTask task = new TimerTask() {
            
            @Override
            public void run() {
                send();
            }
        };
        
        Timer timer = new Timer();
        
        long delay = 1000L;
        long period = 1000L;
        timer.scheduleAtFixedRate(task, delay, period);
    }
    
    @Override
    public Message find(Long id) {
        MessageDao dao = getDao();
        Message message = dao.read(id);
        User recipient  = userDao.read(message.getRecipient().getId());
        message.setRecipient(recipient);
        return message;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

}
