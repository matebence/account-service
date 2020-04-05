package com.blesk.accountservice.Model;

import com.blesk.accountservice.Model.Preferences.AccountPreferenceItems;
import com.blesk.accountservice.Values.Messages;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

@Entity(name = "Accounts")
@Table(name = "accounts")
public class Accounts implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "account")
    private Logins login;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "account")
    private Passwords passwords;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "account_role_items", joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> roles = new HashSet<>();

    @OneToMany(mappedBy = "preferences", cascade = CascadeType.MERGE)
    private Set<AccountPreferenceItems> accountPreferenceItems = new HashSet<>();

    @NotNull(message = Messages.ACCOUNTS_USER_NAME_NULL)
    @Size(min = 5, max = 255, message = Messages.ACCOUNTS_USER_NAME_LENGHT)
    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @NotNull(message = Messages.ACCOUNTS_EMAIL_NULL)
    @Email(message = Messages.ACCOUNTS_EMAIL)
    @Size(min = 5, max = 255, message = Messages.ACCOUNTS_EMAIL_LENGHT)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull(message = Messages.ACCOUNTS_PASSWORD_NULL)
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_activated", nullable = false)
    private Boolean isActivated;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @NotNull(message = Messages.ENTITY_CREATOR_ID)
    @Positive(message = Messages.ENTITY_IDS)
    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Column(name = "created_at", updatable = false, nullable = false)
    private java.sql.Timestamp createdAt;

    @Positive(message = Messages.ENTITY_IDS)
    @Column(name = "updated_by", updatable = false)
    private Long updatedBy;

    @Column(name = "updated_at")
    private java.sql.Timestamp updatedAt;

    @Positive(message = Messages.ENTITY_IDS)
    @Column(name = "deleted_by")
    private Long deletedBy;

    @Column(name = "deleted_at")
    private java.sql.Timestamp deletedAt;

    public Accounts() {
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Logins getLogin() {
        return login;
    }

    public void setLogin(Logins login) {
        this.login = login;
    }

    public Passwords getPasswords() {
        return passwords;
    }

    public void setPasswords(Passwords passwords) {
        this.passwords = passwords;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public Set<AccountPreferenceItems> getAccountPreferenceItems() {
        return accountPreferenceItems;
    }

    public void setAccountPreferenceItems(Set<AccountPreferenceItems> accountPreferenceItems) {
        this.accountPreferenceItems = accountPreferenceItems;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActivated() {
        return isActivated;
    }

    public void setActivated(Boolean activated) {
        isActivated = activated;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(Long deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    @PrePersist
    protected void prePersist() {
        this.isActivated = false;
        this.isDeleted = false;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}