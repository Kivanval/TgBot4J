package com.kivanval.telegram.data.repo;

import java.util.Collection;
import java.util.Optional;

public interface Repository<E, I> {
    Optional<E> getById(I id);


    Collection<E> getAll();


    int add(E entity);

    int addIfAbsent (E entity);


    int update(E entity);


    int remove(E entity);

}
