package com.epam.vitebsk.tests.model.pool;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import com.epam.vitebsk.model.pool.ConnectionPool;
import com.epam.vitebsk.model.pool.PooledConnection;

public class ConnectionPoolTest {

    private static final int MAXPOOL_SIZE = 5;
    private static final String POSTGRESQL_DRIVER = "org.postgresql.Driver";

    private MockConnectionPool spyConnectionPool;

    @Mock
    private PooledConnection pooledConnection;

    @Mock
    private Connection connection;

    public class MockConnectionPool extends ConnectionPool {

        public MockConnectionPool(int maxPool, String url, String username, String password, String driver)
                throws SQLException, ClassNotFoundException {
            super(maxPool, url, username, password, driver);
        }

        @Override
        protected Logger getLogger() {
            return super.getLogger();
        }

        @Override
        protected void finalize() {
            super.finalize();
        }

        @Override
        protected PooledConnection createPooledConnection() throws SQLException {
            return super.createPooledConnection();
        }

        @Override
        protected void freePooledConnection(PooledConnection con) {
            super.freePooledConnection(con);
        }

        @Override
        protected Connection getSimpleConnection() throws SQLException {
            return super.getSimpleConnection();
        }

    }

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        MockitoAnnotations.initMocks(this);
        MockConnectionPool connectionPool = new MockConnectionPool(MAXPOOL_SIZE, "", "", "", POSTGRESQL_DRIVER);
        spyConnectionPool = spy(connectionPool);
    }

    @Test
    public void createPooledConnectionTest() throws SQLException {

        doReturn(connection).when(spyConnectionPool).getSimpleConnection();

        PooledConnection pooledConnection = spyConnectionPool.createPooledConnection();

        verify(spyConnectionPool, times(1)).getSimpleConnection();

        assertThat(pooledConnection)
                .isEqualToComparingFieldByField(new PooledConnection(spyConnectionPool, connection));
    }

    @Test(expected = SQLException.class)
    public void createPooledConnectionFailedTest() throws SQLException {

        doThrow(SQLException.class).when(spyConnectionPool).getSimpleConnection();

        PooledConnection pooledConnection = spyConnectionPool.createPooledConnection();

        verify(spyConnectionPool, times(1)).getSimpleConnection();
    }

    @Test
    public void getConnectionWhenUsedNotFullTest() throws SQLException {

        doReturn(pooledConnection).when(spyConnectionPool).createPooledConnection();

        Connection connection = spyConnectionPool.getConnection();

        assertThat(connection).isEqualTo(pooledConnection);

        verify(spyConnectionPool, times(1)).createPooledConnection();
    }

    @Test
    public void getConnectionWhenUsedFullTest() throws SQLException {

        doReturn(pooledConnection).when(spyConnectionPool).createPooledConnection();

        IntStream.range(0, MAXPOOL_SIZE * 2).forEach(i -> {
            try {
                spyConnectionPool.getConnection();
            } catch (Exception e) {
                assertThat(e).isExactlyInstanceOf(RuntimeException.class);
                assertThat(e.getMessage()).hasToString("Unable to create a connection");
            }
        });
    }

    @Test
    public void getConnectionWhenFreeNotFull() throws SQLException {

        PooledConnection pc = new PooledConnection(spyConnectionPool, connection);

        doReturn(pc).when(spyConnectionPool).createPooledConnection();

        Connection connection = spyConnectionPool.getConnection();

        connection.close();

        Connection connection2 = spyConnectionPool.getConnection();

        verify(spyConnectionPool, times(1)).createPooledConnection();
        verify(spyConnectionPool, times(1)).freePooledConnection(any(PooledConnection.class));

        assertThat(connection2).isEqualTo(pc);
    }

    @Test
    public void destroyTest() throws SQLException {

        PooledConnection pc = new PooledConnection(spyConnectionPool, connection);

        doReturn(pc).when(spyConnectionPool).createPooledConnection();

        Connection connection = spyConnectionPool.getConnection();

        Connection connection2 = spyConnectionPool.getConnection();

        connection.close();

        verify(spyConnectionPool, times(1)).freePooledConnection(any(PooledConnection.class));

        assertThat(connection).isEqualTo(pc);
        assertThat(connection2).isEqualTo(pc);

        spyConnectionPool.destroy();
    }

}
