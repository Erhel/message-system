package com.epam.vitebsk.controller.service;

import com.epam.vitebsk.model.dao.UserDao;
import com.epam.vitebsk.model.entity.User;

public class UserService extends EntityService<Long, User> {

    public User findByLoginAndPassword(String login, String password) {

        UserDao dao = getDao();

        User user = dao.readByLoginAndPassword(login, password);

        return user;
    }

    public User findByUsername(String username) {

        UserDao dao = getDao();

        User user = dao.readByUsername(username);

        return user;
    }
}
