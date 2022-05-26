package com.kivanval.telegram.data.repositories;


import com.kivanval.telegram.data.dao.AbstractJpaDao;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public abstract class AbstractJpaRepository<E, I> implements AutoCloseable {

    protected AbstractJpaDao<E, I> dao;

    protected AbstractJpaRepository(AbstractJpaDao<E, I> dao) {
        this.dao = dao;
    }

    @Transactional
    public List<E> getAll() {
        return dao.readAll();
    }

    @Transactional
    public Optional<E> getById(I id) {
        return dao.readById(id);
    }

    @Transactional
    public void add(E entity) {
        dao.create(entity);
    }

    @Transactional
    public void update(E entity) {
        dao.update(entity);
    }

    public void delete(E entity) {
        dao.remove(entity);
    }

    @Override
    public void close() {
        dao.close();
    }
}
