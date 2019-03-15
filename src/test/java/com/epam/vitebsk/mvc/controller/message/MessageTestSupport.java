package com.epam.vitebsk.mvc.controller.message;

import org.junit.Before;
import org.mockito.Mock;

import com.epam.vitebsk.mvc.controller.ControllerTest;
import com.epam.vitebsk.service.MessageService;

public class MessageTestSupport extends ControllerTest {
	
	@Mock
	MessageService messageService;
	
	@Before
    public void setUp() {
        super.setUp();
        when(serviceFactory.getMessageService()).thenReturn(messageService);
    }

}
