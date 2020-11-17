package com.blesk.accountservice.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

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
    private Boolean isSet;

    @Column(name = "content")
    private String content;

    @Column(name = "value")
    private Double value;

    public AccountPreferences(String content, Double value, Accounts accounts, Preferences preferences) {
        this.content = content;
        this.value = value;
        this.accounts = accounts;
        this.preferences = preferences;
    }

    public AccountPreferences(String content, Double value) {
        this.content = content;
        this.value = value;
    }

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

    public Boolean getIsSet() {
        return this.isSet;
    }

    public void setIsSet(Boolean isSet) {
        this.isSet = isSet;
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
}