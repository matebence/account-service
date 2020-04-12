package com.blesk.accountservice.Model.Preferences;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;

@Embeddable
@Table(name = "account_preference_items_id", uniqueConstraints = {@UniqueConstraint(columnNames = {"preference_id", "account_id"})})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = AccountPreferenceItemsId.class)
public class AccountPreferenceItemsId implements Serializable {

    @Column(name = "preference_id")
    private Long preferenceId;

    @Column(name = "account_id")
    private Long accountId;

    public AccountPreferenceItemsId() {
    }

    public Long getPreferenceId() {
        return this.preferenceId;
    }

    public void setPreferenceId(Long preferenceId) {
        this.preferenceId = preferenceId;
    }

    public Long getAccountId() {
        return this.accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}