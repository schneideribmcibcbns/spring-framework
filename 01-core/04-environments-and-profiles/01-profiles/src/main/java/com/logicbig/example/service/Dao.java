package com.logicbig.example.service;


import java.util.List;

public interface Dao<T> {

    void saveOrUpdate(T t);

    T load(long id);

    List<T> loadAll();


}