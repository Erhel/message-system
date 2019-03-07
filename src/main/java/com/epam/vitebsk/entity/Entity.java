package com.epam.vitebsk.entity;

import java.io.Serializable;

public class Entity implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
