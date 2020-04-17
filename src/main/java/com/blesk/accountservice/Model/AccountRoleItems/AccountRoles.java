package com.blesk.accountservice.Model.AccountRoleItems;

import com.blesk.accountservice.Model.Accounts;
import com.blesk.accountservice.Model.Roles;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@DynamicInsert
@DynamicUpdate
@Entity(name = "AccountRoleItems")
@Table(name = "account_role_items")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = AccountRoles.class)
@AssociationOverrides({@AssociationOverride(name = "accountRoleIds.accounts", joinColumns = @JoinColumn(name = "account_id")), @AssociationOverride(name = "accountRoleIds.roles", joinColumns = @JoinColumn(name = "role_id"))})
public class AccountRoles {

    @EmbeddedId
    private AccountRoleIds accountRoleIds = new AccountRoleIds();

    public AccountRoles(Accounts accounts, Roles roles) {
        getAccountRoleIds().setAccounts(accounts);
        getAccountRoleIds().setRoles(roles);
    }

    public AccountRoles(Accounts accounts) {
        getAccountRoleIds().setAccounts(accounts);
    }

    public AccountRoles(Roles roles) {
        getAccountRoleIds().setRoles(roles);
    }

    public AccountRoles() {
    }

    public AccountRoleIds getAccountRoleIds() {
        return this.accountRoleIds;
    }

    public void setAccountRoleIds(AccountRoleIds accountRoleIds) {
        this.accountRoleIds = accountRoleIds;
    }

    @Transient
    public Accounts getAccounts() {
        return getAccountRoleIds().getAccounts();
    }

    public void setAccounts(Accounts accounts) {
        getAccountRoleIds().setAccounts(accounts);
    }

    @Transient
    public Roles getRoles() {
        return getAccountRoleIds().getRoles();
    }

    public void setRoles(Roles roles) {
        getAccountRoleIds().setRoles(roles);
    }
}