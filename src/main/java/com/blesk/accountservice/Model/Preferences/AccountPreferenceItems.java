package com.blesk.accountservice.Model.Preferences;

import com.blesk.accountservice.Model.Accounts;
import com.blesk.accountservice.Values.Messages;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity(name = "AccountPreferenceItems")
@Table(name = "account_preference_items")
public class AccountPreferenceItems implements Serializable {

    @EmbeddedId
    private AccountPreferenceItemsId id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("accountId")
    private Accounts accounts;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("preferenceId")
    private Preferences preferences;

    @Column(name = "is_set", nullable = true)
    private boolean isSet;

    @Column(name = "content", nullable = true)
    private String content;

    @Column(name = "value", nullable = true)
    private int value;

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

    public AccountPreferenceItems() {
    }

    public AccountPreferenceItemsId getId() {
        return id;
    }

    public void setId(AccountPreferenceItemsId id) {
        this.id = id;
    }

    public Accounts getAccounts() {
        return accounts;
    }

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    public boolean isSet() {
        return isSet;
    }

    public void setSet(boolean set) {
        isSet = set;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
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
        this.isDeleted = false;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}