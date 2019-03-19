package com.epam.vitebsk.tests.controller.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.epam.vitebsk.controller.service.UserService;
import com.epam.vitebsk.model.dao.UserDao;
import com.epam.vitebsk.model.entity.User;

public class UserServiceTest extends ServiceTestSupport<Long, User> {

    private UserService userService;

    private User user;

    @Mock
    UserDao userDao;

    @Before
    public void setUp() {
        super.setUp();
        userService = new UserService();
        user = new User(USER_ID, USERNAME, PASSWORD, DISPLAY_NAME);
        userService.setDao(userDao);
    }

    @Test
    public void findByLoginAndPasswordTest() {
        when(userDao.readByLoginAndPassword(USERNAME, PASSWORD)).thenReturn(user);

        User userResponse = userService.findByLoginAndPassword(USERNAME, PASSWORD);

        verify(userDao, times(1)).readByLoginAndPassword(anyString(), anyString());

        assertThat(userResponse).isEqualToComparingFieldByField(user);
    }

    @Test
    public void findByUsername() {
        when(userDao.readByUsername(USERNAME)).thenReturn(user);

        User userResponse = userService.findByUsername(USERNAME);

        verify(userDao, times(1)).readByUsername(anyString());

        assertThat(userResponse).isEqualToComparingFieldByField(user);
    }
}
