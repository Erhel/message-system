package com.epam.vitebsk.model.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.vitebsk.model.CloseConnectionException;
import com.epam.vitebsk.model.exception.CreateConnectionException;

public class ConnectionPool {

    private static final String CONNECTIVITY = "jdbc";

    private static final String PROTOCOL_KEY = "protocol";
    private static final String HOST_KEY = "host";
    private static final String PORT_KEY = "port";
    private static final String DATABASE_KEY = "database";
    private static final String MAX_POOL_KEY = "maxPool";
    private static final String USER_KEY = "user";
    private static final String PASSWORD_KEY = "password";
    private static final String DRIVER_KEY = "driver";

    private static final String UNABLE_CLOSE_CONNECTION = "Can't close connection";
    private static final String UNABLE_CREATE_CONNECTION = "Can't create connection";
    private static final String CONNETION_POOL_IS_FULL = "All connections occupied. Increase maximum pool size.";

    private Logger logger = LoggerFactory.getLogger(getClass());

    private int maxPool;
    private String url;
    private String username;
    private String password;

    private String driver;

    private List<PooledConnection> freeConnections, usedConnections;

    public ConnectionPool(int maxPool, String url, String username, String password, String driver)
            throws ClassNotFoundException {
        this.maxPool = maxPool;
        this.url = url;
        this.username = username;
        this.password = password;
        this.driver = driver;

        init();
    }

    public ConnectionPool(Map<String, String> map) throws ClassNotFoundException {

        this.url = new StringBuilder(CONNECTIVITY).append(":").append(map.get(PROTOCOL_KEY)).append("://")
                .append(map.get(HOST_KEY)).append(":").append(map.get(PORT_KEY)).append("/")
                .append(map.get(DATABASE_KEY)).toString();

        this.maxPool = Integer.parseInt(map.get(MAX_POOL_KEY));
        this.username = map.get(USER_KEY);
        this.password = map.get(PASSWORD_KEY);
        this.driver = map.get(DRIVER_KEY);

        init();
    }

    private void init() throws ClassNotFoundException {
        Class.forName(driver);

        freeConnections = Collections.synchronizedList(new ArrayList<>(maxPool));
        usedConnections = Collections.synchronizedList(new ArrayList<>(maxPool));
    }

    @Override
    protected void finalize() {
        destroy();
    }

    public synchronized void destroy() {
        closeAllConnections(freeConnections);
        closeAllConnections(usedConnections);
    }

    private void closeAllConnections(List<PooledConnection> connections) {
        for (PooledConnection pooledConnection : connections) {
            try {
                pooledConnection.getRawConnection().close();
            } catch (SQLException e) {
                getLogger().error(UNABLE_CLOSE_CONNECTION, e);
                throw new CloseConnectionException(UNABLE_CLOSE_CONNECTION, e);
            }
        }
    }

    public synchronized Connection getConnection() throws SQLException {
        PooledConnection pooledConnection = null;

        if (freeConnections.size() > 0) {
            pooledConnection = freeConnections.remove(freeConnections.size() - 1);
        } else if (usedConnections.size() < maxPool) {
            pooledConnection = createPooledConnection();
        } else {
            getLogger().error(CONNETION_POOL_IS_FULL);
            throw new CreateConnectionException(CONNETION_POOL_IS_FULL);
        }

        usedConnections.add(pooledConnection);

        return pooledConnection;
    }

    protected PooledConnection createPooledConnection() throws SQLException {
        Connection connection = null;
        PooledConnection pooledConnection = null;

        try {
            connection = getSimpleConnection();
            pooledConnection = new PooledConnection(this, connection);
        } catch (SQLException e) {
            getLogger().error(UNABLE_CREATE_CONNECTION, e);
            throw new CreateConnectionException(UNABLE_CREATE_CONNECTION, e);
        }

        return pooledConnection;
    }

    protected Connection getSimpleConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    protected Logger getLogger() {
        return logger;
    }

    protected void freePooledConnection(PooledConnection connection) {
        usedConnections.remove(connection);
        freeConnections.add(connection);
    }
}
