package com.kivanval.telegram.data.repositories;

import com.kivanval.telegram.data.dao.JpaListedPlaceDao;
import com.kivanval.telegram.models.ListedPlace;
import com.kivanval.telegram.models.ListedPlaceKey;

import java.util.List;
import java.util.Optional;

public record JpaListedPlaceRepository(JpaListedPlaceDao dao) implements ListedPlaceRepository {

    @Override
    public List<ListedPlace> getAll() {
        return dao.readAll();
    }

    @Override
    public Optional<ListedPlace> getById(ListedPlaceKey id) {
        return dao.readById(id);
    }

    @Override
    public List<ListedPlace> getByUserId(Long userId) {
        return dao.readByUserId(userId);
    }

    @Override
    public List<ListedPlace> getByListId(Long listId) {
        return dao.readByListId(listId);
    }

    @Override
    public void add(ListedPlace entity) {
        dao.create(entity);
    }

    @Override
    public void update(ListedPlace entity) {
        dao.update(entity);
    }

    @Override
    public void delete(ListedPlace entity) {
        dao.remove(entity);
    }

    @Override
    public void close() {
        dao.close();
    }
}