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
@Entity(name = "Privileges")
@Table(name = "privileges", uniqueConstraints = {@UniqueConstraint(name = "privilege_id", columnNames = "privilege_id"), @UniqueConstraint(name = "privilege_name", columnNames = "name")})
@SQLDelete(sql = "UPDATE privileges SET is_deleted = TRUE, deleted_at = NOW() WHERE privilege_id = ?")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = Privileges.class)
@JsonIgnoreProperties(value = {"rolePrivileges"})
public class Privileges implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "privilege_id")
    private Long privilegeId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "privileges")
    private Set<RolePrivileges> rolePrivileges = new HashSet<RolePrivileges>();

    @NotNull(message = Messages.PRIVILEGES_NULL)
    @Size(min = 3, max = 255, message = Messages.PRIVILEGES_SIZE)
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

    public Privileges(String name, Boolean isDeleted) {
        this.name = name;
        this.isDeleted = isDeleted;
    }

    public Privileges() {
    }

    public Long getPrivilegeId() {
        return this.privilegeId;
    }

    public void setPrivilegeId(Long privilegeId) {
        this.privilegeId = privilegeId;
    }

    public void addRole(RolePrivileges rolePrivileges) {
        rolePrivileges.setPrivileges(this);
        this.rolePrivileges.add(rolePrivileges);
    }

    public void removeRole(RolePrivileges rolePrivileges) {
        rolePrivileges.getRoles().getRolePrivileges().remove(rolePrivileges);
        this.rolePrivileges.remove(rolePrivileges);
        rolePrivileges.setPrivileges(null);
        rolePrivileges.setRoles(null);
    }

    public void removeAllRoles(Set<RolePrivileges> rolePrivileges){
        for(RolePrivileges rolePrivilege : rolePrivileges){
            rolePrivilege.getRoles().getRolePrivileges().remove(rolePrivilege);
            this.rolePrivileges.remove(rolePrivilege);
            rolePrivilege.setPrivileges(null);
            rolePrivilege.setRoles(null);
        }
    }

    public void addAllRoles(Set<RolePrivileges> rolePrivileges){
        for(RolePrivileges rolePrivilege : rolePrivileges){
            rolePrivilege.setPrivileges(this);
            this.rolePrivileges.add(rolePrivilege);
        }
    }

    public Set<RolePrivileges> getRolePrivileges() {
        return this.rolePrivileges;
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