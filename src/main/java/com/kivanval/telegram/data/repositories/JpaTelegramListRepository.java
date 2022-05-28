package com.kivanval.telegram.data.repositories;

import com.kivanval.telegram.data.dao.JpaTelegramListDao;
import com.kivanval.telegram.models.TelegramList;
import com.kivanval.telegram.utils.HibernateUtils;
import org.hibernate.Session;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class JpaTelegramListRepository implements TelegramListRepository {
    private final JpaTelegramListDao dao;

    public JpaTelegramListRepository() {
        this.dao = new JpaTelegramListDao(HibernateUtils.getSession());
    }

    @Override
    public List<TelegramList> getAll() {
        return dao.readAll();
    }

    @Override
    public Optional<TelegramList> getById(Long id) {
        return dao.readById(id);
    }

    @Override
    public Optional<TelegramList> getByAlias(String alias) {
        return dao.readByAlias(alias);
    }

    @Override
    public List<TelegramList> getByManagerId(Long managerId) {
        return dao.readByManagerId(managerId);
    }

    @Override
    public List<TelegramList> getByUserId(Long userId) {
        return dao.readByUserId(userId);
    }

    @Override
    public List<TelegramList> getByCreatorId(Long creatorId) {
        return dao.readByCreatorId(creatorId);
    }


    @Override
    public List<TelegramList> getByCreatorIdOrderByStartDate(Long creatorId) {
        return getByCreatorId(creatorId).parallelStream()
                .sorted(Comparator.comparing(TelegramList::getStartDate))
                .toList();
    }

    @Override
    public void add(TelegramList entity) {
        dao.create(entity);
    }

    @Override
    public void update(TelegramList entity) {
        dao.update(entity);
    }

    @Override
    public void delete(TelegramList entity) {
        dao.remove(entity);
    }

    @Override
    public void close() {
        dao.close();
    }
}