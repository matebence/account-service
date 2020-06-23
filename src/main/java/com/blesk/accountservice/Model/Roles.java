package com.blesk.accountservice.Model;

import com.blesk.accountservice.Value.Messages;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Entity(name = "Roles")
@Table(name = "roles", uniqueConstraints = {@UniqueConstraint(name = "role_id", columnNames = "role_id"), @UniqueConstraint(name = "role_name", columnNames = "name")})
@SQLDelete(sql = "UPDATE roles SET is_deleted = TRUE, deleted_at = NOW() WHERE role_id = ?")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = Roles.class)
@JsonIgnoreProperties(value = {"accountRoles"})
public class Roles implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "roles")
    private Set<AccountRoles> accountRoles = new HashSet<AccountRoles>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "roles")
    private Set<RolePrivileges> rolePrivileges = new HashSet<RolePrivileges>();

    @NotNull(message = Messages.ROLES_NULL)
    @Size(min = 3, max = 255, message = Messages.ROLES_SIZE)
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

    public Roles(String name, Boolean isDeleted) {
        this.name = name;
        this.isDeleted = isDeleted;
    }

    public Roles() {
    }

    public Long getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public void addPrivilege(RolePrivileges rolePrivileges) {
        rolePrivileges.setRoles(this);
        this.rolePrivileges.add(rolePrivileges);
    }

    public void removePrivilege(RolePrivileges rolePrivileges) {
        rolePrivileges.getPrivileges().getRolePrivileges().remove(rolePrivileges);
        this.rolePrivileges.remove(rolePrivileges);
        rolePrivileges.setRoles(null);
        rolePrivileges.setPrivileges(null);
    }

    public void removeAllPrivileges(Set<RolePrivileges> rolePrivileges){
        for(RolePrivileges rolePrivilege : rolePrivileges){
            rolePrivilege.getPrivileges().getRolePrivileges().remove(rolePrivilege);
            this.rolePrivileges.remove(rolePrivilege);
            rolePrivilege.setRoles(null);
            rolePrivilege.setPrivileges(null);
        }
    }

    public void addAllPrivileges(Set<RolePrivileges> rolePrivileges){
        for(RolePrivileges rolePrivilege : rolePrivileges){
            rolePrivilege.setRoles(this);
            this.rolePrivileges.add(rolePrivilege);
        }
    }

    public Set<RolePrivileges> getRolePrivileges() {
        return this.rolePrivileges;
    }

    public void addAccount(AccountRoles accountRoles) {
        accountRoles.setRoles(this);
        this.accountRoles.add(accountRoles);
    }

    public void removeAccount(AccountRoles accountRoles) {
        accountRoles.getAccounts().getAccountRoles().remove(accountRoles);
        this.accountRoles.remove(accountRoles);
        accountRoles.setRoles(null);
        accountRoles.setAccounts(null);
    }

    public void removeAllAccounts(Set<AccountRoles> accountRoles){
        for(AccountRoles accountRole : accountRoles){
            accountRole.getAccounts().getAccountRoles().remove(accountRole);
            this.accountRoles.remove(accountRole);
            accountRole.setRoles(null);
            accountRole.setAccounts(null);
        }
    }

    public void addAllAccounts(Set<AccountRoles> accountRoles){
        for(AccountRoles accountRole : accountRoles){
            accountRole.setRoles(this);
            this.accountRoles.add(accountRole);
        }
    }

    public Set<AccountRoles> getAccountRoles() {
        return this.accountRoles;
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