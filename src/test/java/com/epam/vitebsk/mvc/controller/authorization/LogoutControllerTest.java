package com.epam.vitebsk.mvc.controller.authorization;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.epam.vitebsk.mvc.Response;

public class LogoutControllerTest extends AuthorizationTestSupport {

	@Before
	public void setUp() {
		super.setUp();
		controller = new LogoutController();
	}
	
	@Test
	public void test() {		
		Response response = controller.handle(req, resp, serviceFactory);
		
		assertThat(response).isEqualToComparingFieldByField(new Response("/authorization/login.html"));
	}

}
