package com.epam.vitebsk.tests.controller.controllers.message;

import org.junit.Before;
import org.mockito.Mock;

import com.epam.vitebsk.controller.service.MessageService;
import com.epam.vitebsk.tests.controller.controllers.ControllerTestSupport;

public class MessageTestSupport extends ControllerTestSupport {

    public static final Long MESSAGE_ID = 1L;
    public static final String SUBJECT = "Greeting";
    public static final String TEXT = "Hello";

    public static final Long USER2_ID = 2L;
    public static final String USERNAME2 = "mike.lohan@mail.ru";
    public static final Long MESSAGE2_ID = 2L;

    @Mock
    MessageService messageService;

    @Before
    public void setUp() {
        super.setUp();
        when(serviceFactory.getMessageService()).thenReturn(messageService);
        when(req.getSession(anyBoolean())).thenReturn(session);
    }

}
