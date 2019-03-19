package com.epam.vitebsk.tests.model.dao.jdbc;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.epam.vitebsk.model.dao.Mapper;
import com.epam.vitebsk.model.dao.jdbc.UserJdbcDao;
import com.epam.vitebsk.model.entity.User;

@RunWith(MockitoJUnitRunner.class)
public class JdbcDaoSupportTest extends JdbcTestSupport {

    private static String SELECT_LIST = "select * from \"user\"";

    private List<User> users;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @Mock
    private Mapper<User> mapper;

    @Before
    public void setUp() {
        userJdbcDao = spy(new UserJdbcDao());
        userJdbcDao.setConnection(connection);

        user = new User(USER_ID, USERNAME, PASSWORD, DISPLAY_NAME);
        users = new ArrayList<User>();
        users.add(user);
    }

    @Test
    public void jdbcSupportUpdateTest() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        userJdbcDao.update(DELETE, USER_ID);

        verify(connection, times(1)).prepareStatement(DELETE);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test(expected = RuntimeException.class)
    public void jdbcSupportUpdateExceptionTest() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        doThrow(SQLException.class).when(preparedStatement).executeUpdate();

        userJdbcDao.update(DELETE, USER_ID);

        verify(connection, times(1)).prepareStatement(DELETE);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void jdbcSupportSelectOneTest() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.execute()).thenReturn(true);
        when(preparedStatement.getResultSet()).thenReturn(resultSet);
        when(resultSet.isBeforeFirst()).thenReturn(true);
        when(resultSet.next()).thenReturn(true);
        doReturn(user).when(userJdbcDao).apply(any(), eq(resultSet));

        User actual = userJdbcDao.selectOne(SELECT_ONE, mapper, USER_ID);

        verify(connection, times(1)).prepareStatement(SELECT_ONE);
        verify(preparedStatement, times(1)).getResultSet();
        verify(userJdbcDao, times(1)).apply(any(), any(ResultSet.class));

        assertThat(actual).isEqualToComparingFieldByField(user);
    }

    @Test
    public void jdbcSupportSelectOneWithExecuteFalseTest() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.execute()).thenReturn(false);

        User actual = userJdbcDao.selectOne(SELECT_ONE, mapper, USER_ID);

        verify(connection, times(1)).prepareStatement(SELECT_ONE);

        assertThat(actual).isNull();
    }

    @Test
    public void jdbcSupportSelectOneWithIsBeforeFalseTest() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.execute()).thenReturn(true);
        when(preparedStatement.getResultSet()).thenReturn(resultSet);
        when(resultSet.isBeforeFirst()).thenReturn(false);

        User actual = userJdbcDao.selectOne(SELECT_ONE, mapper, USER_ID);

        verify(connection, times(1)).prepareStatement(SELECT_ONE);
        verify(preparedStatement, times(1)).getResultSet();

        assertThat(actual).isNull();
    }

    @Test(expected = RuntimeException.class)
    public void jdbcSupportSelectOneWithExceptionTest() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        doThrow(SQLException.class).when(preparedStatement).execute();

        User actual = userJdbcDao.selectOne(SELECT_ONE, mapper, USER_ID);

        verify(connection, times(1)).prepareStatement(SELECT_ONE);
        verify(preparedStatement, times(1)).execute();
    }

    @Test
    public void jdbcSupportSelectListTest() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.execute()).thenReturn(true);
        when(preparedStatement.getResultSet()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        doReturn(user).when(userJdbcDao).apply(any(), eq(resultSet));

        List<User> actual = userJdbcDao.selectList(SELECT_LIST, mapper);

        verify(connection, times(1)).prepareStatement(SELECT_LIST);
        verify(preparedStatement, times(1)).getResultSet();
        verify(userJdbcDao, atLeast(1)).apply(any(), any(ResultSet.class));

        assertThat(actual).isEqualTo(users);
    }

    @Test
    public void jdbcSupportSelectListWithExecuteFalseTest() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.execute()).thenReturn(false);

        List<User> actual = userJdbcDao.selectList(SELECT_LIST, mapper);

        verify(connection, times(1)).prepareStatement(SELECT_LIST);

        assertThat(actual).isEmpty();
    }

    @Test(expected = RuntimeException.class)
    public void jdbcSupportSelectListWithExceptionTest() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        doThrow(SQLException.class).when(preparedStatement).execute();

        List<User> actual = userJdbcDao.selectList(SELECT_LIST, mapper);

        verify(connection, times(1)).prepareStatement(SELECT_LIST);
        verify(preparedStatement, times(1)).execute();
    }
}
