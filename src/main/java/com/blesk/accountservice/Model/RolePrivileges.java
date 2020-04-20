package com.blesk.accountservice.Model.RolePrivilegeItems;

import com.blesk.accountservice.Model.Privileges;
import com.blesk.accountservice.Model.Roles;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@DynamicInsert
@DynamicUpdate
@Entity(name = "RolePrivilegeItems")
@Table(name = "role_privilege_items", uniqueConstraints = {@UniqueConstraint(name = "role_privilege_id", columnNames = "role_privilege_id"), @UniqueConstraint(name = "role_privilege_role_id", columnNames = "role_id"), @UniqueConstraint(name = "role_privilege_privilege_id", columnNames = "privilege_id")})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = RolePrivileges.class)
public class RolePrivileges implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_privilege_id")
    private Long rolePrivilegeId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", nullable = false)
    private Roles roles;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "privilege_id", nullable = false)
    private Privileges privileges;

    public RolePrivileges() {
    }

    @Transient
    public Long getRolePrivilegeId() {
        return rolePrivilegeId;
    }

    public void setRolePrivilegeId(Long rolePrivilegeId) {
        this.rolePrivilegeId = rolePrivilegeId;
    }

    @Transient
    public Roles getRoles() {
        return this.roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    @Transient
    public Privileges getPrivileges() {
        return this.privileges;
    }

    public void setPrivileges(Privileges privileges) {
        this.privileges = privileges;
    }
}