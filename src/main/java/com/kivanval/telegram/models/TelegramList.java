package com.kivanval.telegram.models;


import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;


@Data
public class TelegramList implements Serializable {

    protected Long id;

    protected TelegramUser creator;

    protected String title;

    protected int maxSize = Integer.MAX_VALUE;

    protected Timestamp startDate;

    protected Timestamp endDate;

    protected boolean isFreeze;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TelegramList list = (TelegramList) o;

        return Objects.equals(id, list.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
