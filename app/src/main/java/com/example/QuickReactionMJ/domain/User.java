package com.example.QuickReactionMJ.domain;


public class User {

    private Long id;

    private String name;

    private String contact;

    public User(String name, String contact) {
        this.name = name;
        this.contact = contact;
    }
}