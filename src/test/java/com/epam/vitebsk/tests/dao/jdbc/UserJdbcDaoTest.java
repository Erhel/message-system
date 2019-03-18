package com.epam.vitebsk.tests.dao.jdbc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.epam.vitebsk.dao.jdbc.UserJdbcDao;
import com.epam.vitebsk.entity.User;

@RunWith(MockitoJUnitRunner.class)
public class UserJdbcDaoTest extends JdbcTestSupport {
	
	@Before
	public void setUp() {
		userJdbcDao = spy(new UserJdbcDao());
		userJdbcDao.setConnection(connection);
		
		user = new User(USER_ID, USERNAME, PASSWORD, DISPLAY_NAME);
	}

	@Test
	public void createUserTest() {
		doReturn(INSERT).when(userJdbcDao).getSql(anyString());
		doNothing().when(userJdbcDao).update(INSERT, USERNAME, PASSWORD, DISPLAY_NAME);
		
		userJdbcDao.create(user);

		verify(userJdbcDao, times(1)).getSql(anyString());
		verify(userJdbcDao, times(1)).update(anyString(), anyString(), anyString(), anyString());
	}
	
	@Test
	public void readUserTest() {
		doReturn(SELECT_ONE).when(userJdbcDao).getSql(anyString());
		doReturn(user).when(userJdbcDao).selectOne(eq(SELECT_ONE), any(), eq(USER_ID));
		
		User actual = userJdbcDao.read(USER_ID);

		verify(userJdbcDao, times(1)).getSql(anyString());
		verify(userJdbcDao, times(1)).selectOne(anyString(), any(), anyLong());
		
		assertThat(actual).isEqualToComparingFieldByField(user);
	}
	
	@Test
	public void updateUserTest() {
		doReturn(UPDATE).when(userJdbcDao).getSql(anyString());
		doNothing().when(userJdbcDao).update(UPDATE, USERNAME, PASSWORD, DISPLAY_NAME, USER_ID);
		
		userJdbcDao.update(user);

		verify(userJdbcDao, times(1)).getSql(anyString());
		verify(userJdbcDao, times(1)).update(anyString(), anyString(), anyString(), anyString(), anyLong());
	}
	
	@Test
	public void deleteUserTest() {
		doReturn(DELETE).when(userJdbcDao).getSql(anyString());
		doNothing().when(userJdbcDao).update(DELETE, USER_ID);
		
		userJdbcDao.delete(USER_ID);

		verify(userJdbcDao, times(1)).getSql(anyString());
		verify(userJdbcDao, times(1)).update(anyString(), anyLong());
	}
	
	@Test
	public void readUserByLoginAndPasswordTest() {
		doReturn(SELECT_ONE_BY_USERNAME_AND_PASSWORD).when(userJdbcDao).getSql(anyString());
		doReturn(user).when(userJdbcDao).selectOne(eq(SELECT_ONE_BY_USERNAME_AND_PASSWORD), any(), eq(USERNAME), eq(PASSWORD));
		
		User actual = userJdbcDao.readByLoginAndPassword(USERNAME, PASSWORD);

		verify(userJdbcDao, times(1)).getSql(anyString());
		verify(userJdbcDao, times(1)).selectOne(anyString(), any(), anyString(), anyString());
		
		assertThat(actual).isEqualToComparingFieldByField(user);
	}

	@Test
	public void readUserByUsernameTest() {
		doReturn(SELECT_ONE_BY_USERNAME).when(userJdbcDao).getSql(anyString());
		doReturn(user).when(userJdbcDao).selectOne(eq(SELECT_ONE_BY_USERNAME), any(), eq(USERNAME));
		
		User actual = userJdbcDao.readByUsername(USERNAME);

		verify(userJdbcDao, times(1)).getSql(anyString());
		verify(userJdbcDao, times(1)).selectOne(anyString(), any(), anyString());
		
		assertThat(actual).isEqualToComparingFieldByField(user);
	}
}
