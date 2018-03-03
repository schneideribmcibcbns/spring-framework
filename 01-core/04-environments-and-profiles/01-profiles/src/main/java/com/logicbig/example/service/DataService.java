package com.logicbig.example.service;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class DataService<T> {

    @Autowired
    private Dao<T> dao;

    protected Dao<T> getDao() {
        return dao;
    }
}