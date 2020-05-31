package com.example.QuickReactionMJ.domain;

public class Address {

    private String city;
    private String gunGu;
    private String zipcode;
    private String deatail;

    protected Address(){

    }

    public Address(String city, String gunGu, String zipcode, String deatail) {
        this.city = city;
        this.gunGu = gunGu;
        this.zipcode = zipcode;
        this.deatail = deatail;
    }
}


