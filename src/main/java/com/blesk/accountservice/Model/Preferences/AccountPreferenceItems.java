package com.blesk.accountservice.Model.Preferences;

import com.blesk.accountservice.Model.Accounts;
import com.blesk.accountservice.Value.Messages;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity(name = "AccountPreferenceItems")
@Table(name = "account_preference_items")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = AccountPreferenceItems.class)
@JsonIgnoreProperties(value={ "accounts", "preferences"})
public class AccountPreferenceItems implements Serializable {

    @EmbeddedId
    private AccountPreferenceItemsId id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @MapsId("accountId")
    private Accounts accounts;

    @ManyToOne(fetch = FetchType.EAGER)
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

    public AccountPreferenceItems() {
    }

    public AccountPreferenceItemsId getId() {
        return this.id;
    }

    public void setId(AccountPreferenceItemsId id) {
        this.id = id;
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
        isSet = set;
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

    @PrePersist
    protected void prePersist() {
        this.isDeleted = false;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    @PreRemove
    protected void preRemove() {
        this.deletedAt = new Timestamp(System.currentTimeMillis());
    }
}