package com.kivanval.telegram.data.repo;

import com.kivanval.telegram.data.dao.JdbcTelegramListDao;
import com.kivanval.telegram.data.dao.JdbcTelegramUserDao;
import com.kivanval.telegram.models.TelegramList;
import com.kivanval.telegram.models.TelegramUser;
import lombok.Getter;
import lombok.Setter;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public class JdbcTelegramUserRepository implements Repository<TelegramUser, Long> {

    private final JdbcTelegramUserDao userDao;
    private final JdbcTelegramListDao listDao;

    @Getter
    @Setter
    public boolean deepExecute;

    public JdbcTelegramUserRepository(DataSource dataSource) {
        this.userDao = new JdbcTelegramUserDao(dataSource);
        this.listDao = new JdbcTelegramListDao(dataSource);
    }

    @Override
    public Optional<TelegramUser> getById(Long id) {
        Optional<TelegramUser> userOptional = userDao.readById(id);
        if (deepExecute && userOptional.isPresent()) {
            TelegramUser user = userOptional.get();
            Set<TelegramList> lists = listDao.readByCreatorId(user.getId());
            lists.forEach(user::addCreatedList);
        }
        return Optional.empty();
    }

    @Override
    public Collection<TelegramUser> getAll() {
        return null;
    }

    @Override
    public int add(TelegramUser entity) {
        return 0;
    }

    @Override
    public int addIfAbsent(TelegramUser entity) {
        return userDao.upsert(entity);
    }

    @Override
    public int update(TelegramUser entity) {
        return userDao.update(entity);
    }

    @Override
    public int remove(TelegramUser entity) {
        return 0;
    }
}
