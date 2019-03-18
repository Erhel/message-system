package com.epam.vitebsk.dao.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import com.epam.vitebsk.dao.Mapper;

public abstract class JdbcDaoSupport {

    private Map<String, String> map = new HashMap<>();

    private Connection connection;

    public void setConnection(final Connection connection) {
        this.connection = connection;
    }
    
    public void init(final String name) throws SQLException {
        try (InputStream io = getClass().getResourceAsStream(name)) {
            init(io);
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }
    
    public void init(final InputStream io) throws IOException {
        Properties properties = new Properties();
        properties.load(io);
        properties.forEach((k, v) -> map.put((String) k, (String) v));
    }

    private void buildQuery(PreparedStatement pst, Object... params) throws SQLException {
        if (params.length > 0) {
            for (int i = 0 ; i < params.length; i++) {
                pst.setObject(i + 1, params[i]); 
            }
        }
    }

    public <T> T selectOne(final String sql, final Mapper<T> mapper, Object... params) {

        PreparedStatement pst = null;

        ResultSet rs = null;
        
        try {
            pst = connection.prepareStatement(sql);
            
            buildQuery(pst, params);
            
            if (pst.execute()) {
                rs = pst.getResultSet();
                if (rs.isBeforeFirst()) {                	
                	rs.next();
                	return apply(mapper, rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs  != null) try {rs.close();} catch (Exception e) {}
            if (pst != null) try {pst.close();} catch (Exception e) {}
        }
    }

    public <T> List<T> selectList(final String sql, final Mapper<T> mapper, Object... params) {

        PreparedStatement pst = null;
        
        ResultSet rs = null;
        
        try {
            pst = connection.prepareStatement(sql);
            
            buildQuery(pst, params);
            
            ArrayList<T> list = new ArrayList<T>();
            
            if (pst.execute()) {
                rs = pst.getResultSet();
                
                while(rs.next()) {
                    list.add(apply(mapper, rs));
                }
            }

            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs  != null) try {rs.close();} catch (Exception e) {}
            if (pst != null) try {pst.close();} catch (Exception e) {}
        }
    }

    public void update(final String sql, Object... params) {
    	
    	PreparedStatement pst = null;

        try {
        	pst = connection.prepareStatement(sql);

            buildQuery(pst, params);
            
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (pst != null) try {pst.close();} catch (Exception e) {}
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