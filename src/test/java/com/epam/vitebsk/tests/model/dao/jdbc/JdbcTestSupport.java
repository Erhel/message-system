package com.epam.vitebsk.tests.model.dao.jdbc;

import java.sql.Connection;

import org.mockito.Mock;
import org.mockito.Mockito;

import com.epam.vitebsk.model.dao.jdbc.UserJdbcDao;
import com.epam.vitebsk.model.entity.User;

public class JdbcTestSupport extends Mockito {

    protected static final Long USER_ID = 1L;
    protected static final String USERNAME = "andrey.koval@mail.ru";
    protected static final String PASSWORD = "simplePassword";
    protected static final String DISPLAY_NAME = "Andrey";

    protected static final String INSERT = "insert into \"user\" values(default, ?, ?, ?)";
    protected static final String UPDATE = "update \"user\" set username = ?, password = ?, display_name = ? where id = ?";
    protected static final String DELETE = "delete from \"user\" where id = ?";
    protected static final String SELECT_ONE = "select * from \"user\" where id = ?";
    protected static final String SELECT_ONE_BY_USERNAME_AND_PASSWORD = "select * from \"user\" where username = ? and password = ?";
    protected static final String SELECT_ONE_BY_USERNAME = "select * from \"user\" where username = ?";

    protected UserJdbcDao userJdbcDao;

    protected User user;

    @Mock
    protected Connection connection;

}
