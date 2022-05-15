package com.kivanval.telegram.data.dao;

import com.kivanval.telegram.models.ListedPlace;
import com.kivanval.telegram.models.ListedPlaceKey;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public record HibernateListedPlaceDao(Session session) implements Dao<ListedPlace, ListedPlaceKey> {

    @Override
    public Optional<ListedPlace> readById(ListedPlaceKey id) {
        return Optional.ofNullable(session.find(ListedPlace.class, id));
    }

    @Override
    public List<ListedPlace> readAll() {
        TypedQuery<ListedPlace> query = session.createQuery("SELECT p FROM ListedPlace p", ListedPlace.class);
        return query.getResultList();
    }

    @Override
    public void create(ListedPlace entity) {
        executeInsideTransaction(session -> session.persist(entity));
    }

    @Override
    public void update(ListedPlace entity) {
        executeInsideTransaction(session -> session.merge(entity));
    }

    @Override
    public void delete(ListedPlace entity) {
        executeInsideTransaction(session -> session.remove(entity));
    }

    private void executeInsideTransaction(Consumer<Session> action) {
        Transaction tx = session.getTransaction();
        try {
            tx.begin();
            action.accept(session);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }

    @Override
    public void close() throws Exception {
        session.close();
    }
}
