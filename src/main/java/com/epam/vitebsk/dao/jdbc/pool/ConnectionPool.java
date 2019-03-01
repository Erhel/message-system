package com.epam.vitebsk.dao.jdbc.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Andrei_Markser
 *
 */
public class ConnectionPool {

    private Logger log = LoggerFactory.getLogger(getClass());

    private int maxPool;

    /** JDBC URL to use for connecting to the database server. */
    private String url;

    /** Username to use for connecting to the database server. */
    private String user;

    /** Password to use for connecting to the database server. */
    private String pass;

    private List<PooledConnection> free, used;

    @SuppressWarnings("unused")
    private ConnectionPool() {}

    public ConnectionPool(int minPool, int maxPool, String url, String username, String password) throws SQLException {
        this.maxPool = maxPool;
        this.url = url;
        this.user = username;
        this.pass = password;

        free = Collections.synchronizedList(new ArrayList<PooledConnection>(maxPool));
        used = Collections.synchronizedList(new ArrayList<PooledConnection>(maxPool));

        for (int i = 0; i < minPool; i++) {
            free.add(createPooledConnection());
        }
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
            try {cw.getRawConnection().close();} catch (Exception e) {getLogger().info("Unable to close connection");}
        }

        for (PooledConnection cw : used) {
            try {cw.getRawConnection().close();} catch (Exception e) {getLogger().info("Unable to close connection");}
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
        con = DriverManager.getConnection(url, user, pass);

        // Add caching wrapper to connection.
        pcon = new PooledConnection(this, con);
        getLogger().info("Created a new connection");

        // Check for warnings.
        SQLWarning warn = con.getWarnings();

        while (warn != null) {
            getLogger().info("Warning - {}", warn.getMessage());
          warn = warn.getNextWarning();
        }
      } catch (SQLException ex) {
          getLogger().error("Can't create a new connection for {}", url, ex);
        // Clean up open connection.
        try {if (con != null) con.close();} catch (SQLException ex2) {getLogger().warn("Unable to close connection", ex2);}
        // Rethrow exception.
        throw ex;
      }

      return pcon;
    }

    protected void freePooledConnection(PooledConnection con) {
        used.remove(con);
        free.add(con);
    }
}
