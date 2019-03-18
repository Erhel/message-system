package com.epam.vitebsk.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.epam.vitebsk.dao.Mapper;
import com.epam.vitebsk.dao.UserDao;
import com.epam.vitebsk.entity.User;

public class UserJdbcDao extends JdbcDaoSupport implements UserDao {

    private Mapper<User> mapper = new Mapper<User>() {
        @Override
        public User map(ResultSet rs) {
           try {
                Long id = rs.getLong(1);
                String username = rs.getString(2);
                String password = rs.getString(3);
                String displayName = rs.getString(4);
                User user = new User(id, username, password, displayName);
                return user;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    };
    
    public UserJdbcDao() {
        try {
			init("/sql.properties");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
    }
    
    @Override
    public void create(User entity) {
        update(getSql("user.insert"), entity.getUsername(), entity.getPassword(), entity.getDisplayName());        
    }

    @Override
    public User read(Long id) {
        return selectOne(getSql("user.selectById"), mapper, id);
    }

    @Override
    public void update(User entity) {
        update(getSql("user.update"), entity.getUsername(), entity.getPassword(), entity.getDisplayName(), entity.getId());        
    }

    @Override
    public void delete(Long id) {
        update(getSql("user.delete"), id);        
    }

    @Override
    public User readByLoginAndPassword(String login, String password) {
        return selectOne(getSql("user.selectByLoginAndPassword"), mapper, login, password);
    }

    @Override
    public User readByUsername(String username) {
        return selectOne(getSql("user.selectByUsername"), mapper, username);
    }
}
