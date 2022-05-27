package com.kivanval.telegram.models;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;


@Getter
@Setter
@ToString
public class ListedPlace implements Serializable {

    public enum State {
        WAITING, AWAY, SERVED
    }


    protected ListedPlaceId id;

    protected TelegramList list;

    protected TelegramUser user;

    protected State state = State.WAITING;

    protected Timestamp entryDate = Timestamp.valueOf(LocalDateTime.now());

    protected Integer ordinal;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListedPlace that = (ListedPlace) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
