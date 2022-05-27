package com.kivanval.telegram.models;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;


@Getter
@Setter
@ToString
public class ListedPlace implements Serializable {

    public enum State {
        WAITING, AWAY, SERVED
    }

    @Valid
    @NotNull
    protected ListedPlaceKey id;


    @Valid
    @NotNull
    protected TelegramList list;


    @Valid
    @NotNull
    protected TelegramUser user;


    @NotNull
    protected State state = State.WAITING;

    @PastOrPresent
    @NotNull
    protected LocalDateTime entryDate = LocalDateTime.now();

    protected Integer priority;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
