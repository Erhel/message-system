package com.epam.vitebsk.service;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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

    public void send(Message message) {
        save(message);
        String to = message.getRecipient().getUsername();
        String from = message.getSender().getUsername();
        String host = "localhost";
        int port = 25;
        
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "false");
        properties.put("mail.debug", "true");
        
        Session session = Session.getDefaultInstance(properties);
        
        javax.mail.Message msg = new MimeMessage(session);
        
        try {
			msg.setFrom(new InternetAddress(from));
			InternetAddress [] address = {new InternetAddress(to)};
			msg.setRecipients(javax.mail.Message.RecipientType.TO, address);
			msg.setSubject(message.getSubject());
			msg.setSentDate(new Date());
			
			msg.setText(message.getMessage());
			
			Transport.send(msg);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
        
        
        
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
