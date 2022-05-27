package com.kivanval.telegram.data.dao;


import java.util.Collection;
import java.util.Optional;


public interface Dao<E, I> {


    Optional<E> readById(I id);


    Collection<E> readAll();


    int create(E entity);


    int update(E entity);

    int upsert(E entity);

    int removeById(I id);
}
