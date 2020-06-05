package com.example.QuickReactionMJ.domain;


public class Spot {

    //private Long id;
    private Address address;
    private String lat;
    private String lng;
    private String name;
    //private SpotAdmin spotAdmin;
    //private Code code;

    public Spot(Address address, String lat, String lng, String name) {
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Spot{" +
                "address=" + address +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
