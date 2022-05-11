package com.kivanval.telegram.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "LISTS")
public class TelegramList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    protected Long id;

    @Column(name = "access_key", nullable = false, unique = true)
    @Size(min = 10, max = 10)
    protected String accessKey;

    @Column(name = "name", nullable = false, unique = true)
    @Size(min = 1, max = 255)
    protected String name;

    @Column(name = "is_active", nullable = false)
    @NotNull
    protected Boolean isActive;

    @Column(name = "max_size")
    @Positive
    protected Integer maxSize;

    @Column(name = "start_date", nullable = false)
    @PastOrPresent
    @NotNull
    protected LocalDateTime startDate;

    @Column(name = "end_date")
    @Future
    protected LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    @NotNull
    protected TelegramUser admin;

    @OneToMany(mappedBy = "list", fetch = FetchType.LAZY)
    protected Set<ListedNumber> listedNumbers = new HashSet<>();

    @ManyToMany(mappedBy = "managedLists")
    protected Set<TelegramUser> managers = new LinkedHashSet<>();

    public TelegramList(@NotNull Long id, @Size(min = 10, max = 10) String accessKey, @Size(min = 1, max = 255) String name, @NotNull Boolean isActive, @Positive Integer maxSize, @PastOrPresent @NotNull LocalDateTime startDate, @Future LocalDateTime endDate, @NotNull TelegramUser admin) {
        this.id = id;
        this.accessKey = accessKey;
        this.name = name;
        this.isActive = isActive;
        this.maxSize = maxSize;
        this.startDate = startDate;
        this.endDate = endDate;
        this.admin = admin;
    }

    public TelegramList() {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TelegramList that = (TelegramList) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public @NotNull Long getId() {
        return this.id;
    }

    public @Size(min = 10, max = 10) String getAccessKey() {
        return this.accessKey;
    }

    public @Size(min = 1, max = 255) String getName() {
        return this.name;
    }

    public @NotNull Boolean getIsActive() {
        return this.isActive;
    }

    public @Positive Integer getMaxSize() {
        return this.maxSize;
    }

    public @PastOrPresent @NotNull LocalDateTime getStartDate() {
        return this.startDate;
    }

    public @Future LocalDateTime getEndDate() {
        return this.endDate;
    }

    public @NotNull TelegramUser getAdmin() {
        return this.admin;
    }

    public Set<ListedNumber> getListedNumbers() {
        return this.listedNumbers;
    }

    public Set<TelegramUser> getManagers() {
        return this.managers;
    }

    public void setId(@NotNull Long id) {
        this.id = id;
    }

    public void setAccessKey(@Size(min = 10, max = 10) String accessKey) {
        this.accessKey = accessKey;
    }

    public void setName(@Size(min = 1, max = 255) String name) {
        this.name = name;
    }

    public void setIsActive(@NotNull Boolean isActive) {
        this.isActive = isActive;
    }

    public void setMaxSize(@Positive Integer maxSize) {
        this.maxSize = maxSize;
    }

    public void setStartDate(@PastOrPresent @NotNull LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(@Future LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setAdmin(@NotNull TelegramUser admin) {
        this.admin = admin;
    }

    public void setListedNumbers(Set<ListedNumber> listedNumbers) {
        this.listedNumbers = listedNumbers;
    }

    public void setManagers(Set<TelegramUser> managers) {
        this.managers = managers;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("accessKey", accessKey)
                .append("name", name)
                .append("isActive", isActive)
                .append("maxSize", maxSize)
                .append("startDate", startDate)
                .append("endDate", endDate)
                .append("admin", admin)
                .toString();
    }
}
