package com.epam.vitebsk.mvc.controller.authorization;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.Before;
import org.junit.Test;

import com.epam.vitebsk.mvc.Response;

public class RegistrationControllerTest extends AuthorizationTestSupport {

	private String displayName = "Andrey";
	private String username = "andrey.koval@mail.ru";
	private String password = "simple";
	
	@Before
	public void setUp() {
		super.setUp();
		controller = new RegistrationController();
	}

	@Test
	public void test1() {

		when(req.getParameter("displayName")).thenReturn(null);
		when(req.getParameter("username")).thenReturn(username);
		when(req.getParameter("password")).thenReturn(password);
		when(req.getParameter("confirm")).thenReturn(password);

		Response response = controller.handle(req, resp, serviceFactory);
		assertThat(response).isNull();

		verify(serviceFactory, never()).getUserService();
		verify(service, never()).save(any());
	}
	
	@Test
	public void test2() {

		when(req.getParameter("displayName")).thenReturn(displayName);
		when(req.getParameter("username")).thenReturn(null);
		when(req.getParameter("password")).thenReturn(password);
		when(req.getParameter("confirm")).thenReturn(password);

		Response response = controller.handle(req, resp, serviceFactory);
		assertThat(response).isNull();

		verify(serviceFactory, never()).getUserService();
		verify(service, never()).save(any());
	}

	@Test
	public void test3() {

		when(req.getParameter("displayName")).thenReturn(displayName);
		when(req.getParameter("username")).thenReturn(username);
		when(req.getParameter("password")).thenReturn(null);
		when(req.getParameter("confirm")).thenReturn(password);

		Response response = controller.handle(req, resp, serviceFactory);
		assertThat(response).isNull();

		verify(serviceFactory, never()).getUserService();
		verify(service, never()).save(any());
	}
	
	@Test
	public void test4() {

		when(req.getParameter("displayName")).thenReturn(displayName);
		when(req.getParameter("username")).thenReturn(username);
		when(req.getParameter("password")).thenReturn(password);
		when(req.getParameter("confirm")).thenReturn(null);

		Response response = controller.handle(req, resp, serviceFactory);
		assertThat(response).isNull();

		verify(serviceFactory, never()).getUserService();
		verify(service, never()).save(any());
	}
	
	@Test
	public void test5() {

		when(req.getParameter("displayName")).thenReturn(displayName);
		when(req.getParameter("username")).thenReturn(username);
		when(req.getParameter("password")).thenReturn("small");
		when(req.getParameter("confirm")).thenReturn("small");

		Response response = controller.handle(req, resp, serviceFactory);
		assertThat(response).isEqualToComparingFieldByField(new Response("/authorization/registration.html?info=password should've at least 6 symbols"));

		verify(serviceFactory, never()).getUserService();
		verify(service, never()).save(any());
	}
	
	@Test
	public void test6() {

		when(req.getParameter("displayName")).thenReturn(displayName);
		when(req.getParameter("username")).thenReturn(username);
		when(req.getParameter("password")).thenReturn(RandomString.make(129));
		when(req.getParameter("confirm")).thenReturn(password);

		Response response = controller.handle(req, resp, serviceFactory);
		assertThat(response).isEqualToComparingFieldByField(new Response("/authorization/registration.html?info=password should've less than 128 symbols"));

		verify(serviceFactory, never()).getUserService();
		verify(service, never()).save(any());
	}
	
	@Test
	public void test7() {
		when(req.getParameter("displayName")).thenReturn(RandomString.make(129));
		when(req.getParameter("username")).thenReturn(username);
		when(req.getParameter("password")).thenReturn(password);
		when(req.getParameter("confirm")).thenReturn(password);

		Response response = controller.handle(req, resp, serviceFactory);
		assertThat(response).isEqualToComparingFieldByField(new Response("/authorization/registration.html?info=nickname should've less than 128 symbols"));

		verify(serviceFactory, never()).getUserService();
		verify(service, never()).save(any());
	}
	
	@Test
	public void test8() {
		when(req.getParameter("displayName")).thenReturn(displayName);
		when(req.getParameter("username")).thenReturn(RandomString.make(257));
		when(req.getParameter("password")).thenReturn(password);
		when(req.getParameter("confirm")).thenReturn(password);

		Response response = controller.handle(req, resp, serviceFactory);
		assertThat(response).isEqualToComparingFieldByField(new Response("/authorization/registration.html?info=email should've less than 256 symbols"));

		verify(serviceFactory, never()).getUserService();
		verify(service, never()).save(any());
	}
	
	@Test
	public void test9() {
		when(req.getParameter("displayName")).thenReturn(displayName);
		when(req.getParameter("username")).thenReturn(username);
		when(req.getParameter("password")).thenReturn(password);
		when(req.getParameter("confirm")).thenReturn("small");

		Response response = controller.handle(req, resp, serviceFactory);
		assertThat(response).isEqualToComparingFieldByField(new Response("/authorization/registration.html?info=password and confirm password are not matched"));

		verify(serviceFactory, never()).getUserService();
		verify(service, never()).save(any());
	}
	
	@Test
	public void test10() {
		when(req.getParameter("displayName")).thenReturn(displayName);
		when(req.getParameter("username")).thenReturn(username);
		when(req.getParameter("password")).thenReturn(password);
		when(req.getParameter("confirm")).thenReturn(password);

		Response response = controller.handle(req, resp, serviceFactory);
		assertThat(response).isEqualToComparingFieldByField(new Response("/authorization/login.html?success=you are successfully registered"));

		verify(serviceFactory, times(1)).getUserService();
		verify(service, times(1)).save(any());
	}
}
