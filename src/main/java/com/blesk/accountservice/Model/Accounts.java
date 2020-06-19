package com.blesk.accountservice.Model;

import com.blesk.accountservice.Validator.Match.FieldMatch;
import com.blesk.accountservice.Validator.Password.Password;
import com.blesk.accountservice.Value.Messages;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

@DynamicInsert
@DynamicUpdate
@Entity(name = "Accounts")
@Table(name = "accounts", uniqueConstraints = {@UniqueConstraint(name = "account_id", columnNames = "account_id"), @UniqueConstraint(name = "account_username", columnNames = "user_name"), @UniqueConstraint(name = "account_email", columnNames = "email")})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = Accounts.class)
@JsonIgnoreProperties(value = {"accountPreferences"})
@SQLDelete(sql = "UPDATE accounts SET is_deleted = TRUE, deleted_at = NOW() WHERE account_id = ?")
@FieldMatch(first = "password", second = "confirmPassword", message = Messages.ACCOUNTS_PASWORD_MATCH, groups = Accounts.advancedValidation.class)
public class Accounts implements Serializable {

    public interface advancedValidation {
    }

    public interface basicValidation {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @Valid
    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY, mappedBy = "accounts")
    private Logins login;

    @Valid
    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY, mappedBy = "accounts")
    private Passwords passwords;

    @Valid
    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY, mappedBy = "accounts")
    private Activations activations;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "accounts")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<AccountRoles> accountRoles = new HashSet<AccountRoles>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "accounts")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<AccountPreferences> accountPreferences = new HashSet<AccountPreferences>();

    @NotNull(message = Messages.ACCOUNTS_USER_NAME_NULL, groups = advancedValidation.class)
    @Size(min = 5, max = 255, message = Messages.ACCOUNTS_USER_NAME_LENGHT, groups = advancedValidation.class)
    @Column(name = "user_name", nullable = false)
    private String userName;

    @NotNull(message = Messages.ACCOUNTS_EMAIL_NULL, groups = advancedValidation.class)
    @Email(message = Messages.ACCOUNTS_EMAIL, groups = advancedValidation.class)
    @Size(min = 5, max = 255, message = Messages.ACCOUNTS_EMAIL_LENGHT, groups = advancedValidation.class)
    @Column(name = "email", nullable = false)
    private String email;

    @Password(groups = advancedValidation.class)
    @NotNull(message = Messages.ACCOUNTS_PASSWORD_NULL, groups = advancedValidation.class)
    @Column(name = "password", nullable = false)
    private String password;

    @Transient
    private String confirmPassword;

    @Column(name = "is_activated", nullable = false)
    private Boolean isActivated;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at", updatable = false)
    private Timestamp deletedAt;

    @Transient
    private HashMap<String, String> validations = new HashMap<>();

    public Accounts(String userName, String email, String password, String confirmPassword, Boolean isActivated, Boolean isDeleted, HashMap<String, String> validations) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.isActivated = isActivated;
        this.isDeleted = isDeleted;
        this.validations = validations;
    }

    public Accounts(String userName, String email, String password, String confirmPassword, Boolean isActivated, Boolean isDeleted) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.isActivated = isActivated;
        this.isDeleted = isDeleted;
    }

    public Accounts() {
    }

    public Long getAccountId() {
        return this.accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Logins getLogin() {
        return this.login;
    }

    public void setLogin(Logins login) {
        this.login = login;
        if (this.login != null)
            this.login.setAccounts(this);
    }

    public Passwords getPasswords() {
        return this.passwords;
    }

    public void setPasswords(Passwords passwords) {
        this.passwords = passwords;
        if (this.passwords != null)
            this.passwords.setAccounts(this);
    }

    public Activations getActivations() {
        return this.activations;
    }

    public void setActivations(Activations activations) {
        this.activations = activations;
        if (this.activations != null)
            this.activations.setAccounts(this);
    }

    public void addRole(AccountRoles accountRoles) {
        this.accountRoles.add(accountRoles);
        accountRoles.setAccounts(this);
    }

    public void removeRole(AccountRoles accountRoles) {
        accountRoles.getRoles().getAccountRoles().remove(accountRoles);
        this.accountRoles.remove(accountRoles);
        accountRoles.setAccounts(null);
        accountRoles.setRoles(null);
    }

    public Set<AccountRoles> getAccountRoles() {
        return this.accountRoles;
    }

    public void addPreference(AccountPreferences accountPreferences) {
        this.accountPreferences.add(accountPreferences);
        accountPreferences.setAccounts(this);
    }

    public void removePreference(AccountPreferences accountPreferences) {
        accountPreferences.getPreferences().getAccountPreferences().remove(accountPreferences);
        this.accountPreferences.remove(accountPreferences);
        accountPreferences.setAccounts(null);
        accountPreferences.setPreferences(null);
    }

    public Set<AccountPreferences> getAccountPreferences() {
        return this.accountPreferences;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Boolean getActivated() {
        return this.isActivated;
    }

    public void setActivated(Boolean activated) {
        this.isActivated = activated;
    }

    public Boolean getDeleted() {
        return this.isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        this.isDeleted = deleted;
    }

    public Timestamp getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getDeletedAt() {
        return this.deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    public HashMap<String, String> getValidations() {
        return this.validations;
    }

    public void setValidations(HashMap<String, String> validations) {
        this.validations = validations;
    }

    @PrePersist
    protected void prePersist() {
        this.isActivated = false;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}