package com.epam.vitebsk.service;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
    
    public List<Message> findMessagesByRecipientId(Long id) {

    	MessageDao dao = getDao();
    	
    	List<Message> messages = dao.readByRecipientId(id);
    	
    	for (Message message : messages) {
    		User sender = userDao.read(message.getSender().getId());
    		message.setSender(sender);
    	}
    	
    	return messages;
    }

    @Override
    public void save(Message message) {
        User recipient = userDao.readByUsername(message.getRecipient().getUsername());
        MessageDao dao = getDao();
        message.setRecipient(recipient);
        
        dao.create(message);
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

    public Set<String> findUsernamesByUserId(Long id) {

        MessageDao dao = getDao();
        
        List<Message> receivedMessages = dao.readByRecipientId(id);
        List<Message> sentMessages = dao.readBySenderId(id);
        
        Set<Long> userIds = new TreeSet<Long>();
        Set<String> usernames = new TreeSet<String>();
        
        for (Message message : receivedMessages) {
            Long userId = message.getSender().getId();
            userIds.add(userId);
        }
        
        for (Message message : sentMessages) {
            Long userId = message.getRecipient().getId();
            userIds.add(userId);
        }
        
        for (Long userId : userIds) {
            User user = userDao.read(userId);
            usernames.add(user.getUsername());
        }
        
        return usernames;
    }
}
