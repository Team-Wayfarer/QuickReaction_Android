package com.example.QuickReactionMJ.domain;

public class SpotAdminLoginDto {

   //private Long id;
    private String email;
    private String password;
    //private String contact;
    //private Spot spot;


    @Override
    public String toString() {
        return "SpotAdminLoginDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public SpotAdminLoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
