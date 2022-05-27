package com.kivanval.telegram.data.dao;

import java.sql.ResultSet;
import java.util.Collection;



public interface JdbcDao<E, I> extends Dao<E, I> {

    Collection<E> parse(ResultSet resultSet);
}
