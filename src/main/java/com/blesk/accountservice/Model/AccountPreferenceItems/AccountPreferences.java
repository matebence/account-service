package com.blesk.accountservice.Model.AccountPreferenceItems;

import com.blesk.accountservice.Model.Accounts;
import com.blesk.accountservice.Model.Preferences;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "account_preference_items")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = AccountPreferences.class)
@AssociationOverrides({@AssociationOverride(name = "accountPreferenceIds.accounts", joinColumns = @JoinColumn(name = "account_id")), @AssociationOverride(name = "accountPreferenceIds.preferences", joinColumns = @JoinColumn(name = "preference_id"))})
public class AccountPreferences implements Serializable {

    @EmbeddedId
    private AccountPreferenceIds accountPreferenceIds = new AccountPreferenceIds();

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

    public AccountPreferenceIds getAccountPreferenceIds() {
        return this.accountPreferenceIds;
    }

    public void setAccountPreferenceIds(AccountPreferenceIds accountPreferenceIds) {
        this.accountPreferenceIds = accountPreferenceIds;
    }

    @JsonIgnore
    @Transient
    public Accounts getAccounts() {
        return getAccountPreferenceIds().getAccounts();
    }

    public void setAccounts(Accounts accounts) {
        getAccountPreferenceIds().setAccounts(accounts);
    }

    @JsonIgnore
    @Transient
    public Preferences getPreferences() {
        return getAccountPreferenceIds().getPreferences();
    }

    public void setPreferences(Preferences preferences) {
        getAccountPreferenceIds().setPreferences(preferences);
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