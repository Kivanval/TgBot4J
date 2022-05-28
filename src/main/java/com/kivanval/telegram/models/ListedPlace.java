package com.kivanval.telegram.models;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenerationTime;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "LISTED_PLACES")
@Getter
@Setter
@ToString
public class ListedPlace implements Serializable {

    public enum State {
        WAITING, AWAY, SERVED, DELETED
    }

    @EmbeddedId
    @Valid
    @NotNull
    protected ListedPlaceKey id;

    @ManyToOne
    @MapsId("listId")
    @JoinColumn(
            name = "list_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "listed_numbers_list_id_fkey")
    )
    @Valid
    @NotNull
    protected TelegramList list;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "listed_numbers_user_id_fkey")
    )
    @Valid
    @NotNull
    protected TelegramUser user;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "state",
            nullable = false
    )
    @NotNull
    protected State state;

    @Column(
            name = "entry_date",
            nullable = false
    )
    @PastOrPresent
    @NotNull
    protected LocalDateTime entryDate;

    @Column(
            name = "ordinal",
            nullable = false)
    @org.hibernate.annotations.Generated(value = GenerationTime.INSERT)
    protected Integer ordinal;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ListedPlace that = (ListedPlace) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
