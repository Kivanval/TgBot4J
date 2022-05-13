package com.kivanval.telegram.model;


import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "LISTS")
@Getter
@Setter
@ToString
public class TelegramList implements Serializable {
    public enum State {
        ACTIVE, FREEZE, DELETED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    protected Long id;

    @Column(name = "access_key", nullable = false, unique = true)
    @Pattern(regexp = "\\w{10}")
    @NotNull
    protected String accessKey;

    @Lob
    @Column(name = "alias", unique = true)
    @Size(min = 1)
    protected String alias;

    @Enumerated
    @Column(name = "state", nullable = false)
    @NotNull
    protected State state;

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
    @Valid
    @NotNull
    protected TelegramUser admin;

    @OneToMany(mappedBy = "list", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @OrderBy(value = "number")
    @NotNull
    @ToString.Exclude
    protected List<@Valid ListedNumber> listedNumbers = new ArrayList<>();

    @ManyToMany(mappedBy = "managedLists", fetch = FetchType.LAZY)
    @NotNull
    @ToString.Exclude
    protected Set<@Valid TelegramUser> managers = new HashSet<>();

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

}
