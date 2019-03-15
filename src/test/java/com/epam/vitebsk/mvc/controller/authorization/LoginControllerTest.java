package com.epam.vitebsk.mvc.controller.authorization;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.epam.vitebsk.entity.User;
import com.epam.vitebsk.mvc.Response;

public class LoginControllerTest extends AuthorizationTestSupport  {
    
	@Before
	public void setUp() {
		super.setUp();
		controller = new LoginController();
	}
	
    @Test
    public void test1() {
        when(req.getParameter("login")).thenReturn("andrey.koval@mail.ru");
        when(req.getParameter("password")).thenReturn("simple");
        when(service.findByLoginAndPassword(anyString(), anyString())).thenReturn(new User(1L, "andrey.koval@mail.ru", "simple", "Andrey"));
        
        Response response = controller.handle(req, resp, serviceFactory);
        
        verify(serviceFactory, times(1)).getUserService();
        verify(service, times(1)).findByLoginAndPassword(anyString(), anyString());
        
        assertThat(response).isEqualToComparingFieldByField(new Response("/message/list.html"));        
    }
    
    @Test
    public void test2() {
    	when(req.getParameter("login")).thenReturn(null).thenReturn("andrey.koval@mail.ru");
    	when(req.getParameter("password")).thenReturn("simple").thenReturn(null);
    	
    	Response response = controller.handle(req, resp, serviceFactory);
    	
    	assertThat(response).isNull();
    	
    	response = controller.handle(req, resp, serviceFactory);
    	
        verify(serviceFactory, never()).getUserService();
        verify(service, never()).findByLoginAndPassword(anyString(), anyString());
    	
    	assertThat(response).isNull();
    }
    
    @Test
    public void test3() {
    	when(req.getParameter("login")).thenReturn("andrey.koval@mail.ru");
        when(req.getParameter("password")).thenReturn("small");
        
        Response response = controller.handle(req, resp, serviceFactory);
        
        verify(serviceFactory, never()).getUserService();
        verify(service, never()).findByLoginAndPassword(anyString(), anyString());
        
        assertThat(response).isEqualToComparingFieldByField(new Response("/authorization/login.html?info=password should've at least 6 symbols"));
    }
    
    @Test
    public void test4() {
    	when(req.getParameter("login")).thenReturn("andrey.koval@mail.ru");
        when(req.getParameter("password")).thenReturn("simple");
        when(service.findByLoginAndPassword(anyString(), anyString())).thenReturn(null);
        
        Response response = controller.handle(req, resp, serviceFactory);
        
        verify(serviceFactory, times(1)).getUserService();
        verify(service, times(1)).findByLoginAndPassword(anyString(), anyString());
        
        assertThat(response).isEqualToComparingFieldByField(new Response("/authorization/login.html?info=incorrect password or login"));
    }
}