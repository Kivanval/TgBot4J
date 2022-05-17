package com.kivanval.telegram.data.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<E, I> extends AutoCloseable {
    void create(E entity);

    Optional<E> readById(I id);

    List<E> readAll();

    void update(E entity);

    void remove(E entity);


}
