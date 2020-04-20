package com.blesk.accountservice.Model.AccountRoleItems;

import com.blesk.accountservice.Model.Accounts;
import com.blesk.accountservice.Model.Roles;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@DynamicInsert
@DynamicUpdate
@Entity(name = "AccountRoleItems")
@Table(name = "account_role_items", uniqueConstraints = {@UniqueConstraint(name = "account_role_id", columnNames = "account_role_id"), @UniqueConstraint(name = "account_role_account_id", columnNames = "account_id"), @UniqueConstraint(name = "account_role_role_id", columnNames = "role_id")})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = AccountRoles.class)
public class AccountRoles implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_role_id")
    private Long accountRoleId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", nullable = false)
    private Accounts accounts;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", nullable = false)
    private Roles roles;

    public AccountRoles(Accounts accounts, Roles roles) {
        this.accounts = accounts;
        this.roles = roles;
    }

    public AccountRoles(Accounts accounts) {
        this.accounts = accounts;
    }

    public AccountRoles(Roles roles) {
        this.roles = roles;
    }

    public AccountRoles() {
    }

    @Transient
    public Accounts getAccounts() {
        return this.accounts;
    }

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }

    @Transient
    public Roles getRoles() {
        return this.roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }
}