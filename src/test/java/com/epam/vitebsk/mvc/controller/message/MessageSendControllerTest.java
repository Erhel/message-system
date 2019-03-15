package com.epam.vitebsk.mvc.controller.message;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;

import com.epam.vitebsk.entity.Message;
import com.epam.vitebsk.entity.User;
import com.epam.vitebsk.mvc.Response;
import com.epam.vitebsk.service.MailService;
import com.epam.vitebsk.service.UserService;

public class MessageSendControllerTest extends MessageTestSupport {

	private String username;
	private String subject;
	private String mess;
	private User user;
	private Message message;
	
	@Mock
	UserService userService;
	
	@Mock
	MailService mailService;
	
	@Before
	public void setUp() {
		super.setUp();
		controller = new MessageSendController();
		username = "mike.lohand@mail.ru";
		subject =  "test";
		mess = "testMessage";
		user = new User(1L, "andrey.koval@mail.ru", "simple", "Andrey");
		message = new Message(1L, subject, mess, user, new User(2L, username, "", ""));
	}

	@Test
	public void test1() {
		when(req.getAttribute("recipient")).thenReturn(null);
		when(req.getAttribute("subject")).thenReturn(subject);
		when(req.getAttribute("message")).thenReturn(mess);
		
		Response response = controller.handle(req, resp, serviceFactory);
		
		assertThat(response).isEqualToComparingFieldByField(new Response("/message/list.html"));
	}
	
	@Test
	public void test2() {
		when(req.getAttribute("recipient")).thenReturn(username);
		when(req.getAttribute("subject")).thenReturn(null);
		when(req.getAttribute("message")).thenReturn(mess);
		
		Response response = controller.handle(req, resp, serviceFactory);
		
		assertThat(response).isEqualToComparingFieldByField(new Response("/message/list.html"));
	}
	
	@Test
	public void test3() {
		when(req.getAttribute("recipient")).thenReturn(username);
		when(req.getAttribute("subject")).thenReturn(subject);
		when(req.getAttribute("message")).thenReturn(null);
		
		Response response = controller.handle(req, resp, serviceFactory);
		
		assertThat(response).isEqualToComparingFieldByField(new Response("/message/list.html"));
	}

	@Ignore
	public void test4() {
		when(req.getAttribute("recipient")).thenReturn(username);
		when(req.getAttribute("subject")).thenReturn(RandomString.make(257));
		when(req.getAttribute("message")).thenReturn(mess);
		
		Response response = controller.handle(req, resp, serviceFactory);
		
		verify(session, times(1)).setAttribute(eq("message"), any(Message.class));
		
		assertThat(response).isEqualToComparingFieldByField(new Response("/message/edit.html?info=subject should've less than 256 symbols"));
	}
	
	@Ignore
	public void test5() {
		when(req.getAttribute("recipient")).thenReturn(username);
		when(req.getAttribute("subject")).thenReturn(subject);
		when(req.getAttribute("message")).thenReturn(RandomString.make(1025));
		when(serviceFactory.getUserService()).thenReturn(userService);
		when(userService.findByUsername(anyString())).thenReturn(null);
		
		Response response = controller.handle(req, resp, serviceFactory);
		
		verify(session, times(1)).setAttribute(eq("message"), any(Message.class));
		
		assertThat(response).isEqualToComparingFieldByField(new Response("/message/edit.html?info=such user doesn't exists"));
	}
	
	@Ignore
	public void test6() {
		when(req.getAttribute("recipient")).thenReturn(username);
		when(req.getAttribute("subject")).thenReturn(subject);
		when(req.getAttribute("message")).thenReturn(mess);
		when(serviceFactory.getUserService()).thenReturn(userService);
		when(serviceFactory.getMailService()).thenReturn(mailService);
		when(userService.findByUsername(anyString())).thenReturn(user);
		doNothing().when(messageService).save(any(Message.class));
		
		Response response = controller.handle(req, resp, serviceFactory);
		
		verify(messageService, times(1)).save(any(Message.class));
		verify(mailService, times(1)).send(any(Message.class));
		
		assertThat(response).isEqualToComparingFieldByField(new Response("/message/list.html"));
	}
}
