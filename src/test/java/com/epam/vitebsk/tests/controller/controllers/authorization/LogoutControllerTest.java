package com.epam.vitebsk.tests.controller.controllers.authorization;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.epam.vitebsk.controller.Response;
import com.epam.vitebsk.controller.controllers.authorization.LogoutController;
import com.epam.vitebsk.utils.Route;

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

        assertThat(response).isEqualToComparingFieldByField(new Response(Route.TO_LOGIN));
    }

    @Test
    public void whenSessionNotExistTest() {

        when(req.getSession(false)).thenReturn(null);

        Response response = controller.handle(req, resp, serviceFactory);

        verify(session, never()).invalidate();

        assertThat(response).isEqualToComparingFieldByField(new Response(Route.TO_LOGIN));
    }

}
