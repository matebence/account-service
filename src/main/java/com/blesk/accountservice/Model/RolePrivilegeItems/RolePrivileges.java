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
@Table(name = "role_privilege_items")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = RolePrivileges.class)
@AssociationOverrides({@AssociationOverride(name = "rolePrivilegeIds.roles", joinColumns = @JoinColumn(name = "role_id")), @AssociationOverride(name = "rolePrivilegeIds.privileges", joinColumns = @JoinColumn(name = "privilege_id"))})
public class RolePrivileges implements Serializable {

    @EmbeddedId
    private RolePrivilegeIds rolePrivilegeIds = new RolePrivilegeIds();

    public RolePrivileges() {
    }

    public RolePrivilegeIds getRolePrivilegeIds() {
        return this.rolePrivilegeIds;
    }

    public void setRolePrivilegeIds(RolePrivilegeIds rolePrivilegeIds) {
        this.rolePrivilegeIds = rolePrivilegeIds;
    }

    @Transient
    public Roles getRoles() {
        return getRolePrivilegeIds().getRoles();
    }

    public void setRoles(Roles roles) {
        getRolePrivilegeIds().setRoles(roles);
    }

    @Transient
    public Privileges getPrivileges() {
        return getRolePrivilegeIds().getPrivileges();
    }

    public void setPrivileges(Privileges privileges) {
        getRolePrivilegeIds().setPrivileges(privileges);
    }
}