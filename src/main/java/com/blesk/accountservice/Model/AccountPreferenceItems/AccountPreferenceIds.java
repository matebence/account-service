package com.blesk.accountservice.Model.AccountPreferenceItems;

import com.blesk.accountservice.Model.Accounts;
import com.blesk.accountservice.Model.Preferences;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class AccountPreferenceIds implements Serializable {

    @ManyToOne(cascade = CascadeType.ALL)
    private Accounts accounts;

    @ManyToOne(cascade = CascadeType.ALL)
    private Preferences preferences;

    public AccountPreferenceIds() {
    }

    public AccountPreferenceIds(Accounts accounts, Preferences preferences) {
        this.accounts = accounts;
        this.preferences = preferences;
    }

    public Accounts getAccounts(){
        return this.accounts;
    }

    public void setAccounts(Accounts accounts){
        this.accounts = accounts;
    }

    public Preferences getPreferences(){
        return this.preferences;
    }

    public void setPreferences(Preferences preferences){
        this.preferences = preferences;
    }
}