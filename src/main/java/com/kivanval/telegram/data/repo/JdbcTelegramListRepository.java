package com.kivanval.telegram.data.repo;

import com.kivanval.telegram.data.dao.JdbcTelegramListDao;
import com.kivanval.telegram.data.dao.JdbcTelegramUserDao;
import com.kivanval.telegram.models.TelegramList;
import com.kivanval.telegram.models.TelegramUser;
import lombok.Getter;
import lombok.Setter;

import javax.sql.DataSource;
import java.util.*;

public class JdbcTelegramListRepository implements Repository<TelegramList, Long> {

    private final JdbcTelegramUserDao userDao;
    private final JdbcTelegramListDao listDao;

    @Getter
    @Setter
    public boolean deepRead;

    public JdbcTelegramListRepository(DataSource dataSource) {
        this.userDao = new JdbcTelegramUserDao(dataSource);
        this.listDao = new JdbcTelegramListDao(dataSource);
    }

    @Override
    public Optional<TelegramList> getById(Long id) {
        return listDao.readById(id);
    }

    public Set<TelegramList> getByCreatorId(Long id) {
        Set<TelegramList> lists = listDao.readByCreatorId(id);
        if (deepRead && !lists.isEmpty()) {
            Optional<TelegramUser> userOptional = userDao.readById(id);
            userOptional.ifPresent(telegramUser -> lists.forEach(l -> l.setCreator(telegramUser)));
        }
        return lists;
    }

    public List<TelegramList> getByCreatorIdOrderByStartDate(Long id) {
        return getByCreatorId(id).stream()
                .sorted(Comparator.comparing(TelegramList::getStartDate))
                .toList();
    }

    @Override
    public Set<TelegramList> getAll() {
        Set<TelegramList> lists = listDao.readAll();
        if (deepRead && !lists.isEmpty()) {
            Long userId = lists.iterator().next().getCreator().getId();
            Optional<TelegramUser> userOptional = userDao.readById(userId);
            userOptional.ifPresent(telegramUser -> lists.forEach(l -> l.setCreator(telegramUser)));
        }
        return lists;
    }

    @Override
    public int add(TelegramList entity) {
        return listDao.create(entity);
    }

    @Override
    public int addIfAbsent(TelegramList entity) {
        return add(entity);
    }

    @Override
    public int update(TelegramList entity) {
        return listDao.update(entity);
    }

    @Override
    public int remove(TelegramList entity) {
        return listDao.removeById(entity.getId());
    }
}
