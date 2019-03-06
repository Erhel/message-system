package com.epam.vitebsk.dao;

import com.epam.vitebsk.entity.User;

public interface UserDao extends CrudDao<Long, User>{
    
    void ddl();
    
    User readByLoginAndPassword(String login, String password);

}
