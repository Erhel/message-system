package com.epam.vitebsk.service;

import java.util.List;

import com.epam.vitebsk.entity.Message;
import com.epam.vitebsk.entity.User;

public class MessageService extends Service<Message> {

	public List<Message> findMessagesBySenderId(Long id) {
//		ArrayList<Message> messages = new ArrayList<>();
//		User andrey = new User("andrey.koval@mail.ru", null, "andrey", 1L);
//		User mike = new User("mike.lohan@mail.ru", null, "mike", 2L);
//		messages.add(new Message("hello", "it's me", andrey, mike, 1L));
//		messages.add(new Message("hello too", "i reply", mike, andrey, 2L));
	    
		return null;
	}

	public Message getById(Long id) {
		User andrey = new User("andrey.koval@mail.ru", null, "andrey", 1L);
		User mike = new User("mike.lohan@mail.ru", null, "mike", 2L);
		return new Message("hello", "it's me", andrey, mike, 1L); 
	}

    public void save(Message message) {
        
    }

    public void send(Message message) {
        
    }
}
