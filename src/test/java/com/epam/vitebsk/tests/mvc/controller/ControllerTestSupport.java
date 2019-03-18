package com.epam.vitebsk.tests.mvc.controller;

import org.junit.Before;
import org.mockito.Mock;

import com.epam.vitebsk.mvc.Controller;
import com.epam.vitebsk.service.ServiceFactory;
import com.epam.vitebsk.tests.mvc.MvcTestSupport;

public abstract class ControllerTestSupport extends MvcTestSupport {
	
    protected Controller controller;
    
    @Mock
    protected ServiceFactory serviceFactory;

    @Before
    public void setUp() {
        super.setUp();
    }
}
