package com.epam.vitebsk.model.dao;

import com.epam.vitebsk.model.entity.User;

public interface UserDao extends CrudDao<Long, User>{
    
    User readByLoginAndPassword(String login, String password);

    User readByUsername(String username);

}
