package com.blesk.accountservice.Model;

import com.blesk.accountservice.Model.Preferences.AccountPreferenceItems;
import com.blesk.accountservice.Service.Accounts.AccountsService;
import com.blesk.accountservice.Validator.Table.Match.FieldMatch;
import com.blesk.accountservice.Validator.Table.Password.EncryptionAware;
import com.blesk.accountservice.Validator.Table.Unique.Unique;
import com.blesk.accountservice.Validator.Table.Password.Password;
import com.blesk.accountservice.Value.Messages;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

@Entity(name = "Accounts")
@Table(name = "accounts", uniqueConstraints = {@UniqueConstraint(columnNames = {"account_id"})})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = Accounts.class)
@JsonIgnoreProperties(value = {"accountPreferenceItems"})
@FieldMatch(first = "password", second = "confirmPassword", message = Messages.ACCOUNTS_PASWORD_MATCH, groups = Accounts.validationWithEncryption.class)
public class Accounts implements Serializable, EncryptionAware {

    public interface validationWithEncryption {
    }

    public interface validationWithoutEncryption {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER, mappedBy = "accounts")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Logins login;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER, mappedBy = "accounts")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Passwords passwords;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER, mappedBy = "accounts")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Activations activations;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "account_role_items", joinColumns = @JoinColumn(name = "account_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> roles = new HashSet<>();

    @OneToMany(mappedBy = "preferences", fetch = FetchType.EAGER)
    private Set<AccountPreferenceItems> accountPreferenceItems = new HashSet<>();

    @NotNull(message = Messages.ACCOUNTS_USER_NAME_NULL, groups = validationWithEncryption.class)
    @Size(min = 5, max = 255, message = Messages.ACCOUNTS_USER_NAME_LENGHT, groups = validationWithEncryption.class)
    @Unique(service = AccountsService.class, fieldName = "userName", message = Messages.ACCOUNTS_USER_NAME_UNIQUE, groups = validationWithEncryption.class)
    @Column(name = "user_name", nullable = false)
    private String userName;

    @NotNull(message = Messages.ACCOUNTS_EMAIL_NULL, groups = validationWithEncryption.class)
    @Email(message = Messages.ACCOUNTS_EMAIL, groups = validationWithEncryption.class)
    @Size(min = 5, max = 255, message = Messages.ACCOUNTS_EMAIL_LENGHT, groups = validationWithEncryption.class)
    @Unique(service = AccountsService.class, fieldName = "email", message = Messages.ACCOUNTS_EMAIL_UNIQUE, groups = validationWithEncryption.class)
    @Column(name = "email", nullable = false)
    private String email;

    @Password(groups = validationWithEncryption.class)
    @NotNull(message = Messages.ACCOUNTS_PASSWORD_NULL, groups = validationWithEncryption.class)
    @Column(name = "password", nullable = false)
    private String password;

    @Transient
    private String confirmPassword;

    @Column(name = "is_activated", nullable = false)
    private Boolean isActivated;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @NotNull(message = Messages.ENTITY_CREATOR_ID, groups = validationWithoutEncryption.class)
    @Positive(message = Messages.ENTITY_IDS, groups = validationWithoutEncryption.class)
    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    @Positive(message = Messages.ENTITY_IDS, groups = validationWithEncryption.class)
    @Column(name = "updated_by", updatable = false)
    private Long updatedBy;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Positive(message = Messages.ENTITY_IDS, groups = validationWithEncryption.class)
    @Column(name = "deleted_by")
    private Long deletedBy;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @Transient
    private HashMap<String, String> validations = new HashMap<>();

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
    }

    public Passwords getPasswords() {
        return this.passwords;
    }

    public void setPasswords(Passwords passwords) {
        this.passwords = passwords;
    }

    public Activations getActivations() {
        return this.activations;
    }

    public void setActivations(Activations activations) {
        this.activations = activations;
    }

    public Set<Roles> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public Set<AccountPreferenceItems> getAccountPreferenceItems() {
        return this.accountPreferenceItems;
    }

    public void setAccountPreferenceItems(Set<AccountPreferenceItems> accountPreferenceItems) {
        this.accountPreferenceItems = accountPreferenceItems;
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

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public Boolean getActivated() {
        return this.isActivated;
    }

    public void setActivated(Boolean activated) {
        isActivated = activated;
    }

    public Boolean getDeleted() {
        return this.isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getDeletedBy() {
        return this.deletedBy;
    }

    public void setDeletedBy(Long deletedBy) {
        this.deletedBy = deletedBy;
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
        this.isDeleted = false;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}