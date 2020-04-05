package com.blesk.accountservice.Model.Preferences;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AccountPreferenceItemsId implements Serializable {

    @Column(name = "preference_id")
    private Long preferenceId;

    @Column(name = "account_id")
    private Long accountId;

    public AccountPreferenceItemsId() {
    }

    public Long getPreferenceId() {
        return preferenceId;
    }

    public void setPreferenceId(Long preferenceId) {
        this.preferenceId = preferenceId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}