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
@Entity(name = "RolePrivilegeItems")
@Table(name = "role_privilege_items", uniqueConstraints = {@UniqueConstraint(name = "role_privilege_id", columnNames = "role_privilege_id")})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = RolePrivileges.class)
@JsonIgnoreProperties(value = {"rolePrivilegeId"})
public class RolePrivileges implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_privilege_id")
    private Long rolePrivilegeId;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Roles roles;

    @ManyToOne
    @JoinColumn(name = "privilege_id")
    private Privileges privileges;

    public RolePrivileges(Roles roles, Privileges privileges) {
        this.roles = roles;
        this.privileges = privileges;
    }

    public RolePrivileges(Roles roles) {
        this.roles = roles;
    }

    public RolePrivileges(Privileges privileges) {
        this.privileges = privileges;
    }

    public RolePrivileges() {
    }

    public Long getRolePrivilegeId() {
        return this.rolePrivilegeId;
    }

    public void setRolePrivilegeId(Long rolePrivilegeId) {
        this.rolePrivilegeId = rolePrivilegeId;
    }

    public Roles getRoles() {
        return this.roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public Privileges getPrivileges() {
        return this.privileges;
    }

    public void setPrivileges(Privileges privileges) {
        this.privileges = privileges;
    }
}