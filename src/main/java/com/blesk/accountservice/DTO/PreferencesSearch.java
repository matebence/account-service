package com.blesk.accountservice.DTO;

public class PreferencesSearch {

    private Long preferenceId;
    private String name;
    private Long accountId;

    public PreferencesSearch(Long preferenceId, String name, Long accountId) {
        this.preferenceId = preferenceId;
        this.name = name;
        this.accountId = accountId;
    }

    public Long getPreferenceId() {
        return this.preferenceId;
    }

    public void setPreferenceId(Long preferenceId) {
        this.preferenceId = preferenceId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAccountId() {
        return this.accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}