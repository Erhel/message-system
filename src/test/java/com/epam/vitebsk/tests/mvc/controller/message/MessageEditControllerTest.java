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
	private Set<String> usernames;
	private Message message;
	
	private static Long USER1_ID = 1L;

	@Before
	public void setUp() {
		super.setUp();
		controller = new MessageEditController();
		user = new User(1L, "andrey.koval@mail.ru", "simple", "Andrey");
		usernames = new TreeSet<String>(Arrays.nonNullElementsIn(Arrays.array("mike.lohan@mail.ru", "tim.johans@mail.ru")));
		message = new Message(USER1_ID, "test", "mess", user, new User(2L, "mike.lohan@mail.ru", null , null));
		when(session.getAttribute("user")).thenReturn(user);
	}

	@Test
	public void test1() {
		when(req.getParameter("id")).thenReturn(null);
		when(session.getAttribute("message")).thenReturn(null);
		when(messageService.findUsernamesByUserId(anyLong())).thenReturn(usernames);
		
		Response response = controller.handle(req, resp, serviceFactory);
		
        verify(serviceFactory, times(1)).getMessageService();
        verify(messageService, times(1)).findUsernamesByUserId(anyLong());
        verify(req, times(1)).setAttribute(eq("usernames"), anyCollectionOf(TreeSet.class));
		
		assertThat(response).isNull();
	}
	
	@Test
	public void test2() {
		when(req.getParameter("id")).thenReturn(null);
		when(session.getAttribute("message")).thenReturn(message);
		when(messageService.findUsernamesByUserId(anyLong())).thenReturn(usernames);
		
		Response response = controller.handle(req, resp, serviceFactory);
		
        verify(serviceFactory, times(1)).getMessageService();
        verify(messageService, times(1)).findUsernamesByUserId(anyLong());
        verify(req, times(1)).setAttribute(eq("usernames"), anyCollectionOf(TreeSet.class));
        verify(req, times(1)).setAttribute(eq("message"), any(Message.class));
		
		assertThat(response).isNull();
	}
	
	@Test
	public void test3() {
		when(req.getParameter("id")).thenReturn(message.getId().toString());
		when(messageService.findUsernamesByUserId(anyLong())).thenReturn(usernames);
		when(messageService.find(anyLong())).thenReturn(message);
		
		Response response = controller.handle(req, resp, serviceFactory);
		
        verify(serviceFactory, times(1)).getMessageService();
        verify(messageService, times(1)).findUsernamesByUserId(anyLong());
        verify(req, times(1)).setAttribute(eq("usernames"), anyCollectionOf(TreeSet.class));
        verify(req, times(1)).setAttribute(eq("message"), any(Message.class));
		
		assertThat(response).isNull();
	}
	
	@Test
	public void test4() {
		when(req.getParameter("id")).thenReturn("222");
		when(messageService.findUsernamesByUserId(anyLong())).thenReturn(usernames);
		when(messageService.find(anyLong())).thenReturn(new Message(222L, "", "", new User(2L, "", "", ""), new User(3L, "", "", "")));
		
		Response response = controller.handle(req, resp, serviceFactory);
		
        verify(serviceFactory, times(1)).getMessageService();
        verify(messageService, times(1)).findUsernamesByUserId(anyLong());
        verify(req, times(1)).setAttribute(eq("usernames"), anyCollectionOf(TreeSet.class));
        verify(req, never()).setAttribute(eq("message"), any(Message.class));
		
		assertThat(response).isEqualToComparingFieldByField(new Response("/message/list.html"));
	}

}
