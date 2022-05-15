package com.kivanval.telegram.models;


import com.kivanval.telegram.utils.TelegramUserUtils;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.NaturalId;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(
        name = "LISTS",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"access_key"},
                        name = "lists_access_key_uq"),
                @UniqueConstraint(
                        columnNames = {"alias"},
                        name = "lists_alias_uq")
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

    @org.hibernate.annotations.NaturalId
    @Column(name = "access_key", nullable = false)
    @Size(
            min = 1,
            max = 255
    )
    @NotNull
    protected String accessKey;

    @org.hibernate.annotations.NaturalId
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
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    @JoinTable(
            name = "LISTS_MANAGERS",
            joinColumns = @JoinColumn(
                    name = "list_id",
                    referencedColumnName = "id",
                    nullable = false,
                    foreignKey = @ForeignKey(name = "managers_lists_list_id_fkey")
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "manager_id",
                    referencedColumnName = "id",
                    nullable = false,
                    foreignKey = @ForeignKey(name = "managers_lists_user_id_fkey")
            )
    )
    @NotNull
    @ToString.Exclude
    protected Set<@Valid TelegramUser> managers = new HashSet<>();

    public TelegramList addManager(TelegramUser manager) {
        if (Objects.equals(manager, null))
            throw new NullPointerException("Can't add null session");
        TelegramUser admin = getAdmin();
        if (Objects.equals(admin, manager))
            throw new IllegalStateException("%s is an admin of this list"
                    .formatted(TelegramUserUtils.getName(admin)));
        if (managers.contains(manager))
            throw new IllegalStateException("%s is already a session of this list"
                    .formatted(TelegramUserUtils.getName(manager)));

        getManagers().add(manager);
        return this;
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

}
