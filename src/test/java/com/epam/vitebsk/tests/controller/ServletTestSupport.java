package com.epam.vitebsk.tests.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public abstract class ServletTestSupport extends Mockito {

    public static final String CONTEXT_PATH = "/message-system";
    public static final String LOGIN_REQUEST_URI = "/message-system/authorization/login.html";
    public static final String REGISTRATION_REQUEST_URI = "/message-system/authorization/registration.html";
    public static final String INACCESSIBLE_REQUEST_URI = "/message-system/message/list.html";

    public static final String USERNAME = "andrey.koval@mail.ru";
    public static final String PASSWORD = "simplePassword";
    public static final String DISPLAY_NAME = "Andrey";
    public static final Long USER_ID = 1L;

    @Mock
    protected HttpSession session;

    @Mock
    protected HttpServletRequest req;

    @Mock
    protected HttpServletResponse resp;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

}
