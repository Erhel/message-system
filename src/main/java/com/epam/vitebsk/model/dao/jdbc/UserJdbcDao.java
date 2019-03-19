package com.epam.vitebsk.model.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.epam.vitebsk.model.dao.Mapper;
import com.epam.vitebsk.model.dao.UserDao;
import com.epam.vitebsk.model.entity.User;

public class UserJdbcDao extends JdbcDaoSupport implements UserDao {

    private static final String SQL_RESOURCE = "/sql.properties";
    private static final String INSERT_USER = "user.insert";
    private static final String UPDATE_USER = "user.update";
    private static final String DELETE_USER = "user.delete";
    private static final String SELECT_USER_BY_ID = "user.selectById";
    private static final String SELECT_USER_BY_USERNAME_AND_PASSWORD = "user.selectByLoginAndPassword";
    private static final String SELECT_USER_BY_USERNAME = "user.selectByUsername";

    private Mapper<User> mapper = new Mapper<User>() {
        @Override
        public User map(ResultSet resultSet) {
            try {
                Long id = resultSet.getLong(1);
                String username = resultSet.getString(2);
                String password = resultSet.getString(3);
                String displayName = resultSet.getString(4);
                User user = new User(id, username, password, displayName);
                return user;
            } catch (SQLException e) {
                // TODO: exception
                throw new RuntimeException(e);
            }
        }
    };

    public UserJdbcDao() {
        try {
            init(SQL_RESOURCE);
        } catch (SQLException e) {
            // TODO: exception
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(User entity) {
        update(getSql(INSERT_USER), entity.getUsername(), entity.getPassword(), entity.getDisplayName());
    }

    @Override
    public User read(Long id) {
        return selectOne(getSql(SELECT_USER_BY_ID), mapper, id);
    }

    @Override
    public void update(User entity) {
        update(getSql(UPDATE_USER), entity.getUsername(), entity.getPassword(), entity.getDisplayName(),
                entity.getId());
    }

    @Override
    public void delete(Long id) {
        update(getSql(DELETE_USER), id);
    }

    @Override
    public User readByLoginAndPassword(String login, String password) {
        return selectOne(getSql(SELECT_USER_BY_USERNAME_AND_PASSWORD), mapper, login, password);
    }

    @Override
    public User readByUsername(String username) {
        return selectOne(getSql(SELECT_USER_BY_USERNAME), mapper, username);
    }
}
