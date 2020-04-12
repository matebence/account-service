package com.blesk.accountservice.Model;

import com.blesk.accountservice.Value.Messages;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Entity(name = "PasswordResetToken")
@Table(name = "password_reset_token", uniqueConstraints = {@UniqueConstraint(columnNames = {"password_reset_token_id"})})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = Passwords.class)
public class Passwords implements Serializable {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "password_reset_token_id")
    private Long passwordResetTokenId;

    @NotNull(message = Messages.PASSWORDS_TOKEN_NULL)
    @Column(name = "token", nullable = false)
    private String token;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "account_id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Accounts account;

    @FutureOrPresent(message = Messages.PASSWORDS_DATE_VALID)
    @NotNull(message = Messages.PASSWORDS_DATE_NULL)
    @Column(name = "expiry_date", nullable = false)
    private Date expiryDate;

    public Passwords() {
    }

    public Long getPasswordResetTokenId() {
        return this.passwordResetTokenId;
    }

    public void setPasswordResetTokenId(Long passwordResetTokenId) {
        this.passwordResetTokenId = passwordResetTokenId;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Accounts getAccount() {
        return this.account;
    }

    public void setAccount(Accounts account) {
        this.account = account;
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
}