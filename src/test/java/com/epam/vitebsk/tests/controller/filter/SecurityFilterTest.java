package com.epam.vitebsk.tests.controller.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.epam.vitebsk.controller.filter.SecurityFilter;
import com.epam.vitebsk.model.entity.User;
import com.epam.vitebsk.tests.controller.ServletTestSupport;

public class SecurityFilterTest extends ServletTestSupport {

    private static String USER_ATTRIBUTE = "user";

    private SecurityFilter securityFilter;
    private User user;

    @Mock
    FilterChain filterChain;

    @Before
    public void setUp() {
        super.setUp();
        when(req.getContextPath()).thenReturn(CONTEXT_PATH);
        securityFilter = new SecurityFilter();
        user = new User(USER_ID, PASSWORD, PASSWORD, DISPLAY_NAME);
    }

    @Test
    public void forwardToLoginPageTest() throws IOException, ServletException {
        when(req.getRequestURI()).thenReturn(LOGIN_REQUEST_URI);

        securityFilter.doFilter(req, resp, filterChain);

        verify(filterChain, times(1)).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));
    }

    @Test
    public void forwardToRegistrationPageTest() throws IOException, ServletException {
        when(req.getRequestURI()).thenReturn(REGISTRATION_REQUEST_URI);

        securityFilter.doFilter(req, resp, filterChain);

        verify(filterChain, times(1)).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));
    }

    @Test
    public void tryAccessInternalPageWithoutSessionTest() throws IOException, ServletException {
        when(req.getRequestURI()).thenReturn(INACCESSIBLE_REQUEST_URI);
        when(req.getSession(false)).thenReturn(null);

        securityFilter.doFilter(req, resp, filterChain);

        verify(resp, times(1)).sendRedirect(LOGIN_REQUEST_URI);
    }

    @Test
    public void tryAccessInternalPageWithoutUserInSessionTest() throws IOException, ServletException {
        when(req.getRequestURI()).thenReturn(INACCESSIBLE_REQUEST_URI);
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute(USER_ATTRIBUTE)).thenReturn(null);

        securityFilter.doFilter(req, resp, filterChain);

        verify(resp, times(1)).sendRedirect(LOGIN_REQUEST_URI);
    }

    @Test
    public void successfullAccessInternalPageTest() throws IOException, ServletException {
        when(req.getRequestURI()).thenReturn(INACCESSIBLE_REQUEST_URI);
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute(USER_ATTRIBUTE)).thenReturn(user);

        securityFilter.doFilter(req, resp, filterChain);

        verify(filterChain, times(1)).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));
    }

}
