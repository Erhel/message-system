package com.epam.vitebsk.tests.mvc.controller.authorization;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.Before;
import org.junit.Test;

import com.epam.vitebsk.mvc.Response;
import com.epam.vitebsk.mvc.controller.authorization.RegistrationController;

public class RegistrationControllerTest extends AuthorizationTestSupport {

	private Response responseToRegistrationPage;
	
	private static String LARGE_PASSWORD = RandomString.make(129);
	private static String LARGE_DISPLAY_NAME = RandomString.make(129);
	private static String LARGE_USERNAME = RandomString.make(257);
	private static String WRONG_PASSWORD = "wrongPassword";
	
	@Before
	public void setUp() {
		super.setUp();
		when(req.getSession()).thenReturn(session);
		when(req.getParameter(DISPLAY_NAME_PARAMETR)).thenReturn(DISPLAY_NAME);
		when(req.getParameter(USERNAME_PARAMETR)).thenReturn(USERNAME);
		when(req.getParameter(PASSWORD_PARAMETR)).thenReturn(PASSWORD);
		when(req.getParameter(CONFIRM_PARAMETR)).thenReturn(PASSWORD);
		
		controller = new RegistrationController();
		responseToRegistrationPage = new Response(TO_REGISTRATION_PAGE);
	}

	@Test
	public void forwardToRegistrationPageWhenDisplayNameNullTest() {
		doReturn(null).when(req).getParameter(DISPLAY_NAME_PARAMETR);

		Response response = controller.handle(req, resp, serviceFactory);
		
		verify(serviceFactory, never()).getUserService();
		verify(service, never()).save(any());

		assertThat(response).isNull();
	}
	
	@Test
	public void forwardToRegistrationPageWhenUsernameNullTest() {
		doReturn(null).when(req).getParameter(USERNAME_PARAMETR);
		
		Response response = controller.handle(req, resp, serviceFactory);
		
		verify(serviceFactory, never()).getUserService();
		verify(service, never()).save(any());
		
		assertThat(response).isNull();
	}

	@Test
	public void forwardToRegistrationPageWhenPasswordNullTest() {
		doReturn(null).when(req).getParameter(PASSWORD_PARAMETR);

		Response response = controller.handle(req, resp, serviceFactory);
		
		verify(serviceFactory, never()).getUserService();
		verify(service, never()).save(any());

		assertThat(response).isNull();
	}
	
	@Test
	public void forwardToRegistrationPageWhenConfirmNullTest() {
		doReturn(null).when(req).getParameter(CONFIRM_PARAMETR);

		Response response = controller.handle(req, resp, serviceFactory);
		
		verify(serviceFactory, never()).getUserService();
		verify(service, never()).save(any());
		
		assertThat(response).isNull();
	}
	
	@Test
	public void smallPasswordTest() {
		doReturn(SMALL_PASSWORD).when(req).getParameter(PASSWORD_PARAMETR);
		
		Response response = controller.handle(req, resp, serviceFactory);
		
		verify(session, times(1)).setAttribute(anyString(), anyString());
		verify(serviceFactory, never()).getUserService();
		verify(service, never()).save(any());
		
		assertThat(response).isEqualToComparingFieldByField(responseToRegistrationPage);
	}
	
	@Test
	public void largePasswordTest() {
		doReturn(LARGE_PASSWORD).when(req).getParameter(PASSWORD_PARAMETR);

		Response response = controller.handle(req, resp, serviceFactory);
		
		verify(session, times(1)).setAttribute(anyString(), anyString());
		verify(serviceFactory, never()).getUserService();
		verify(service, never()).save(any());
		
		assertThat(response).isEqualToComparingFieldByField(new Response(TO_REGISTRATION_PAGE));
	}
	
	@Test
	public void largeDisplayNameTest() {
		doReturn(LARGE_DISPLAY_NAME).when(req).getParameter(DISPLAY_NAME_PARAMETR);

		Response response = controller.handle(req, resp, serviceFactory);

		verify(session, times(1)).setAttribute(anyString(), anyString());
		verify(serviceFactory, never()).getUserService();
		verify(service, never()).save(any());
		
		assertThat(response).isEqualToComparingFieldByField(responseToRegistrationPage);
	}
	
	@Test
	public void largeUsernameTest() {
		doReturn(LARGE_USERNAME).when(req).getParameter(USERNAME_PARAMETR);

		Response response = controller.handle(req, resp, serviceFactory);

		verify(session, times(1)).setAttribute(anyString(), anyString());
		verify(serviceFactory, never()).getUserService();
		verify(service, never()).save(any());
		
		assertThat(response).isEqualToComparingFieldByField(responseToRegistrationPage);
	}
	
	@Test
	public void passwordAndConfirmNotMatchedTest() {
		doReturn(WRONG_PASSWORD).when(req).getParameter(CONFIRM_PARAMETR);

		Response response = controller.handle(req, resp, serviceFactory);
		
		verify(session, times(1)).setAttribute(anyString(), anyString());
		verify(serviceFactory, never()).getUserService();
		verify(service, never()).save(any());
		
		assertThat(response).isEqualToComparingFieldByField(responseToRegistrationPage);
	}
	
	@Test
	public void successfullRegistrationTest() {
		Response response = controller.handle(req, resp, serviceFactory);
		
		verify(session, times(2)).setAttribute(anyString(), anyString());
		verify(serviceFactory, times(1)).getUserService();
		verify(service, times(1)).save(any());

		assertThat(response).isEqualToComparingFieldByField(new Response(TO_LOGIN_PAGE));
	}
}
