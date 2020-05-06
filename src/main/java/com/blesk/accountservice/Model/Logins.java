package com.blesk.accountservice.Model;

import com.blesk.accountservice.Value.Messages;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

@DynamicInsert
@DynamicUpdate
@Entity(name = "Logins")
@Table(name = "logins", uniqueConstraints = {@UniqueConstraint(columnNames = {"login_id"})})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = Logins.class)
public class Logins implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_id")
    private Long loginId;

    @Version
    private Long version;

    @OneToOne
    @JoinColumn(name = "account_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Accounts accounts;

    @NotNull(message = Messages.LOGIN_TIMESTAMP_NULL)
    @Column(name = "last_login", nullable = false)
    private Timestamp lastLogin;

    @NotNull(message = Messages.LOGIN_IP_ADDRESS_NULL)
    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    public Logins(Accounts accounts, Timestamp lastLogin, String ipAddress) {
        this.accounts = accounts;
        this.lastLogin = lastLogin;
        this.ipAddress = ipAddress;
    }

    public Logins(Timestamp lastLogin, String ipAddress) {
        this.lastLogin = lastLogin;
        this.ipAddress = ipAddress;
    }

    public Logins() {
    }

    public Long getLoginId() {
        return this.loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }

    public Accounts getAccounts() {
        return this.accounts;
    }

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }

    public Timestamp getLastLogin() {
        return this.lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @PreUpdate
    protected void preUpdate() {
        this.lastLogin = new Timestamp(System.currentTimeMillis());
    }
}