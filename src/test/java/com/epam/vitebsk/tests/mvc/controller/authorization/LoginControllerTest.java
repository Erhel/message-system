package com.epam.vitebsk.tests.mvc.controller.authorization;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.epam.vitebsk.entity.User;
import com.epam.vitebsk.mvc.Response;
import com.epam.vitebsk.mvc.controller.authorization.LoginController;

public class LoginControllerTest extends AuthorizationTestSupport  {
    
	private String username;
	private String password;
	
	@Before
	public void setUp() {
		super.setUp();
		controller = new LoginController();
		username = "andrey.koval@mail.ru";
		password = "simple";
	}
	
    @Test
    public void test1() {
        when(req.getParameter("login")).thenReturn(username);
        when(req.getParameter("password")).thenReturn(password);
        when(service.findByLoginAndPassword(anyString(), anyString())).thenReturn(new User(1L, username, password, "Andrey"));
        
        Response response = controller.handle(req, resp, serviceFactory);
        
        verify(serviceFactory, times(1)).getUserService();
        verify(service, times(1)).findByLoginAndPassword(anyString(), anyString());
        
        assertThat(response).isEqualToComparingFieldByField(new Response("/message/list.html"));        
    }
    
    @Test
    public void test2() {
    	when(req.getParameter("login")).thenReturn(null).thenReturn(username);
    	when(req.getParameter("password")).thenReturn(password).thenReturn(null);
    	
    	Response response = controller.handle(req, resp, serviceFactory);
    	
    	assertThat(response).isNull();
    	
    	response = controller.handle(req, resp, serviceFactory);
    	
        verify(serviceFactory, never()).getUserService();
        verify(service, never()).findByLoginAndPassword(anyString(), anyString());
    	
    	assertThat(response).isNull();
    }
    
    @Test
    public void test3() {
    	when(req.getParameter("login")).thenReturn(username);
        when(req.getParameter("password")).thenReturn("small");
        
        Response response = controller.handle(req, resp, serviceFactory);
        
        verify(serviceFactory, never()).getUserService();
        verify(service, never()).findByLoginAndPassword(anyString(), anyString());
        
        assertThat(response).isEqualToComparingFieldByField(new Response("/authorization/login.html"));
    }
    
    @Test
    public void test4() {
    	when(req.getParameter("login")).thenReturn(username);
        when(req.getParameter("password")).thenReturn(password);
        when(service.findByLoginAndPassword(anyString(), anyString())).thenReturn(null);
        
        Response response = controller.handle(req, resp, serviceFactory);
        
        verify(serviceFactory, times(1)).getUserService();
        verify(service, times(1)).findByLoginAndPassword(anyString(), anyString());
        
        assertThat(response).isEqualToComparingFieldByField(new Response("/authorization/login.html"));
    }
}