package com.epam.vitebsk.mvc.controller.authorization;

import org.junit.Before;
import org.mockito.Mock;

import com.epam.vitebsk.mvc.controller.ControllerTest;
import com.epam.vitebsk.service.UserService;

public class AuthorizationTestSupport extends ControllerTest {
    
    @Mock
    UserService service;
    
    @Before
    public void setUp() {
        super.setUp();
        when(factory.getUserService()).thenReturn(service);
    }
       
}