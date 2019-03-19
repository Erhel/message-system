package com.epam.vitebsk.tests.controller.controllers.authorization;

import org.junit.Before;
import org.mockito.Mock;

import com.epam.vitebsk.controller.service.UserService;
import com.epam.vitebsk.tests.controller.controllers.ControllerTestSupport;

public class AuthorizationTestSupport extends ControllerTestSupport {

    public static final String SMALL_PASSWORD = "small";

    @Mock
    protected UserService service;

    @Before
    public void setUp() {
        super.setUp();
        when(serviceFactory.getUserService()).thenReturn(service);
    }
}
