package com.epam.vitebsk.tests.controller.service;

import org.junit.Before;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.epam.vitebsk.model.entity.Entity;

public abstract class ServiceTestSupport<ID, T extends Entity> extends Mockito {

    public static final String USERNAME = "andrey.koval@mail.ru";
    public static final String PASSWORD = "simplePassword";
    public static final String DISPLAY_NAME = "Andrey";
    public static final Long USER_ID = 1L;

    public static final String SUBJECT = "Greeting";
    public static final String TEXT = "Hello";
    public static final Long MESSAGE_ID = 1L;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

}
