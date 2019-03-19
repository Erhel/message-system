package com.epam.vitebsk.model.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.epam.vitebsk.model.dao.Mapper;
import com.epam.vitebsk.utils.Resource;

public abstract class JdbcDaoSupport {

    private Map<String, String> map = new HashMap<>();

    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void init(final String name) throws SQLException {
        map = new Resource(name).load().toMap();
    }

    private void buildQuery(PreparedStatement preparedStatement, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
    }

    public <T> T selectOne(String sql, Mapper<T> mapper, Object... params) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sql);

            buildQuery(preparedStatement, params);

            if (preparedStatement.execute()) {
                resultSet = preparedStatement.getResultSet();

                if (resultSet.isBeforeFirst()) {
                    resultSet.next();
                    return apply(mapper, resultSet);
                }
            }

            return null;
        } catch (SQLException e) {
            // TODO: exception
            throw new RuntimeException(e);
        } finally {
            if (resultSet != null)
                try {
                    resultSet.close();
                } catch (Exception e) {
                    // TODO: logging
                }
            if (preparedStatement != null)
                try {
                    preparedStatement.close();
                } catch (Exception e) {
                    // TODO: logging
                }
        }
    }

    public <T> List<T> selectList(String sql, Mapper<T> mapper, Object... params) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<T> list = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(sql);

            buildQuery(preparedStatement, params);

            if (preparedStatement.execute()) {
                resultSet = preparedStatement.getResultSet();

                while (resultSet.next()) {
                    list.add(apply(mapper, resultSet));
                }
            }

            return list;
        } catch (SQLException e) {
            // TODO: exception
            throw new RuntimeException(e);
        } finally {
            if (resultSet != null)
                try {
                    resultSet.close();
                } catch (Exception e) {
                    // TODO: logging
                }
            if (preparedStatement != null)
                try {
                    preparedStatement.close();
                } catch (Exception e) {
                    // TODO: logging
                }
        }
    }

    public void update(String sql, Object... params) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);

            buildQuery(preparedStatement, params);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO: exception
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null)
                try {
                    preparedStatement.close();
                } catch (Exception e) {
                    // TODO: logging
                }
        }
    }

    public String getSql(String name) {
        Objects.requireNonNull(name);

        return map.get(name);
    }

    public <T> T apply(Mapper<T> mapper, ResultSet rs) {
        return mapper.map(rs);
    }
}