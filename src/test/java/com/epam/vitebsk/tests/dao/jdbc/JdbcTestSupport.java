package com.epam.vitebsk.tests.dao.jdbc;

import java.sql.Connection;

import org.mockito.Mock;
import org.mockito.Mockito;

import com.epam.vitebsk.dao.jdbc.UserJdbcDao;
import com.epam.vitebsk.entity.User;

public class JdbcTestSupport extends Mockito {

	protected static Long USER_ID = 1L;
	protected static String USERNAME = "andrey.koval@mail.ru";
	protected static String PASSWORD = "simplePassword";
	protected static String DISPLAY_NAME = "Andrey";
	
	protected static String INSERT = "insert into \"user\" values(default, ?, ?, ?)";
	protected static String UPDATE = "update \"user\" set username = ?, password = ?, display_name = ? where id = ?";
	protected static String DELETE = "delete from \"user\" where id = ?";
	protected static String SELECT_ONE = "select * from \"user\" where id = ?";
	protected static String SELECT_ONE_BY_USERNAME_AND_PASSWORD = "select * from \"user\" where username = ? and password = ?";
	protected static String SELECT_ONE_BY_USERNAME = "select * from \"user\" where username = ?";
	
	protected UserJdbcDao userJdbcDao;
	
	protected User user;
	
	@Mock
	protected Connection connection;
	
}
