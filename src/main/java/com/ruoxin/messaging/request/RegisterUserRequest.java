package com.ruoxin.messaging.request;

import com.ruoxin.messaging.enums.Gender;

public class RegisterUserRequest {
    private String username; // alphanumeric
    private String password;
    private String repeatPassword;
    private String nickname;
    private String email;
    private String address;
    private Gender gender;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "RegisterUserRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", repeatPassword='" + repeatPassword + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", gender=" + gender +
                '}';
    }
}
