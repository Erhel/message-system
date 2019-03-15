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

    private String url;
    private String user;
    private String pass;

    private List<PooledConnection> free, used;

    @SuppressWarnings("unused")
    private ConnectionPool() {}

    public ConnectionPool(int minPool, int maxPool, String url, String username, String password) throws SQLException {
        this.maxPool = maxPool;
        this.url = url;
        this.user = username;
        this.pass = password;
        
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

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
          
        if (user!=null && pass!=null) {
            con = DriverManager.getConnection(url, user, pass);
        } else {
            con = DriverManager.getConnection(url);
        }

        pcon = new PooledConnection(this, con);
        getLogger().info("Created a new connection");
      } catch (SQLException e) {
        getLogger().error("Can't create a new connection for {}", url, e);
        throw e;
      } finally {
          try {if (con != null) con.close();} catch (SQLException e2) {getLogger().error("Unable to close connection", e2);}  
      }

      return pcon;
    }

    protected void freePooledConnection(PooledConnection con) {
        used.remove(con);
        free.add(con);
    }
}
