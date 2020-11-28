package com.qrofeus.requestmanager;

public class UserAccount {
    private final String username;
    private final String password;
    private final String mailID;
    private final String phone_number;
    private final String type;

    public UserAccount(String username, String password, String mailID, String phone_number, String type) {
        this.username = username;
        this.password = password;
        this.mailID = mailID;
        this.phone_number = phone_number;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getMailID() {
        return mailID;
    }

    public String getPhone_number() {
        return phone_number;
    }
}
