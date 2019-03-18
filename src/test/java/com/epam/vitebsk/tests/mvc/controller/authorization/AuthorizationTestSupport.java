package com.epam.vitebsk.tests.mvc.controller.authorization;

import org.junit.Before;
import org.mockito.Mock;

import com.epam.vitebsk.service.UserService;
import com.epam.vitebsk.tests.mvc.controller.ControllerTestSupport;

public class AuthorizationTestSupport extends ControllerTestSupport {

	public static String USERNAME_PARAMETR = "username";
	public static String PASSWORD_PARAMETR = "password";
	public static String DISPLAY_NAME_PARAMETR = "displayName";
	public static String CONFIRM_PARAMETR = "confirm";
	
	public static String SMALL_PASSWORD = "small";

	@Mock
	protected UserService service;

	@Before
	public void setUp() {
		super.setUp();
		when(serviceFactory.getUserService()).thenReturn(service);
	}
}
