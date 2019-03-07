package com.epam.vitebsk.service;

import com.epam.vitebsk.dao.UserDao;
import com.epam.vitebsk.entity.User;

public class UserService extends EntityService<Long, User> {

    public User findByLoginAndPassword(String login, String password) {

        UserDao dao = getDao();
        
        User user = dao.readByLoginAndPassword(login, password);
        
        return user;
    }
}
