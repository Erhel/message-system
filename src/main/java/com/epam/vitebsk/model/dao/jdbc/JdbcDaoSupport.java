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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.vitebsk.model.dao.Mapper;
import com.epam.vitebsk.model.exception.BuildSqlException;
import com.epam.vitebsk.model.exception.SQLQueryException;
import com.epam.vitebsk.utils.Resource;

public abstract class JdbcDaoSupport {

    private static final String BUILD_SQL_EXCEPTION = "Can't set object in statement";
    private static final String CANT_CLOSE_RESULT_SET = "Can't close resutl set";
    private static final String CANT_CLOSE_STATEMENT = "Can't close statement";
    private static final String CANT_QUERY = "Can't query to database";

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Map<String, String> map = new HashMap<>();

    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void init(final String name) {
        map = new Resource(name).load().toMap();
    }

    private void buildQuery(PreparedStatement preparedStatement, Object... params) throws BuildSqlException {
        try {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
        } catch (SQLException e) {
            throw new BuildSqlException(BUILD_SQL_EXCEPTION, e);
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
        } catch (Exception e) {
            throw new SQLQueryException(CANT_QUERY, e);
        } finally {
            if (resultSet != null)
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    getLogger().error(CANT_CLOSE_RESULT_SET, e);
                }
            if (preparedStatement != null)
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    getLogger().error(CANT_CLOSE_STATEMENT, e);
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
        } catch (Exception e) {
            throw new SQLQueryException(CANT_QUERY, e);
        } finally {
            if (resultSet != null)
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    getLogger().error(CANT_CLOSE_RESULT_SET, e);
                }
            if (preparedStatement != null)
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    getLogger().error(CANT_CLOSE_STATEMENT, e);
                }
        }
    }

    public void update(String sql, Object... params) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);

            buildQuery(preparedStatement, params);

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new SQLQueryException(CANT_QUERY, e);
        } finally {
            if (preparedStatement != null)
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    getLogger().error(CANT_CLOSE_STATEMENT, e);
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

    protected Logger getLogger() {
        return logger;
    }
}