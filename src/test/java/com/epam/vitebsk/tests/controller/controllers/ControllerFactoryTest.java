package com.epam.vitebsk.tests.controller.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import javax.servlet.ServletException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.epam.vitebsk.controller.controllers.Controller;
import com.epam.vitebsk.controller.controllers.ControllerFactory;
import com.epam.vitebsk.controller.controllers.authorization.LoginController;
import com.epam.vitebsk.utils.Route;

public class ControllerFactoryTest extends Mockito {

    private static final String WRONG_PATH = "/authorization/signup";

    @Mock
    Controller controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getControllerTest() throws ServletException {
        Controller controller = ControllerFactory.getController(Route.TO_LOGIN);

        assertThat(controller).isExactlyInstanceOf(LoginController.class);
    }

    @Ignore
    public void exceptionWhenGetControllerTest() {
        try {
            ControllerFactory.getController(WRONG_PATH);
        } catch (ServletException e) {
            assertThat(e).isExactlyInstanceOf(ServletException.class);
            assertThat(e.getMessage()).hasToString(anyString());
        }
    }
}
