package com.example.QuickReactionMJ.domain;

public class UserLoginDto {
    String email;
    String password;

    @Override
    public String toString() {
        return "UserLoginDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public UserLoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
