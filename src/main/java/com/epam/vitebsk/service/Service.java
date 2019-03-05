package com.epam.vitebsk.service;

import com.epam.vitebsk.dao.Dao;

public abstract class Service<T> {
    
    protected Dao<T> dao;

    public Dao<T> getDao() {
        return dao;
    }

    public void setDao(Dao<T> dao) {
        this.dao = dao;
    }

}
