package com.epam.vitebsk.dao.jdbc.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionPool {

	private Logger log = LoggerFactory.getLogger(getClass());

	private int maxPool;

	@SuppressWarnings("unused")
	private String driver;

	private String url;
	private String user;
	private String pass;

	private List<PooledConnection> free, used;

	public ConnectionPool(int maxPool, String url, String username, String password, String driver)
			throws SQLException, ClassNotFoundException {
		this.maxPool = maxPool;
		this.url = url;
		this.user = username;
		this.pass = password;
		this.driver = driver;
		
		Class.forName(driver);
		
		free = Collections.synchronizedList(new ArrayList<PooledConnection>(maxPool));
		used = Collections.synchronizedList(new ArrayList<PooledConnection>(maxPool));
	}

	protected Logger getLogger() {
		return log;
	}

	@Override
	protected void finalize() throws Throwable {
		destroy();
	}

	public synchronized void destroy() {
		for (PooledConnection cw : free) {
			try {
				cw.getRawConnection().close();
			} catch (Exception e) {
				getLogger().info("Unable to close connection");
			}
		}

		for (PooledConnection cw : used) {
			try {
				cw.getRawConnection().close();
			} catch (Exception e) {
				getLogger().info("Unable to close connection");
			}
		}
	}

	public synchronized Connection getConnection() throws SQLException {
		PooledConnection cw = null;

		if (free.size() > 0) {
			cw = free.remove(free.size() - 1);
		} else if (used.size() < maxPool) {
			cw = createPooledConnection();
		} else {
			throw new RuntimeException("Unable to create a connection");
		}

		used.add(cw);

		return cw;
	}

	protected PooledConnection createPooledConnection() throws SQLException {
		Connection con = null;
		PooledConnection pcon = null;

		try {
			con = getSimpleConnection();
			pcon = new PooledConnection(this, con);
			getLogger().info("Created a new connection");
		} catch (SQLException e) {
			getLogger().error("Can't create a new connection for {}", url, e);
			throw e;
		}

		return pcon;
	}

	protected void freePooledConnection(PooledConnection con) {
		used.remove(con);
		free.add(con);
	}

	protected Connection getSimpleConnection() throws SQLException {
		return DriverManager.getConnection(url, user, pass);
	}
}
