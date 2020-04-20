package com.blesk.accountservice.Model;

import com.blesk.accountservice.Value.Messages;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@DynamicInsert
@DynamicUpdate
@Entity(name = "Passwords")
@Table(name = "passwords", uniqueConstraints = {@UniqueConstraint(columnNames = {"password_id"})})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = Passwords.class)
public class Passwords implements Serializable {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "password_id")
    private Long passwordTokenId;

    @OneToOne
    @JoinColumn(name = "account_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Accounts accounts;

    @NotNull(message = Messages.PASSWORDS_TOKEN_NULL)
    @Column(name = "token", nullable = false)
    private String token;

    @FutureOrPresent(message = Messages.PASSWORDS_DATE_VALID)
    @NotNull(message = Messages.PASSWORDS_DATE_NULL)
    @Column(name = "expiry_date", nullable = false)
    private Date expiryDate;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    public Passwords(Accounts accounts, String token) {
        this.accounts = accounts;
        this.token = token;
        setExpiryDate();
    }

    public Passwords(String token) {
        this.token = token;
        setExpiryDate();
    }

    public Passwords() {
    }

    public Long getPasswordTokenId() {
        return this.passwordTokenId;
    }

    public void setPasswordTokenId(Long passwordTokenId) {
        this.passwordTokenId = passwordTokenId;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Accounts getAccounts() {
        return this.accounts;
    }

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }

    public Date getExpiryDate() {
        return this.expiryDate;
    }

    public void setExpiryDate() {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, Passwords.EXPIRATION);
        this.expiryDate = new Date(cal.getTime().getTime());
    }

    public Timestamp getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @PrePersist
    protected void prePersist() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
}