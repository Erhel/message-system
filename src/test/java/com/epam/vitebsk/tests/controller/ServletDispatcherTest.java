package com.epam.vitebsk.tests.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.epam.vitebsk.controller.Response;
import com.epam.vitebsk.controller.ServletDispatcher;
import com.epam.vitebsk.controller.controllers.Controller;
import com.epam.vitebsk.controller.service.ServiceFactory;
import com.epam.vitebsk.model.pool.ConnectionPool;
import com.epam.vitebsk.utils.Route;

public class ServletDispatcherTest extends ServletTestSupport {

    private ServletDispatcher servletDispatcher;
    private Response response;

    @Mock
    ConnectionPool connectionPool;

    @Mock
    Controller controller;

    @Mock
    ServiceFactory serviceFactory;

    @Mock
    RequestDispatcher requestDispatcher;

    @Before
    public void setUp() {
        super.setUp();
        servletDispatcher = spy(new ServletDispatcher());
        response = new Response(Route.TO_MESSAGE_LIST);
        when(req.getRequestURI()).thenReturn(LOGIN_REQUEST_URI);
        when(req.getContextPath()).thenReturn(CONTEXT_PATH);
    }

    @Test
    public void doGetTest() throws ServletException, IOException {
        when(controller.handle(eq(req), eq(resp), any(ServiceFactory.class))).thenReturn(null);
        when(req.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        doReturn(controller).when(servletDispatcher).getController(anyString());

        servletDispatcher.doGet(req, resp);

        verify(req, times(1)).getRequestDispatcher(anyString());
        verify(requestDispatcher, times(1)).forward(any(HttpServletRequest.class), any(HttpServletResponse.class));
    }

    @Test
    public void doPostTest() throws ServletException, IOException {
        doReturn(controller).when(servletDispatcher).getController(anyString());
        when(controller.handle(eq(req), eq(resp), any(ServiceFactory.class))).thenReturn(response);

        servletDispatcher.doPost(req, resp);

        verify(resp, times(1)).sendRedirect(anyString());
    }
}
