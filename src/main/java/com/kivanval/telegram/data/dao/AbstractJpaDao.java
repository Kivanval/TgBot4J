package com.kivanval.telegram.data.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public abstract class AbstractJpaDao<E, I> implements AutoCloseable {

    protected Class<E> eClass;
    protected Class<I> iClass;

    protected EntityManager entityManager;

    protected AbstractJpaDao(EntityManager entityManager, Class<E> eClass, Class<I> iClass) {
        this.entityManager = entityManager;
        this.eClass = eClass;
        this.iClass = iClass;
    }

    public Optional<E> readById(I id) {
        return Optional.ofNullable(entityManager.find(eClass, id));
    }


    public List<E> readAll() {
        TypedQuery<E> query = entityManager.createQuery("FROM " + eClass.getSimpleName(), eClass);
        return query.getResultList();
    }


    @Transactional
    public void create(E entity) {
        entityManager.persist(entity);

    }


    @Transactional
    public void update(E entity) {
        entityManager.merge(entity);
    }


    @Transactional
    public void remove(E entity) {
        entityManager.remove(entity);
    }

    @Override
    public void close() {
        entityManager.close();
    }


}
