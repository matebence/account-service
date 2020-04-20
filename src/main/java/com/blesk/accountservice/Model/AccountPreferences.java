package com.blesk.accountservice.Model.AccountPreferenceItems;

import com.blesk.accountservice.Model.Accounts;
import com.blesk.accountservice.Model.Preferences;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@DynamicInsert
@DynamicUpdate
@Entity(name = "AccountPreferencesItems")
@Table(name = "account_preference_items", uniqueConstraints = {@UniqueConstraint(name = "account_preference_id", columnNames = "account_preference_id"), @UniqueConstraint(name = "account_preference_account_id", columnNames = "account_id"), @UniqueConstraint(name = "account_preference_preference_id", columnNames = "preference_id")})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = AccountPreferences.class)
@JsonIgnoreProperties(value = {"accountPreferenceId", "accounts", "preferences"})
public class AccountPreferences implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_preference_id")
    private Long accountPreferenceId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", nullable = false)
    private Accounts accounts;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "preference_id", nullable = false)
    private Preferences preferences;

    @Column(name = "is_set", nullable = true)
    private boolean isSet;

    @Column(name = "content", nullable = true)
    private String content;

    @Column(name = "value", nullable = true)
    private int value;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at", updatable = false)
    private Timestamp deletedAt;

    public AccountPreferences() {
    }

    public AccountPreferences(boolean isSet, String content, int value, Boolean isDeleted) {
        this.isSet = isSet;
        this.content = content;
        this.value = value;
        this.isDeleted = isDeleted;
    }

    @Transient
    public Long getAccountPreferenceId() {
        return accountPreferenceId;
    }

    public void setAccountPreferenceId(Long accountPreferenceId) {
        this.accountPreferenceId = accountPreferenceId;
    }

    @Transient
    public Accounts getAccounts() {
        return this.accounts;
    }

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }

    @Transient
    public Preferences getPreferences() {
        return this.getPreferences();
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    public boolean isSet() {
        return this.isSet;
    }

    public void setSet(boolean set) {
        this.isSet = set;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
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

    @PrePersist
    protected void prePersist() {
        this.isDeleted = false;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}