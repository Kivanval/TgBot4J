package com.kivanval.telegram.data.repositories;


import java.util.List;
import java.util.Optional;

public interface Repository<E, I> extends AutoCloseable {

    List<E> getAll();

    Optional<E> getById(I id);

    void add(E entity);

    void update(E entity);

    void delete(E entity);
}
