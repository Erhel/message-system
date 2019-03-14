package com.epam.vitebsk.mvc.controller.authorization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.epam.vitebsk.entity.User;
import com.epam.vitebsk.mvc.Response;

public class LoginControllerTest extends AuthorizationTestSupport {
    
    @Test
    public void test() {
        when(req.getParameter("login")).thenReturn("andrey.koval@mail.ru");
        when(req.getParameter("password")).thenReturn("simple");
        when(service.findByLoginAndPassword(anyString(), anyString())).thenReturn(new User(1L, "andrey.koval@mail.ru", "simple", "Andrey"));
        when(req.getSession(anyBoolean())).thenReturn(session);
        controller = new LoginController();
        
        Response response = controller.handle(req, resp, factory);
        
        assertThat(response). (new Response("/message/list.html")));
        assertEquals(new Response("/message/list.html"), response);
        
    }

}