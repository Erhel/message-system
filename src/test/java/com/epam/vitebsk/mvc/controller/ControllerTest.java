package com.epam.vitebsk.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.epam.vitebsk.mvc.Controller;
import com.epam.vitebsk.service.ServiceFactory;

public class ControllerTest extends Mockito {

    protected Controller controller;
    
    @Mock
    protected HttpSession session;
    
    @Mock
    protected HttpServletRequest req;
    
    @Mock
    protected HttpServletResponse resp;
    
    @Mock
    protected ServiceFactory factory;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
}
