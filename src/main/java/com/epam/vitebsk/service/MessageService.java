package com.epam.vitebsk.service;

import java.util.ArrayList;
import java.util.List;

import com.epam.vitebsk.entity.Message;
import com.epam.vitebsk.entity.User;

public class MessageService {

	public List<Message> findMessagesBySenderId(Long id) {
		ArrayList<Message> messages = new ArrayList<>();
		User andrey = new User("andrey.koval@mail.ru", null, "andrey");
		User mike = new User("mike.lohan@mail.ru", null, "mike");
		messages.add(new Message("hello", "it's me", andrey, mike, 1L));
		messages.add(new Message("hello too", "i reply", mike, andrey, 2L));
		return messages;
	}
}
