package com.epam.vitebsk.tests.mvc.controller.authorization;

import org.junit.Before;
import org.mockito.Mock;

import com.epam.vitebsk.service.UserService;
import com.epam.vitebsk.tests.mvc.controller.ControllerTest;

public class AuthorizationTestSupport extends ControllerTest {
    
    @Mock
    UserService service;
    
    @Before
    public void setUp() {
        super.setUp();
        when(serviceFactory.getUserService()).thenReturn(service);
    }
}
