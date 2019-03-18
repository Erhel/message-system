package com.epam.vitebsk.tests.mvc.controller.message;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.TreeSet;

import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;

import com.epam.vitebsk.entity.Message;
import com.epam.vitebsk.entity.User;
import com.epam.vitebsk.mvc.Response;
import com.epam.vitebsk.mvc.controller.message.MessageEditController;

public class MessageEditControllerTest extends MessageTestSupport {

	private User user;
	private User user2;
	private User user3;
	private Set<String> usernames;
	private Message message;
	private Message message2;
	
	private static Long USER3_ID = 3L;
	private static String USERNAME3 = "tim.johans@mail.ru";
	
	private static String ID_PARAMETR = "id";

	@Before
	public void setUp() {
		super.setUp();
		controller = new MessageEditController();
		
		user = new User(USER_ID, USERNAME, PASSWORD, DISPLAY_NAME);
		user2 = new User(USER2_ID, USERNAME2, "", "");
		user3 = new User(USER3_ID, USERNAME3, "", "");
		
		usernames = new TreeSet<String>(Arrays.nonNullElementsIn(Arrays.array(USERNAME2, USERNAME3)));
		
		message = new Message(MESSAGE_ID, "", "", user, user2);
		message2 = new Message(MESSAGE2_ID, "", "", user2, user3);
		
		when(session.getAttribute(USER_ATTRIBUTE)).thenReturn(user);
		when(messageService.findUsernamesByUserId(anyLong())).thenReturn(usernames);
	}

	@Test
	public void writeNewMessageTest() {
		when(req.getParameter(ID_PARAMETR)).thenReturn(null);
		when(session.getAttribute(MESSAGE_ATTRIBUTE)).thenReturn(null);
		
		Response response = controller.handle(req, resp, serviceFactory);
		
        verify(serviceFactory, times(1)).getMessageService();
        verify(messageService, times(1)).findUsernamesByUserId(anyLong());
        verify(req, times(1)).setAttribute(eq(USERNAMES_ATTRIBUTE), anyCollectionOf(TreeSet.class));
        verify(messageService, never()).find(anyLong());
        verify(req, never()).setAttribute(eq(MESSAGE_ATTRIBUTE), any(Message.class));
		
		assertThat(response).isNull();
	}
	
	@Test
	public void incorrectMessageTest() {
		when(req.getParameter(ID_PARAMETR)).thenReturn(null);
		when(session.getAttribute(MESSAGE_ATTRIBUTE)).thenReturn(message);
		
		Response response = controller.handle(req, resp, serviceFactory);
		
        verify(serviceFactory, times(1)).getMessageService();
        verify(messageService, times(1)).findUsernamesByUserId(anyLong());
        verify(req, times(1)).setAttribute(eq(USERNAMES_ATTRIBUTE), anyCollectionOf(TreeSet.class));
        verify(messageService, never()).find(anyLong());
        verify(req, times(1)).setAttribute(eq(MESSAGE_ATTRIBUTE), any(Message.class));
		
		assertThat(response).isNull();
	}
	
	@Test
	public void viewMessageTest() {
		when(req.getParameter(ID_PARAMETR)).thenReturn(MESSAGE_ID.toString());
		when(messageService.find(anyLong())).thenReturn(message);
		
		Response response = controller.handle(req, resp, serviceFactory);
		
        verify(serviceFactory, times(1)).getMessageService();
        verify(messageService, times(1)).findUsernamesByUserId(anyLong());
        verify(req, times(1)).setAttribute(eq(USERNAMES_ATTRIBUTE), anyCollectionOf(TreeSet.class));
        verify(messageService, times(1)).find(anyLong());
        verify(req, times(1)).setAttribute(eq(MESSAGE_ATTRIBUTE), any(Message.class));
		
		assertThat(response).isNull();
	}
	
	@Test
	public void viewSomeoneElseMessageTest() {
		when(req.getParameter(ID_PARAMETR)).thenReturn(MESSAGE2_ID.toString());
		when(messageService.find(anyLong())).thenReturn(message2);
		
		Response response = controller.handle(req, resp, serviceFactory);
		
        verify(serviceFactory, times(1)).getMessageService();
        verify(messageService, times(1)).findUsernamesByUserId(anyLong());
        verify(req, times(1)).setAttribute(eq(USERNAMES_ATTRIBUTE), anyCollectionOf(TreeSet.class));
        verify(messageService, times(1)).find(anyLong());
        verify(req, never()).setAttribute(eq(MESSAGE_ATTRIBUTE), any(Message.class));
		
		assertThat(response).isEqualToComparingFieldByField(new Response(TO_LIST_PAGE));
	}

}
