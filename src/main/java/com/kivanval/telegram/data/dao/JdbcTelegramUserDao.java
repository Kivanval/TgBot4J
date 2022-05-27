package com.kivanval.telegram.data.dao;

import com.kivanval.telegram.models.TelegramUser;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;


@Slf4j

public class JdbcTelegramUserDao implements JdbcDao<TelegramUser, Long> {

    DataSource dataSource;

    public JdbcTelegramUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Set<TelegramUser> parse(ResultSet resultSet) {
        Set<TelegramUser> users = new TreeSet<>(Comparator.comparing(TelegramUser::getId));
        try {
            if (resultSet.next()) {
                TelegramUser user = new TelegramUser();
                user.setId(resultSet.getLong("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setUserName(resultSet.getString("user_name"));
                user.setLanguageCode(resultSet.getString("language_code"));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return Collections.emptySet();
    }

    @Override
    public Optional<TelegramUser> readById(Long id) {
        PreparedStatement statement = null;
        try (Connection conn = dataSource.getConnection()) {
            String query = """
                    SELECT
                        u.id,
                        u.first_name,
                        u.last_name,
                        u.user_name,
                        u.language_code
                    FROM
                        users u
                    WHERE
                        u.id = ?
                    """;
            log.info(query);
            statement = conn.prepareStatement(query);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            Set<TelegramUser> lists = parse(resultSet);
            return lists.stream().findFirst();

        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error(e.getMessage());
                }
            }
        }

        return Optional.empty();
    }


    @Override
    public Set<TelegramUser> readAll() {
        Statement statement = null;
        try (Connection conn = dataSource.getConnection()) {
            String query = """
                    SELECT
                        u.id,
                        u.first_name,
                        u.last_name,
                        u.user_name,
                        u.language_code
                    FROM
                        users u
                    """;
            log.info(query);
            statement = conn.createStatement();
            statement.execute(query);
            ResultSet resultSet = statement.getResultSet();
            return parse(resultSet);
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return Collections.emptySet();
    }

    @Override
    public int create(TelegramUser entity) {
        PreparedStatement statement = null;
        try (Connection conn = dataSource.getConnection()) {
            String query = """
                    INSERT INTO users (id, first_name, last_name, user_name, language_code)
                        VALUES (?, ?, ?, ?, ?)
                    """;
            log.info(query);
            statement = conn.prepareStatement(query);
            statement.setLong(1, entity.getId());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getLastName());
            statement.setString(4, entity.getUserName());
            statement.setString(5, entity.getLanguageCode());
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return 0;
    }


    @Override
    public int update(TelegramUser entity) {
        PreparedStatement statement = null;
        try (Connection conn = dataSource.getConnection()) {
            String query = """
                    UPDATE users
                        SET first_name = ?,
                            last_name = ?,
                            user_name = ?,
                            language_code = ?
                    WHERE id = ?
                    """;
            log.info(query);
            statement = conn.prepareStatement(query);
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getUserName());
            statement.setString(4, entity.getLanguageCode());
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return 0;
    }

    @Override
    public int upsert(TelegramUser entity) {
        PreparedStatement statement = null;
        try (Connection conn = dataSource.getConnection()) {
            String query = """
                    INSERT INTO users (id, first_name, last_name, user_name, language_code)
                        VALUES (?, ?, ?, ?, ?)
                        ON CONFLICT (id)
                        DO
                            UPDATE SET first_name = excluded.first_name,
                                       last_name = excluded.last_name,
                                       user_name = excluded.user_name,
                                       language_code = excluded.language_code
                    """;
            log.info(query);
            statement = conn.prepareStatement(query);
            statement.setLong(1, entity.getId());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getLastName());
            statement.setString(4, entity.getUserName());
            statement.setString(5, entity.getLanguageCode());
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return 0;
    }

    @Override
    public int removeById(Long id) {
        PreparedStatement statement = null;
        try (Connection conn = dataSource.getConnection()) {
            String query = """
                    DELETE FROM users
                    WHERE id = ?
                    """;
            log.info(query);
            statement = conn.prepareStatement(query);
            statement.setLong(1, id);
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return 0;
    }
}
