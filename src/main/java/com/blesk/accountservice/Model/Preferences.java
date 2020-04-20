package com.blesk.accountservice.Model;

import com.blesk.accountservice.Value.Messages;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@DynamicInsert
@DynamicUpdate
@Entity(name = "Preferences")
@Table(name = "preferences", uniqueConstraints = {@UniqueConstraint(name = "preference_id", columnNames = "preference_id"), @UniqueConstraint(name = "preference_name", columnNames = "name")})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = Preferences.class)
@JsonIgnoreProperties(value = {"accountPreferences"})
@SQLDelete(sql = "UPDATE account_preference_items SET is_deleted = TRUE, deleted_at = NOW() WHERE preference_id = ?")
public class Preferences implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "preference_id")
    private Long preferenceId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "preferences")
    private Set<AccountPreferences> accountPreferences = new HashSet<AccountPreferences>();

    @NotNull(message = Messages.PREFERENCES_NULL)
    @Size(min = 3, max = 255, message = Messages.PREFERENCES_SIZE)
    @Column(name = "name", nullable = false)
    private String name;

    public Preferences(String name) {
        this.name = name;
    }

    public Preferences() {
    }

    public Long getPreferenceId() {
        return this.preferenceId;
    }

    public void setPreferenceId(Long preferenceId) {
        this.preferenceId = preferenceId;
    }

    public void addAccount(AccountPreferences accountPreferences) {
        accountPreferences.setPreferences(this);
        this.accountPreferences.add(accountPreferences);
    }

    public void removeAccount(AccountPreferences accountPreferences) {
        accountPreferences.getAccounts().getAccountPreferences().remove(accountPreferences);
        this.accountPreferences.remove(accountPreferences);
        accountPreferences.setPreferences(null);
        accountPreferences.setAccounts(null);
    }

    public Set<AccountPreferences> getAccountPreferences() {
        return this.accountPreferences;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}