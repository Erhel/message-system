package com.epam.vitebsk.dao;

import java.sql.Connection;

import com.epam.vitebsk.dao.jdbc.pool.ConnectionPool;

public class DAOFactory {
	
	private ConnectionPool connectionPool;

	public DAOFactory(ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
	}
	
}
