package com.epam.vitebsk.tests.mvc.controller.message;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.epam.vitebsk.entity.Message;
import com.epam.vitebsk.entity.User;
import com.epam.vitebsk.mvc.Response;
import com.epam.vitebsk.mvc.controller.message.MessageListController;

public class MessageListControllerTest extends MessageTestSupport {
	
	private User user;
	private List<Message> receivedMessages;
	private List<Message> sentMessages;
	
	@Before
	public void setUp() {
		super.setUp();
		controller = new MessageListController();
		user = new User(USER_ID, USERNAME, PASSWORD, DISPLAY_NAME);
		receivedMessages = new ArrayList<Message>();
		sentMessages = new ArrayList<Message>();
	}

	@Test
	public void listMessagesTest() {
		when(session.getAttribute(USER_ATTRIBUTE)).thenReturn(user);
		when(messageService.findMessagesByRecipientId(USER_ID)).thenReturn(receivedMessages);
		when(messageService.findMessagesBySenderId(USER_ID)).thenReturn(sentMessages);
		
		Response response = controller.handle(req, resp, serviceFactory);
		
		verify(serviceFactory, times(1)).getMessageService();
		verify(req, times(2)).setAttribute(anyString(), anyCollectionOf(ArrayList.class));
		
		assertThat(response).isNull();
		
	}
}
