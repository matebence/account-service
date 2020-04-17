package com.blesk.accountservice.Model;

import com.blesk.accountservice.Model.AccountRoleItems.AccountRoles;
import com.blesk.accountservice.Model.RolePrivilegeItems.RolePrivileges;
import com.blesk.accountservice.Value.Messages;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = Roles.class)
@JsonIgnoreProperties(value = {"accounts"})
public class Roles implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "accountRoleIds.roles")
    private Set<AccountRoles> accountRoles = new HashSet<AccountRoles>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "rolePrivilegeIds.roles")
    private Set<RolePrivileges> rolePrivileges = new HashSet<RolePrivileges>();

    @NotNull(message = Messages.ROLES_NULL)
    @Size(min = 3, max = 255, message = Messages.ROLES_SIZE)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @NotNull(message = Messages.ENTITY_CREATOR_ID)
    @Column(name = "created_by", updatable = false, nullable = false)
    private Long createdBy;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_by")
    private Long deletedBy;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    public Roles() {
    }

    public Roles(String name, Boolean isDeleted, Long createdBy, Long updatedBy, Long deletedBy) {
        this.name = name;
        this.isDeleted = isDeleted;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.deletedBy = deletedBy;
    }

    public Long getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public void addPrivilege(RolePrivileges rolePrivileges) {
        this.rolePrivileges.add(rolePrivileges);
    }

    public Set<RolePrivileges> getRolePrivileges() {
        return this.rolePrivileges;
    }

    public void setRolePrivileges(Set<RolePrivileges> privileges) {
        this.rolePrivileges = privileges;
    }

    public void addRolePrivileges(RolePrivileges rolePrivileges) {
        this.rolePrivileges.add(rolePrivileges);
    }

    public Set<AccountRoles> getAccountRoles() {
        return this.accountRoles;
    }

    public void setAccountRoles(Set<AccountRoles> accounts) {
        this.accountRoles = accounts;
    }

    public void addAccountRoles(AccountRoles accountRoles) {
        this.accountRoles.add(accountRoles);
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
        isDeleted = deleted;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getDeletedBy() {
        return this.deletedBy;
    }

    public void setDeletedBy(Long deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Timestamp getDeletedAt() {
        return this.deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    @PrePersist
    protected void prePersist() {
        this.isDeleted = false;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
        if(this.deletedBy != null){
            this.deletedAt = new Timestamp(System.currentTimeMillis());
            this.isDeleted = true;
        }
    }
}