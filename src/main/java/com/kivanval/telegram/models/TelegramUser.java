package com.kivanval.telegram.models;


import com.kivanval.telegram.utils.TelegramUserUtils;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.objects.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@ToString
public class TelegramUser implements Serializable {

    protected Long id;

    protected String firstName;

    protected String lastName;

    protected String userName;

    protected String languageCode;


    @ToString.Exclude
    private List<TelegramList> createdLists = new ArrayList<>();

    public List<TelegramList> getCreatedLists() {
        return Collections.unmodifiableList(createdLists);
    }

    public TelegramUser addCreatedList(TelegramList list) {
        Objects.requireNonNull(list, "list must be not null");
        createdLists.add(list);
        TelegramUser creator = list.getCreator();
        if (creator != null)
            throw new IllegalArgumentException("This list with id %d already has a creator %s"
                    .formatted(list.id, TelegramUserUtils.getName(creator)));
        list.setCreator(this);
        return this;
    }

    public TelegramUser removeCreatedList(TelegramList list) {
        Objects.requireNonNull(list, "list must be not null");
        if (createdLists.remove(list)) list.setCreator(null);
        return this;
    }

    public static TelegramUser from(@NotNull User user) {
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.id = user.getId();
        telegramUser.firstName = user.getFirstName();
        telegramUser.lastName = user.getLastName();
        telegramUser.userName = user.getUserName();
        telegramUser.languageCode = user.getLanguageCode();
        return telegramUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TelegramUser that = (TelegramUser) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
