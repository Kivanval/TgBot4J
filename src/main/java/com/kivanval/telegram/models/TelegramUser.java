package com.kivanval.telegram.models;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.telegram.telegrambots.meta.api.objects.User;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(
        name = "USERS",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"user_name"},
                        name = "users_user_name_uq")
        }
)
@Getter
@Setter
@ToString
public class TelegramUser implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @NotNull
    protected Long id;

    @Column(name = "first_name", nullable = false)
    @Size(
            min = 1,
            max = 255
    )
    @NotNull
    protected String firstName;

    @Column(name = "is_bot", nullable = false)
    @NotNull
    protected Boolean isBot;


    @Column(name = "last_name")
    @Size(max = 255)
    protected String lastName;

    @Column(name = "user_name")
    @Pattern(regexp = "\\w{5,32}")
    protected String userName;

    @Column(name = "language_code")
    @Size(
            min = 1,
            max = 255
    )
    protected String languageCode;

    @Column(name = "can_join_groups")
    protected Boolean canJoinGroups;

    @Column(name = "can_read_all_group_messages")
    protected Boolean canReadAllGroupMessages;

    @Column(name = "supports_inline_queries")
    protected Boolean supportInlineQueries;

    @OneToMany(
            mappedBy = "creator",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @Valid
    @NotNull
    @ToString.Exclude
    private List<@Valid @NotNull TelegramList> createdLists = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "LISTS_MANAGERS",
            joinColumns = @JoinColumn(
                    name = "manager_id",
                    referencedColumnName = "id",
                    nullable = false,
                    foreignKey = @ForeignKey(name = "managers_lists_user_id_fkey")
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "list_id",
                    referencedColumnName = "id",
                    nullable = false,
                    foreignKey = @ForeignKey(name = "managers_lists_list_id_fkey")
            )
    )
    @NotNull
    @ToString.Exclude
    private Set<@Valid @NotNull TelegramList> managedLists = new HashSet<>();

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @NotNull
    @ToString.Exclude
    private List<@Valid @NotNull ListedPlace> listedPlaces = new ArrayList<>();

    public static TelegramUser from(@NotNull User user) {
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.id = user.getId();
        telegramUser.firstName = user.getFirstName();
        telegramUser.isBot = user.getIsBot();
        telegramUser.lastName = user.getLastName();
        telegramUser.userName = user.getUserName();
        telegramUser.languageCode = user.getLanguageCode();
        telegramUser.canJoinGroups = user.getCanJoinGroups();
        telegramUser.canReadAllGroupMessages = user.getCanReadAllGroupMessages();
        telegramUser.supportInlineQueries = user.getSupportInlineQueries();
        return telegramUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TelegramUser that = (TelegramUser) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
