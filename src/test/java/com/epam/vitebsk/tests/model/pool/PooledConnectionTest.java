package com.epam.vitebsk.tests.model.pool;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.epam.vitebsk.model.pool.ConnectionPool;
import com.epam.vitebsk.model.pool.PooledConnection;

public class PooledConnectionTest {

    private PooledConnection pooledConnection;

    public class MockConnectionPool extends ConnectionPool {
        public MockConnectionPool(int maxPool, String url, String username, String password, String driver)
                throws SQLException, ClassNotFoundException {
            super(maxPool, url, username, password, driver);
        }

        @Override
        protected void freePooledConnection(PooledConnection con) {
            super.freePooledConnection(con);
        }
    }

    @Mock
    private Connection connection;

    @Mock
    private MockConnectionPool connectionPool;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        pooledConnection = new PooledConnection(connectionPool, connection);
    }

    @Test
    public void getRawConnectionTest() {
        Connection conn = pooledConnection.getRawConnection();
        assertThat(conn).isEqualToComparingFieldByField(connection);
    }

    @Test
    public void closeTest() throws SQLException {
        pooledConnection.close();
        verify(connectionPool, times(1)).freePooledConnection(pooledConnection);
    }

}
