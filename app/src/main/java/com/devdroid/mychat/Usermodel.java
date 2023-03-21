package com.devdroid.mychat;

public class Usermodel {
    private String userId, username,userEmail,userpassword;

    public Usermodel() {
    }

    public Usermodel(String userId, String username, String userEmail, String userpassword) {
        this.userId = userId;
        this.username = username;
        this.userEmail = userEmail;
        this.userpassword = userpassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    @Override
    public String toString() {
        return "Usermodel{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userpassword='" + userpassword + '\'' +
                '}';
    }
}


