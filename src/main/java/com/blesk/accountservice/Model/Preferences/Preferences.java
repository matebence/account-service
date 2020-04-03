package com.blesk.accountservice.Model.Preferences;

import com.blesk.authorizationserver.Values.Messages;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "preferences")
@Table(name = "preferences")
public class Preferences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "preference_id")
    private Long preferenceId;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "accounts")
    private Set<AccountPreferenceItems> accountPreferenceItems = new HashSet<>();

    @NotNull(message = Messages.PREFERENCES_NOT_NULL)
    @Size(min = 3, max = 255, message = Messages.PREFERENCES_SIZE)
    @Column(name = "name", nullable=false, unique = true)
    private String name;

    public Preferences() {
    }

    public Long getPreferenceId() {
        return preferenceId;
    }

    public void setPreferenceId(Long preferenceId) {
        this.preferenceId = preferenceId;
    }

    public Set<AccountPreferenceItems> getAccountPreferenceItems() {
        return accountPreferenceItems;
    }

    public void setAccountPreferenceItems(Set<AccountPreferenceItems> accountPreferenceItems) {
        this.accountPreferenceItems = accountPreferenceItems;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Preferences{" +
                "preferenceId=" + preferenceId +
                ", accountPreferenceItems=" + accountPreferenceItems +
                ", name='" + name + '\'' +
                '}';
    }
}
