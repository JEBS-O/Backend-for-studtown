package com.studmisto.entities.enums;

public enum Dorm {
    DORM_3("Гуртожиток №3"),
    DORM_5("Гуртожиток №5"),
    DORM_6("Гуртожиток №6"),
    DORM_7("Гуртожиток №7");

    String address;

    Dorm(String address) {
        this.address = address;
    }

    public static Dorm getDorm(String dorm) throws IllegalArgumentException {
        switch (dorm) {
            case "Гуртожиток №3 (вул. ХХХ1)" :
                return DORM_3;
            case "Гуртожиток №5 (вул. ХХХ1)" :
                return DORM_5;
            case "Гуртожиток №6 (вул. Тичини 8)" :
                return DORM_6;
            case "Гуртожиток №7 (вул. ХХХ1)" :
                return DORM_7;
        }
        throw new IllegalArgumentException("Некоректна назва гуртожитку");
    }

    public String getAddress() {
        return address;
    }
}