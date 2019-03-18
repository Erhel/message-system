package com.epam.vitebsk.tests.mvc;

import static org.assertj.core.api.Assertions.assertThat;

import javax.servlet.ServletException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.epam.vitebsk.mvc.Controller;
import com.epam.vitebsk.mvc.ControllerFactory;
import com.epam.vitebsk.mvc.controller.authorization.LoginController;

public class ControllerFactoryTest extends Mockito {

	private static String LOGIN_PATH = "/authorization/login";
	private static String WRONG_PATH = "/authorization/signup";
	
	@Mock
	Controller controller;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getControllerTest() throws ServletException {
		Controller controller = ControllerFactory.getController(LOGIN_PATH);
		
		assertThat(controller).isExactlyInstanceOf(LoginController.class);
	}
	
	@Ignore
	public void exceptionWhenGetControllerTest() {		
		try {
			ControllerFactory.getController(WRONG_PATH);
		} catch (ServletException e) {
			assertThat(e).isExactlyInstanceOf(ServletException.class);
			assertThat(e.getMessage()).hasToString(anyString());
		}
	}
}
