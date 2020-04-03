package com.blesk.accountservice.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity(name = "logins")
@Table(name = "logins")
public class Logins implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_id")
    private Long loginId;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="account_id", nullable = false)
    private Accounts account;

    @Column(name = "no_trys", nullable=false)
    private Integer noTrys;

    @Column(name = "last_login", nullable=false)
    private java.sql.Timestamp lastLogin;

    @Column(name = "ip_address", nullable=false)
    private String ipAddress;

    public Logins() {
    }

    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }

    public Accounts getAccount() {
        return account;
    }

    public void setAccount(Accounts account) {
        this.account = account;
    }

    public Integer getNoTrys() {
        return noTrys;
    }

    public void setNoTrys(Integer noTrys) {
        this.noTrys = noTrys;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @PrePersist
    protected void prePersist() {
        this.noTrys = 0;
    }

    @PreUpdate
    protected void preUpdate() {
        this.lastLogin = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return "Logins{" +
                "loginId=" + loginId +
                ", account=" + account +
                ", noTrys=" + noTrys +
                ", lastLogin=" + lastLogin +
                ", ipAddress='" + ipAddress + '\'' +
                '}';
    }
}