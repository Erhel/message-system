package com.epam.vitebsk.tests.controller.controllers.authorization;

import static com.epam.vitebsk.utils.StoredProperties.PASSWORD_PARAMETR;
import static com.epam.vitebsk.utils.StoredProperties.USERNAME_PARAMETR;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.epam.vitebsk.controller.Response;
import com.epam.vitebsk.controller.controllers.authorization.LoginController;
import com.epam.vitebsk.model.entity.User;
import com.epam.vitebsk.utils.Route;

public class LoginControllerTest extends AuthorizationTestSupport {

    private User user;

    @Before
    public void setUp() {
        super.setUp();
        controller = new LoginController();
        user = new User(USER_ID, USERNAME, PASSWORD, DISPLAY_NAME);

        when(req.getSession(false)).thenReturn(null);
        when(req.getSession()).thenReturn(session);

        when(req.getParameter(USERNAME_PARAMETR)).thenReturn(USERNAME);
        when(req.getParameter(PASSWORD_PARAMETR)).thenReturn(PASSWORD);
    }

    @Test
    public void successLogin() {
        when(service.findByLoginAndPassword(anyString(), anyString())).thenReturn(user);

        Response response = controller.handle(req, resp, serviceFactory);

        verify(serviceFactory, times(1)).getUserService();
        verify(service, times(1)).findByLoginAndPassword(anyString(), anyString());
        verify(session, times(1)).setAttribute(anyString(), eq(user));

        assertThat(response).isEqualToComparingFieldByField(new Response(Route.TO_MESSAGE_LIST));
    }

    @Test
    public void forwardToLoginPage() {
        doReturn(null).doReturn(USERNAME).when(req).getParameter(USERNAME_PARAMETR);
        doReturn(PASSWORD).doReturn(null).when(req).getParameter(PASSWORD_PARAMETR);

        Response response = controller.handle(req, resp, serviceFactory);

        assertThat(response).isNull();

        response = controller.handle(req, resp, serviceFactory);

        verify(serviceFactory, never()).getUserService();
        verify(service, never()).findByLoginAndPassword(anyString(), anyString());

        assertThat(response).isNull();
    }

    @Test
    public void smallPasswordTestAndSessionNullTest() {
        doReturn(SMALL_PASSWORD).when(req).getParameter(PASSWORD_PARAMETR);

        Response response = controller.handle(req, resp, serviceFactory);

        verify(serviceFactory, never()).getUserService();
        verify(service, never()).findByLoginAndPassword(anyString(), anyString());
        verify(session, times(1)).setAttribute(anyString(), anyString());

        assertThat(response).isEqualToComparingFieldByField(new Response(Route.TO_LOGIN));
    }

    @Test
    public void sessionNotNullTest() {
        doReturn(session).when(req).getSession(false);

        Response response = controller.handle(req, resp, serviceFactory);

        verify(session, times(1)).invalidate();
    }

    @Test
    public void userNotFound() {
        when(service.findByLoginAndPassword(anyString(), anyString())).thenReturn(null);

        Response response = controller.handle(req, resp, serviceFactory);

        verify(serviceFactory, times(1)).getUserService();
        verify(service, times(1)).findByLoginAndPassword(anyString(), anyString());
        verify(session, times(1)).setAttribute(anyString(), anyString());

        assertThat(response).isEqualToComparingFieldByField(new Response(Route.TO_LOGIN));
    }
}