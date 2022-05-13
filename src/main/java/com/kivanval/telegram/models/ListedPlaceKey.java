package com.kivanval.telegram.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@ToString
public class ListedPlaceKey implements Serializable {

    @Column(name = "list_id")
    @NotNull
    Long listId;

    @Column(name = "user_id")
    @NotNull
    Long userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ListedPlaceKey that = (ListedPlaceKey) o;
        return listId != null && Objects.equals(listId, that.listId)
                && userId != null && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listId, userId);
    }
}
