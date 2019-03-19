package com.epam.vitebsk.tests.controller.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.epam.vitebsk.controller.service.UserService;
import com.epam.vitebsk.model.dao.UserDao;
import com.epam.vitebsk.model.entity.User;

public class EntityServiceTest extends ServiceTestSupport<Long, User> {

    private UserService userService;

    private User user;

    @Mock
    private UserDao userDao;

    @Before
    public void setUp() {
        super.setUp();
        userService = new UserService();
        user = new User(USER_ID, USERNAME, PASSWORD, DISPLAY_NAME);
        userService.setDao(userDao);
    }

    @Test
    public void updateEntityTest() {
        userService.save(user);

        verify(userDao, times(1)).update(any(User.class));
    }

    @Test
    public void createEntityTest() {
        user.setId(null);
        userService.save(user);

        verify(userDao, times(1)).create(any(User.class));
    }

    @Test
    public void deleteEntityTest() {
        userService.delete(USER_ID);

        verify(userDao, times(1)).delete(anyLong());
    }

    @Test
    public void readEntityTest() {
        when(userDao.read(USER_ID)).thenReturn(user);

        User userResponse = userService.find(USER_ID);

        verify(userDao, times(1)).read(anyLong());

        assertThat(userResponse).isEqualToComparingFieldByField(user);
    }
}
