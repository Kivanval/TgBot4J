package com.kivanval.telegram.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "LISTED_NUMBER")
@Getter
@Setter
@ToString
public class ListedNumber implements Serializable {

    public enum State {
        WAITING, AWAY, SERVED, DELETED
    }

    @EmbeddedId
    @Valid
    @NotNull
    protected ListedNumberKey id;

    @ManyToOne
    @MapsId("listId")
    @JoinColumn(name = "list_id", nullable = false)
    @Valid
    @NotNull
    protected TelegramList list;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "user_id", nullable = false)
    @Valid
    @NotNull
    protected TelegramUser user;

    @Enumerated
    @Column(name = "state", nullable = false)
    @NotNull
    protected State state;

    @Column(name = "entry_date", nullable = false)
    @PastOrPresent
    @NotNull
    protected LocalDateTime entryDate;

    @Column(nullable = false)
    @Positive
    @NotNull
    protected Integer number;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ListedNumber that = (ListedNumber) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
