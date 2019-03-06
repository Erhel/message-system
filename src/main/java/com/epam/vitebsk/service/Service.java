package com.epam.vitebsk.service;

public interface Service<ID, T> {
    
    void save(T entity);
    
    T find(ID id);
    
    void delete(ID id);
}
