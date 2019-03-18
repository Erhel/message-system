package com.epam.vitebsk.tests.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.epam.vitebsk.dao.MessageDao;
import com.epam.vitebsk.dao.UserDao;
import com.epam.vitebsk.entity.Message;
import com.epam.vitebsk.entity.User;
import com.epam.vitebsk.service.MessageService;

public class MessageServiceTest extends ServiceTestSupport<Long, Message> {

	private static Long USER2_ID = 2L;
	private static String USERNAME2 = "mike.lohan@mail.ru";
	private static Long MESSAGE2_ID = 2L;
	private static Long MESSAGE3_ID = 3L;
	
	private MessageService messageService;

	private Message message;
	private Message message2;
	private Message message3;
	private User user;
	private User user2;

	private List<Message> receivedMessages;
	private List<Message> sentMessages;
	
	@Mock
	private MessageDao messageDao;

	@Mock
	private UserDao userDao;

	@Before
	public void setUp() {
		super.setUp();
		messageService = new MessageService();
		messageService.setDao(messageDao);
		messageService.setUserDao(userDao);

		user = new User(USER_ID, USERNAME, PASSWORD, DISPLAY_NAME);
		user2 = new User(USER2_ID, USERNAME2, "", "");
		message = new Message(MESSAGE_ID, SUBJECT, TEXT, user, user);
		message2 = new Message(MESSAGE2_ID, "", "", user2, user);
		message3 = new Message(MESSAGE3_ID, "", "", user2, user2);
		
		sentMessages = new ArrayList<Message>();
		sentMessages.add(message2);
		sentMessages.add(message3);
		
		receivedMessages = new ArrayList<Message>();
		receivedMessages.add(message);
		receivedMessages.add(message2);
	}

	@Test
	public void findMessagesByRecipientIdTest() {
		when(messageDao.readByRecipientId(USER_ID)).thenReturn(receivedMessages);
		when(userDao.read(anyLong())).thenReturn(user, user2);
		
		List<Message> actual = messageService.findMessagesByRecipientId(USER_ID);
		
		verify(messageDao, times(1)).readByRecipientId(anyLong());
		verify(userDao, times(actual.size())).read(anyLong());
		
		assertThat(actual).allMatch(i -> i.getSender().getUsername()!=null);
	}
	
	@Test
	public void findMessagesBySenderIdTest() {
		when(messageDao.readBySenderId(USER2_ID)).thenReturn(sentMessages);
		when(userDao.read(anyLong())).thenReturn(user, user2);
		
		List<Message> actual = messageService.findMessagesBySenderId(USER2_ID);
		
		verify(messageDao, times(1)).readBySenderId(anyLong());
		verify(userDao, times(actual.size())).read(anyLong());
		
		assertThat(actual).allMatch(i -> i.getRecipient().getUsername()!=null);
	}
	
	@Test
	public void saveMessageTest() {
		when(userDao.readByUsername(USERNAME)).thenReturn(user);
		
		messageService.save(message2);
		
		verify(userDao, times(1)).readByUsername(anyString());
		verify(messageDao, times(1)).create(any(Message.class));	
	}
	
	@Test
	public void findMessageTest() {
		when(messageDao.read(MESSAGE_ID)).thenReturn(message);
		when(userDao.read(USER_ID)).thenReturn(user);
		
		Message actual = messageService.find(MESSAGE_ID);
		
		verify(messageDao, times(1)).read(anyLong());
		verify(userDao, times(1)).read(anyLong());
		
		assertThat(actual.getRecipient().getUsername()).isNotNull();		
	}
	
	@Test
	public void findUsernamesByUserIdTest() {
		List<Message> sent = new ArrayList<Message>();
		sent.add(message);
		when(messageDao.readByRecipientId(USER_ID)).thenReturn(receivedMessages);
		when(messageDao.readBySenderId(USER_ID)).thenReturn(sent);
		when(userDao.read(anyLong())).thenReturn(user, user2);
		
		Set<String> actual = messageService.findUsernamesByUserId(USER_ID);
		
		verify(messageDao, times(1)).readByRecipientId(anyLong());
		verify(messageDao, times(1)).readBySenderId(anyLong());
		verify(userDao, times(2)).read(anyLong());
		
		Set<String> expected = new TreeSet<String>();
		expected.add(USERNAME);
		expected.add(USERNAME2);
		
		assertThat(actual).isEqualTo(expected);
	}
}
