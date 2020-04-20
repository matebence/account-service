package com.blesk.accountservice.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@DynamicInsert
@DynamicUpdate
@Entity(name = "AccountRoleItems")
@Table(name = "account_role_items", uniqueConstraints = {@UniqueConstraint(name = "account_role_id", columnNames = "account_role_id")})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = AccountRoles.class)
@JsonIgnoreProperties(value = {"accountRoleId"})
public class AccountRoles implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_role_id")
    private Long accountRoleId;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Accounts accounts;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Roles roles;

    @Transient
    private Boolean isDeleted;

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

    public Long getAccountRoleId() {
        return this.accountRoleId;
    }

    public void setAccountRoleId(Long accountRoleId) {
        this.accountRoleId = accountRoleId;
    }

    public Accounts getAccounts() {
        return this.accounts;
    }

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }

    public Roles getRoles() {
        return this.roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public Boolean getDeleted() {
        return this.isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        this.isDeleted = deleted;
    }
}