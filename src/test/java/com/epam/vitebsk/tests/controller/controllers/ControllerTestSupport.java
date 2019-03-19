package com.epam.vitebsk.tests.controller.controllers;

import org.junit.Before;
import org.mockito.Mock;

import com.epam.vitebsk.controller.controllers.Controller;
import com.epam.vitebsk.controller.service.ServiceFactory;
import com.epam.vitebsk.tests.controller.ServletTestSupport;

public abstract class ControllerTestSupport extends ServletTestSupport {

    protected Controller controller;

    @Mock
    protected ServiceFactory serviceFactory;

    @Before
    public void setUp() {
        super.setUp();
    }
}
