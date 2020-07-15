package com.blesk.accountservice.DTO.JPQL;

public class AccountJoinValuesByColumn {

    private String userName;
    private String email;
    private String name;

    public AccountJoinValuesByColumn(String userName, String email, String name){
        this.userName = userName;
        this.email = email;
        this.name = name;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}