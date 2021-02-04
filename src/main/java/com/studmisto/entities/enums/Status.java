package com.studmisto.entities.enums;

public enum Status {
    ADMINISTRATION("Адміністрація студмістечка"),
    STAFF_3("Адміністрація/Персонал гуртожитку №3"),
    STAFF_5("Адміністрація/Персонал гуртожитку №5"),
    STAFF_6("Адміністрація/Персонал гуртожитку №6"),
    STAFF_7("Адміністрація/Персонал гуртожитку №7"),
    WAITING_FOR_ORDER("Очікування ордеру"),
    INMATE("Мешканець");

    String name;

    Status(String name) {
        this.name = name;
    }

    public static Status getStatus(String status) throws IllegalArgumentException {
        switch(status) {
            case "Адміністрація студмістечка" :
                return ADMINISTRATION;
            case "Адміністрація/Персонал гуртожитку №3" :
                return STAFF_3;
            case "Адміністрація/Персонал гуртожитку №5" :
                return STAFF_5;
            case "Адміністрація/Персонал гуртожитку №6" :
                return STAFF_6;
            case "Адміністрація/Персонал гуртожитку №7" :
                return STAFF_7;
            case "Мешканець" :
                return INMATE;
            case "Очікування ордеру" :
                return WAITING_FOR_ORDER;
        }
        throw new IllegalArgumentException("Некоректний статус");
    }

    public String getName() {
        return name;
    }
}
