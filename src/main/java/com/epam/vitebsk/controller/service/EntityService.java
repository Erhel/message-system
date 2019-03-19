package com.epam.vitebsk.controller.service;

import com.epam.vitebsk.model.dao.CrudDao;
import com.epam.vitebsk.model.entity.Entity;

public abstract class EntityService<ID, T extends Entity> implements Service<ID, T> {

    private CrudDao<ID, T> dao;

    @Override
    public void save(T entity) {
        if (entity.getId() == null) {
            dao.create(entity);
        } else {
            dao.update(entity);
        }
    }

    @Override
    public T find(ID id) {
        return dao.read(id);
    }

    @Override
    public void delete(ID id) {
        dao.delete(id);
    }

    @SuppressWarnings("unchecked")
    public <M extends CrudDao<ID, T>> M getDao() {
        return (M) dao;
    }

    public void setDao(CrudDao<ID, T> dao) {
        this.dao = dao;
    }
}
