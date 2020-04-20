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

@DynamicInsert
@DynamicUpdate
@Entity(name = "Activations")
@Table(name = "activations", uniqueConstraints = {@UniqueConstraint(columnNames = {"activation_id"})})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = Activations.class)
public class Activations implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activation_id")
    private Long accountActivationId;

    @OneToOne
    @JoinColumn(name = "account_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Accounts accounts;

    @NotNull(message = Messages.ACTIVATIONS_TOKEN_NULL)
    @Column(name = "token", nullable = false)
    private String token;

    public Activations(Accounts accounts, String token) {
        this.accounts = accounts;
        this.token = token;
    }

    public Activations(String token) {
        this.token = token;
    }

    public Activations() {
    }

    public Long getAccountActivationId() {
        return this.accountActivationId;
    }

    public void setAccountActivationId(Long accountActivationId) {
        this.accountActivationId = accountActivationId;
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
}