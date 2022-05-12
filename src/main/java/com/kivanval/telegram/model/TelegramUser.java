package com.kivanval.telegram.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.Hibernate;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class TelegramUser {

    @Id
    @Column(name = "id", nullable = false)
    @NotNull
    protected Long id;

    @Column(name = "first_name", nullable = false)
    @NotEmpty
    protected String firstName;

    @Column(name = "is_bot", nullable = false)
    @NotNull
    protected Boolean isBot;

    @Column(name = "last_name")
    protected String lastName;

    @Column(name = "user_name", unique = true)
    @Pattern(regexp = "\\w{5,32}")
    protected String userName;

    @Column(name = "language_code")
    @Size(min = 1)
    protected String languageCode;

    @Column(name = "can_join_groups")
    protected Boolean canJoinGroups;

    @Column(name = "can_read_all_group_messages")
    protected Boolean canReadAllGroupMessages;

    @Column(name = "supports_inline_queries")
    protected Boolean supportInlineQueries;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    protected Set<ListedNumber> listedNumbers = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(name = "MANAGERS_LISTS",
            joinColumns = @JoinColumn(name = "manager_id"),
            inverseJoinColumns = @JoinColumn(name = "list_id"))
    protected Set<TelegramList> managedLists = new LinkedHashSet<>();

    @OneToMany(mappedBy = "admin", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    protected Set<TelegramList> adminLists = new HashSet<>();

    private TelegramUser(Long id, String firstName, Boolean isBot, String lastName,
                        String userName, String languageCode,
                        Boolean canJoinGroups, Boolean canReadAllGroupMessages, Boolean supportInlineQueries) {
        this.id = id;
        this.firstName = firstName;
        this.isBot = isBot;
        this.lastName = lastName;
        this.userName = userName;
        this.languageCode = languageCode;
        this.canJoinGroups = canJoinGroups;
        this.canReadAllGroupMessages = canReadAllGroupMessages;
        this.supportInlineQueries = supportInlineQueries;
    }

    public TelegramUser() {
    }


    public static TelegramUser from(@NotNull User user) {
        Long id = user.getId();
        String firstName = user.getFirstName();
        Boolean isBot = user.getIsBot();
        String lastName = user.getLastName();
        String userName = user.getUserName();
        String languageCode = user.getLanguageCode();
        Boolean canJoinGroups = user.getCanJoinGroups();
        Boolean canReadAllGroupMessages = user.getCanReadAllGroupMessages();
        Boolean supportInlineQueries = user.getSupportInlineQueries();
        return new TelegramUser(id, firstName, isBot, lastName, userName, languageCode, canJoinGroups, canReadAllGroupMessages, supportInlineQueries);
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

    public @NotNull Long getId() {
        return this.id;
    }

    public @NotEmpty String getFirstName() {
        return this.firstName;
    }

    public @NotNull Boolean getIsBot() {
        return this.isBot;
    }

    public String getLastName() {
        return this.lastName;
    }

    public @Pattern(regexp = "\\w{5,32}") String getUserName() {
        return this.userName;
    }

    public @Size(min = 1) String getLanguageCode() {
        return this.languageCode;
    }

    public Boolean getCanJoinGroups() {
        return this.canJoinGroups;
    }

    public Boolean getCanReadAllGroupMessages() {
        return this.canReadAllGroupMessages;
    }

    public Boolean getSupportInlineQueries() {
        return this.supportInlineQueries;
    }

    public Set<ListedNumber> getListedNumbers() {
        return this.listedNumbers;
    }

    public Set<TelegramList> getManagedLists() {
        return this.managedLists;
    }

    public Set<TelegramList> getAdminLists() {
        return this.adminLists;
    }

    public void setId(@NotNull Long id) {
        this.id = id;
    }

    public void setFirstName(@NotEmpty String firstName) {
        this.firstName = firstName;
    }

    public void setIsBot(@NotNull Boolean isBot) {
        this.isBot = isBot;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserName(@Pattern(regexp = "\\w{5,32}") String userName) {
        this.userName = userName;
    }

    public void setLanguageCode(@Size(min = 1) String languageCode) {
        this.languageCode = languageCode;
    }

    public void setCanJoinGroups(Boolean canJoinGroups) {
        this.canJoinGroups = canJoinGroups;
    }

    public void setCanReadAllGroupMessages(Boolean canReadAllGroupMessages) {
        this.canReadAllGroupMessages = canReadAllGroupMessages;
    }

    public void setSupportInlineQueries(Boolean supportInlineQueries) {
        this.supportInlineQueries = supportInlineQueries;
    }

    public void setListedNumbers(Set<ListedNumber> listedNumbers) {
        this.listedNumbers = listedNumbers;
    }

    public void setManagedLists(Set<TelegramList> managedLists) {
        this.managedLists = managedLists;
    }

    public void setAdminLists(Set<TelegramList> adminLists) {
        this.adminLists = adminLists;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("firstName", firstName)
                .append("isBot", isBot)
                .append("lastName", lastName)
                .append("userName", userName)
                .append("languageCode", languageCode)
                .append("canJoinGroups", canJoinGroups)
                .append("canReadAllGroupMessages", canReadAllGroupMessages)
                .append("supportInlineQueries", supportInlineQueries)
                .toString();
    }
}
