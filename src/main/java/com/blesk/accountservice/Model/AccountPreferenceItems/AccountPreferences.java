package com.blesk.accountservice.Model.AccountPreferenceItems;

import com.blesk.accountservice.Model.Accounts;
import com.blesk.accountservice.Model.Preferences;
import com.blesk.accountservice.Value.Messages;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @NotNull(message = Messages.ENTITY_CREATOR_ID)
    @Column(name = "created_by", updatable = false, nullable = false)
    private Long createdBy;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_by")
    private Long deletedBy;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    public AccountPreferences() {
    }

    public AccountPreferences(boolean isSet, String content, int value, Boolean isDeleted, Long createdBy, Long updatedBy, Long deletedBy) {
        this.isSet = isSet;
        this.content = content;
        this.value = value;
        this.isDeleted = isDeleted;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.deletedBy = deletedBy;
    }

    public AccountPreferenceIds getAccountPreferenceIds() {
        return this.accountPreferenceIds;
    }

    public void setAccountPreferenceIds(AccountPreferenceIds accountPreferenceIds) {
        this.accountPreferenceIds = accountPreferenceIds;
    }

    @Transient
    public Accounts getAccounts() {
        return getAccountPreferenceIds().getAccounts();
    }

    public void setAccounts(Accounts accounts) {
        getAccountPreferenceIds().setAccounts(accounts);
    }

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

    @PrePersist
    protected void prePersist() {
        this.isDeleted = false;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
        if (this.deletedBy != null) {
            this.deletedAt = new Timestamp(System.currentTimeMillis());
            this.isDeleted = true;
        }
    }
}