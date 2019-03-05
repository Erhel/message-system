package com.epam.vitebsk.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.epam.vitebsk.dao.jdbc.MessageJdbcDao;
import com.epam.vitebsk.dao.jdbc.UserJdbcDao;
import com.epam.vitebsk.dao.jdbc.pool.ConnectionPool;

public class DAOFactory {
	
	private ConnectionPool connectionPool;

	public DAOFactory(ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
	}
	
	public MessageDao getMessageDao() {
	    MessageJdbcDao dao = new MessageJdbcDao();
	    dao.setConnection(getConnection());
	    return dao;
	}
	
	public UserDao getUserDao() {
	    UserJdbcDao dao = new UserJdbcDao();
	    dao.setConnection(getConnection());
	    return dao;
	}
	
	public Connection getConnection() {
	    try {
            return connectionPool.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}
}
