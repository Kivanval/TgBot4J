package com.kivanval.telegram.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(
        name = "LISTED_NUMBER",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"list_id", "number"})
        }
)
public class ListedNumber {

    @EmbeddedId
    @NotNull
    protected ListedNumberKey id;

    @ManyToOne
    @MapsId("listId")
    @JoinColumn(name = "list_id")
    protected TelegramList list;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "user_id")
    protected TelegramUser user;

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

    public @NotNull ListedNumberKey getId() {
        return this.id;
    }

    public TelegramList getList() {
        return this.list;
    }

    public TelegramUser getUser() {
        return this.user;
    }

    public @PastOrPresent @NotNull LocalDateTime getEntryDate() {
        return this.entryDate;
    }

    public @Positive @NotNull Integer getNumber() {
        return this.number;
    }

    public void setId(@NotNull ListedNumberKey id) {
        this.id = id;
    }

    public void setList(TelegramList list) {
        this.list = list;
    }

    public void setUser(TelegramUser user) {
        this.user = user;
    }

    public void setEntryDate(@PastOrPresent @NotNull LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public void setNumber(@Positive @NotNull Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("list", list)
                .append("user", user)
                .append("number", number)
                .toString();
    }
}
