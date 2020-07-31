package com.blesk.accountservice.DTO;

public class PreferencesSearch {

    private Long preferenceId;
    private String name;
    private String content;
    private Boolean isSet;
    private Double value;
    private Long accountId;

    public PreferencesSearch(Long preferenceId, String name, String content, Boolean isSet, Double value, Long accountId) {
        this.preferenceId = preferenceId;
        this.name = name;
        this.content = content;
        this.isSet = isSet;
        this.value = value;
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

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getSet() {
        return this.isSet;
    }

    public void setSet(Boolean set) {
        this.isSet = set;
    }

    public Double getValue() {
        return this.value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Long getAccountId() {
        return this.accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}