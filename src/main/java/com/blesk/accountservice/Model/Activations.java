package com.blesk.accountservice.Model;

import com.blesk.accountservice.Value.Messages;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity(name = "AccountActivations")
@Table(name = "account_activations", uniqueConstraints = {@UniqueConstraint(columnNames = {"account_activation_id"})})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = Activations.class)
public class Activations implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_activation_id")
    private Long accountActivationId;

    @NotNull(message = Messages.ACTIVATIONS_TOKEN_NULL)
    @Column(name = "token", nullable = false)
    private String token;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "account_id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Accounts account;

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

    public Accounts getAccount() {
        return this.account;
    }

    public void setAccount(Accounts account) {
        this.account = account;
    }
}