package com.epam.vitebsk.model.dao;

import java.sql.ResultSet;

public interface Mapper<T> {
    T map(ResultSet rs);
}
