package com.blesk.accountservice.Model.RolePrivilegeItems;

import com.blesk.accountservice.Model.Privileges;
import com.blesk.accountservice.Model.Roles;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class RolePrivilegeIds {

    @ManyToOne(cascade = CascadeType.ALL)
    private Roles roles;

    @ManyToOne(cascade = CascadeType.ALL)
    private Privileges privileges;


    public RolePrivilegeIds() {
    }

    public RolePrivilegeIds(Roles roles, Privileges privileges) {
        this.roles = roles;
        this.privileges = privileges;
    }

    public Roles getRoles(){
        return this.roles;
    }

    public void setRoles(Roles roles){
        this.roles = roles;
    }

    public Privileges getPrivileges(){
        return this.privileges;
    }

    public void setPrivileges(Privileges privileges){
        this.privileges = privileges;
    }
}