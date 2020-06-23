package com.blesk.accountservice.Model;

import com.blesk.accountservice.Value.Messages;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@DynamicInsert
@DynamicUpdate
@Entity(name = "Preferences")
@Table(name = "preferences", uniqueConstraints = {@UniqueConstraint(name = "preference_id", columnNames = "preference_id"), @UniqueConstraint(name = "preference_name", columnNames = "name")})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = Preferences.class)
@SQLDelete(sql = "UPDATE preferences SET is_deleted = TRUE, deleted_at = NOW() WHERE preference_id = ?")
public class Preferences implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "preference_id")
    private Long preferenceId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "preferences")
    private Set<AccountPreferences> accountPreferences = new HashSet<AccountPreferences>();

    @NotNull(message = Messages.PREFERENCES_NULL)
    @Size(min = 3, max = 255, message = Messages.PREFERENCES_SIZE)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at", updatable = false)
    private Timestamp deletedAt;

    public Preferences(String name, Boolean isDeleted) {
        this.name = name;
        this.isDeleted = isDeleted;
    }

    public Preferences(String name) {
        this.name = name;
    }

    public Preferences() {
    }

    public Long getPreferenceId() {
        return this.preferenceId;
    }

    public void setPreferenceId(Long preferenceId) {
        this.preferenceId = preferenceId;
    }

    public void addAccount(AccountPreferences accountPreferences) {
        accountPreferences.setPreferences(this);
        this.accountPreferences.add(accountPreferences);
    }

    public void removeAccount(AccountPreferences accountPreferences) {
        accountPreferences.getAccounts().getAccountPreferences().remove(accountPreferences);
        this.accountPreferences.remove(accountPreferences);
        accountPreferences.setPreferences(null);
        accountPreferences.setAccounts(null);
    }

    public void removeAllAccounts(Set<AccountPreferences> accountPreferences){
        for(AccountPreferences accountPreference : accountPreferences){
            accountPreference.getAccounts().getAccountPreferences().remove(accountPreference);
            this.accountPreferences.remove(accountPreference);
            accountPreference.setPreferences(null);
            accountPreference.setAccounts(null);
        }
    }

    public void addAllAccounts(Set<AccountPreferences> accountPreferences){
        for(AccountPreferences accountPreference : accountPreferences){
            accountPreference.setPreferences(this);
            this.accountPreferences.add(accountPreference);
        }
    }

    public Set<AccountPreferences> getAccountPreferences() {
        return this.accountPreferences;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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