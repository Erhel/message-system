package com.epam.vitebsk.tests.controller.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.epam.vitebsk.controller.controllers.Controller;
import com.epam.vitebsk.controller.controllers.ControllerFactory;
import com.epam.vitebsk.controller.controllers.authorization.LoginController;
import com.epam.vitebsk.controller.exception.ControllerInstantiationException;
import com.epam.vitebsk.utils.Route;

public class ControllerFactoryTest extends Mockito {

    @Mock
    private Controller controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getControllerTest() throws ControllerInstantiationException {
        Controller controller = ControllerFactory.getController(Route.TO_LOGIN);

        assertThat(controller).isExactlyInstanceOf(LoginController.class);
    }
}
