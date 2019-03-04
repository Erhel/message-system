package com.epam.vitebsk.service;

import com.epam.vitebsk.entity.User;

public class LoginService implements Service {
    
    public User findByLoginAndPassword(String login, String password) {
        return new User("andrey.koval@mail.ru", "sadwefg", "andrey", 1L);
    }
    
}
