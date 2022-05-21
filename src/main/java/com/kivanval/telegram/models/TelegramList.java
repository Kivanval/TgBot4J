package com.kivanval.telegram.models;


import com.kivanval.telegram.utils.TelegramUserUtils;
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

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "creator_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "lists_user_id_fkey")
    )
    @Valid
    @NotNull
    @ToString.Exclude
    protected TelegramUser creator;

    @ManyToMany
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
    private Set<@Valid @NotNull TelegramUser> managers = new HashSet<>();

    @OneToMany(
            mappedBy = "list",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @NotNull
    @ToString.Exclude
    private List<@Valid @NotNull ListedPlace> listedPlaces = new ArrayList<>();


    public TelegramList addCreator(TelegramUser creator) {
        if (Objects.equals(creator, null))
            throw new NullPointerException("Can't add null creator");
        setCreator(creator);
        creator.getCreatedLists().add(this);
        return this;
    }

    public TelegramList addManager(TelegramUser manager) {
        if (Objects.equals(manager, null))
            throw new NullPointerException("Can't add null manager");
        if (Objects.equals(creator, manager))
            throw new IllegalArgumentException("%s is an creator of this list"
                    .formatted(TelegramUserUtils.getName(creator)));
        if (managers.contains(manager))
            throw new IllegalArgumentException("%s is already a manager of this list"
                    .formatted(TelegramUserUtils.getName(manager)));
        managers.add(manager);
        manager.getManagedLists().add(this);
        return this;
    }

    public TelegramList removeManager(TelegramUser manager) {
        if (Objects.equals(manager, null))
            throw new NullPointerException("Can't add null entityManager");
        if (!managers.contains(manager))
            throw new IllegalArgumentException("%s isn't a manager"
                    .formatted(TelegramUserUtils.getName(creator)));
        managers.remove(manager);
        manager.getManagedLists().remove(this);
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
