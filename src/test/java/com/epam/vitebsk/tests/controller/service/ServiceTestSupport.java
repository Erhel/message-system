package com.epam.vitebsk.tests.controller.service;

import org.junit.Before;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.epam.vitebsk.model.entity.Entity;

public abstract class ServiceTestSupport<ID, T extends Entity> extends Mockito {

    public static String USERNAME = "andrey.koval@mail.ru";
    public static String PASSWORD = "simplePassword";
    public static String DISPLAY_NAME = "Andrey";
    public static Long USER_ID = 1L;

    public static String SUBJECT = "Greeting";
    public static String TEXT = "Hello";
    public static Long MESSAGE_ID = 1L;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

}
