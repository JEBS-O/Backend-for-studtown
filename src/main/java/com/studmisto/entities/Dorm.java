package com.studmisto.entities;

public enum Dorm {
    DORM_3("вул. ХХХ1"),
    DORM_5("вул. ХХХ2"),
    DORM_6("вул. ХХХ3"),
    DORM_7("вул. ХХХ4");

    String address;

    Dorm(String address) {
        this.address = address;
    }
}
