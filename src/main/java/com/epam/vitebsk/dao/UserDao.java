package com.epam.vitebsk.dao;

import com.epam.vitebsk.entity.User;

public interface UserDao extends CrudDao<Long, User>{
    
    User readByLoginAndPassword(String login, String password);

    User readByUsername(String username);

}
