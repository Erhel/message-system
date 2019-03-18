package com.epam.vitebsk.tests.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public abstract class MvcTestSupport extends Mockito {

	public static String CONTEXT_PATH = "/message-system";
	public static String LOGIN_REQUEST_URI = "/message-system/authorization/login.html";
	public static String REGISTRATION_REQUEST_URI = "/message-system/authorization/registration.html";
	public static String INACCESSIBLE_REQUEST_URI = "/message-system/message/list.html";
	
	public static String TO_LIST_PAGE = "/message/list.html";
	public static String TO_EDIT_PAGE = "/message/edit.html";
	public static String TO_LOGIN_PAGE = "/authorization/login.html";
	public static String TO_REGISTRATION_PAGE = "/authorization/registration.html";
	
	public static String USERNAME = "andrey.koval@mail.ru";
	public static String PASSWORD = "simplePassword";
	public static String DISPLAY_NAME = "Andrey";
	public static Long USER_ID = 1L;
	
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
