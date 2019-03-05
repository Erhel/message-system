package com.epam.vitebsk.dao;

import java.sql.ResultSet;

public interface Mapper<T> {
    T map(ResultSet rs);
}
