package com.epam.vitebsk.dao;

public interface CrudDao<ID, T> extends Dao<T> {
    
    void create(T entity);

    T read(ID id);

    void update(T entity);

    void delete(ID id);
}
