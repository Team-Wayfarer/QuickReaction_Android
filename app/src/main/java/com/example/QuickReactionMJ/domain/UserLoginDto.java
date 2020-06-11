package com.example.QuickReactionMJ.domain;

public class UserLoginDto {
    String email;
    String password;
    String duid;

    @Override
    public String toString() {
        return "UserLoginDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", token='" + duid + '/' +
                '}';
    }

    public UserLoginDto(String email, String password, String duid) {
        this.email = email;
        this.password = password;
        this.duid = duid;
    }
}
