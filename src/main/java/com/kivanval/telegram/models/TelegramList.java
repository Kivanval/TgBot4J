package com.kivanval.telegram.models;


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
@Table(
        name = "LISTS",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"access_key"},
                        name = "lists_access_key_akey"),
                @UniqueConstraint(
                        columnNames = {"alias"},
                        name = "lists_alias_akey")
        }
)
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

    @Column(name = "access_key", nullable = false)
    @Pattern(regexp = "\\w{10}")
    @NotNull
    protected String accessKey;


    @Column(name = "alias")
    @Size(
            min = 1,
            max = 255
    )
    protected String alias;

    @Enumerated(EnumType.STRING)
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
    @JoinColumn(
            name = "admin_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "lists_user_id_fkey")
    )
    @Valid
    @NotNull
    protected TelegramUser admin;

    @OneToMany(
            mappedBy = "list",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL})
    @OrderBy("placeNumber")
    @NotNull
    @ToString.Exclude
    protected List<@Valid ListedPlace> listedPlaces = new ArrayList<>();

    @ManyToMany(
            mappedBy = "managedLists",
            fetch = FetchType.LAZY)
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
