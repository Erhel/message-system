package com.epam.vitebsk.mvc.controller.message;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Arrays;
import com.epam.vitebsk.entity.User;
import com.epam.vitebsk.mvc.Response;
import com.epam.vitebsk.entity.Message;

public class MessageListControllerTest extends MessageTestSupport {

	private User user;
	private List<Message> receivedMessages;
	private List<Message> sentMessages;
	
	@Before
	public void setUp() {
		super.setUp();
		controller = new MessageListController();
		user = new User(1L, "andrey.koval@mail.ru", "simple", "Andrey");
		receivedMessages = new ArrayList<Message>();
		sentMessages = new ArrayList<Message>();
	}

	@Test
	public void test1() {
		when(session.getAttribute("user")).thenReturn(user);
		when(messageService.findMessagesByRecipientId(user.getId())).thenReturn(receivedMessages);
		when(messageService.findMessagesBySenderId(user.getId())).thenReturn(sentMessages);
		
		Response response = controller.handle(req, resp, serviceFactory);
		
		verify(req, times(1)).setAttribute(eq("receivedMessages"), anyCollectionOf(ArrayList.class));
		verify(req, times(1)).setAttribute(eq("sentMessages"), anyCollectionOf(ArrayList.class));
		
		assertThat(response).isNull();
		
	}
}
