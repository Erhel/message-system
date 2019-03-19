package com.epam.vitebsk.tests.model.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.epam.vitebsk.model.dao.DAOFactory;
import com.epam.vitebsk.model.dao.MessageDao;
import com.epam.vitebsk.model.dao.UserDao;
import com.epam.vitebsk.model.dao.jdbc.MessageJdbcDao;
import com.epam.vitebsk.model.dao.jdbc.UserJdbcDao;
import com.epam.vitebsk.model.pool.ConnectionPool;

@RunWith(MockitoJUnitRunner.class)
public class DAOFactoryTest extends Mockito {

    private DAOFactory daoFactory;
    
    @Mock
    private ConnectionPool connectionPool;
    
    @Mock
    Connection connection;

    @Before
    public void setUp() {
        daoFactory = new DAOFactory(connectionPool);
    }
    
    @Test
    public void getMessageDaoTest() {
        DAOFactory spy = spy(daoFactory);
        doReturn(connection).when(spy).getConnection();
        
        MessageDao dao = spy.getMessageDao();
        
        verify(spy,times(1)).getConnection();
        
        assertThat(dao).isExactlyInstanceOf(MessageJdbcDao.class);        
    }
    
    @Test
    public void getUserDaoTest() {
        DAOFactory spy = spy(daoFactory);
        doReturn(connection).when(spy).getConnection();
        
        UserDao dao = spy.getUserDao();
        
        verify(spy,times(1)).getConnection();
        
        assertThat(dao).isExactlyInstanceOf(UserJdbcDao.class);        
    }
    
    @Test
    public void getConnectionTest() throws SQLException {
        when(connectionPool.getConnection()).thenReturn(connection);
        
        Connection actual = daoFactory.getConnection();
        
        verify(connectionPool,times(1)).getConnection();
        
        assertThat(actual).isEqualTo(connection);        
    }
    
    @Test(expected = RuntimeException.class)
    public void getConnectionExceptionTest() throws SQLException {
        doThrow(SQLException.class).when(connectionPool).getConnection();
        
        Connection actual = daoFactory.getConnection();
        
        verify(connectionPool,times(1)).getConnection();    
    }
}
