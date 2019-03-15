package com.epam.vitebsk.tests.mvc.controller.message;

import org.junit.Before;
import org.mockito.Mock;

import com.epam.vitebsk.service.MessageService;
import com.epam.vitebsk.tests.mvc.controller.ControllerTest;

public class MessageTestSupport extends ControllerTest {
	
	@Mock
	MessageService messageService;
	
	@Before
    public void setUp() {
        super.setUp();
        when(serviceFactory.getMessageService()).thenReturn(messageService);
    }

}
