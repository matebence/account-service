package com.blesk.accountservice.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@DynamicInsert
@DynamicUpdate
@Entity(name = "AccountPreferencesItems")
@Table(name = "account_preference_items", uniqueConstraints = {@UniqueConstraint(name = "account_preference_id", columnNames = "account_preference_id")})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = AccountPreferences.class)
@JsonIgnoreProperties(value = {"accountPreferenceId"})
public class AccountPreferences implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_preference_id")
    private Long accountPreferenceId;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Accounts accounts;

    @ManyToOne
    @JoinColumn(name = "preference_id")
    private Preferences preferences;

    @Column(name = "is_set")
    private boolean isSet;

    @Column(name = "content")
    private String content;

    @Column(name = "value")
    private Double value;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at", updatable = false)
    private Timestamp deletedAt;

    public AccountPreferences(Accounts accounts, Preferences preferences) {
        this.accounts = accounts;
        this.preferences = preferences;
    }

    public AccountPreferences(Accounts accounts) {
        this.accounts = accounts;
    }

    public AccountPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    public AccountPreferences(boolean isSet, String content, Double value, Boolean isDeleted) {
        this.isSet = isSet;
        this.content = content;
        this.value = value;
        this.isDeleted = isDeleted;
    }

    public AccountPreferences(boolean isSet, String content, Double value, Boolean isDeleted, Accounts accounts, Preferences preferences) {
        this.isSet = isSet;
        this.content = content;
        this.value = value;
        this.isDeleted = isDeleted;
        this.accounts = accounts;
        this.preferences = preferences;
    }

    public AccountPreferences() {
    }

    public Long getAccountPreferenceId() {
        return this.accountPreferenceId;
    }

    public void setAccountPreferenceId(Long accountPreferenceId) {
        this.accountPreferenceId = accountPreferenceId;
    }

    public Accounts getAccounts() {
        return this.accounts;
    }

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }

    public Preferences getPreferences() {
        return this.preferences;
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

    public Double getValue() {
        return this.value;
    }

    public void setValue(Double value) {
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
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}