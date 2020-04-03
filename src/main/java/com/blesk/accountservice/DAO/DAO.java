package com.blesk.accountservice.DAO;

import java.util.List;

public interface DAO<T> {

    T save(T t);

    boolean update(T t);

    boolean delete(T t);

    T get(Class c, Long id);

    List<T> getAll(Class c, int pageNumber, int pageSize);
}