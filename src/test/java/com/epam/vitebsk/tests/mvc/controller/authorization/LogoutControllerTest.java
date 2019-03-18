package com.epam.vitebsk.tests.mvc.controller.authorization;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.epam.vitebsk.mvc.Response;
import com.epam.vitebsk.mvc.controller.authorization.LogoutController;

public class LogoutControllerTest extends AuthorizationTestSupport {

	@Before
	public void setUp() {
		super.setUp();
		controller = new LogoutController();
	}
	
	@Test
	public void whenSessionExistTest() {
		
		when(req.getSession(false)).thenReturn(session);
		
		Response response = controller.handle(req, resp, serviceFactory);
		
		verify(session, times(1)).invalidate();
		
		assertThat(response).isEqualToComparingFieldByField(new Response(TO_LOGIN_PAGE));
	}
	
	@Test
	public void whenSessionNotExistTest() {
		
		when(req.getSession(false)).thenReturn(null);
		
		Response response = controller.handle(req, resp, serviceFactory);
		
		verify(session, never()).invalidate();
		
		assertThat(response).isEqualToComparingFieldByField(new Response(TO_LOGIN_PAGE));
	}

}
