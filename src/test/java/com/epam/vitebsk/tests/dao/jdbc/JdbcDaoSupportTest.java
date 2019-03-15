package com.epam.vitebsk.tests.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.epam.vitebsk.dao.Mapper;
import com.epam.vitebsk.dao.jdbc.JdbcDaoSupport;

@RunWith(MockitoJUnitRunner.class)
public class JdbcDaoSupportTest extends Mockito {

    String sql = "SELECT * FROM \"user\" WHERE id = ?";
    
    @Mock
    Connection connection;
    
    @Mock
    PreparedStatement preparedStatement;
    
    @Mock
    ResultSet resultSet;
    
    @Mock(answer = Answers.CALLS_REAL_METHODS)
    JdbcDaoSupport jdbcDaoSupport;
    
    @Mock
    Mapper<?> mapper;
    
    @Before
    public void setUp() {
        jdbcDaoSupport.setConnection(connection);
    }

    @Test
    public void test() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.execute()).thenReturn(true);
        when(preparedStatement.getResultSet()).thenReturn(resultSet);
        
//        jdbcDaoSupport.selectOne(sql, mapper, 1);
    }

}
