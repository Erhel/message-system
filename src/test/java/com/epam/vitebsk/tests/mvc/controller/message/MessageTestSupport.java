package com.epam.vitebsk.tests.mvc.controller.message;

import org.junit.Before;
import org.mockito.Mock;

import com.epam.vitebsk.service.MessageService;
import com.epam.vitebsk.tests.mvc.controller.ControllerTestSupport;

public class MessageTestSupport extends ControllerTestSupport {
	
	public static String USER_ATTRIBUTE = "user";
	public static String MESSAGE_ATTRIBUTE = "message";
	public static String USERNAMES_ATTRIBUTE = "usernames";
	
	public static Long MESSAGE_ID = 1L;
	public static String SUBJECT = "Greeting";
	public static String TEXT = "Hello";
	
	public static Long USER2_ID = 2L;
	public static String USERNAME2 = "mike.lohan@mail.ru";
	public static Long MESSAGE2_ID = 2L;
	
	@Mock
	MessageService messageService;
	
	@Before
    public void setUp() {
        super.setUp();
        when(serviceFactory.getMessageService()).thenReturn(messageService);
        when(req.getSession(anyBoolean())).thenReturn(session);
    }

}
