package com.blesk.accountservice.Model.AccountRoleItems;

import com.blesk.accountservice.Model.Accounts;
import com.blesk.accountservice.Model.Roles;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class AccountRoleIds {

    @ManyToOne(cascade = CascadeType.ALL)
    private Accounts accounts;

    @ManyToOne(cascade = CascadeType.ALL)
    private Roles roles;

    public AccountRoleIds() {
    }

    public AccountRoleIds(Accounts accounts, Roles roles) {
        this.accounts = accounts;
        this.roles = roles;
    }

    public Accounts getAccounts(){
        return this.accounts;
    }

    public void setAccounts(Accounts accounts){
        this.accounts = accounts;
    }

    public Roles getRoles(){
        return this.roles;
    }

    public void setRoles(Roles roles){
        this.roles = roles;
    }
}