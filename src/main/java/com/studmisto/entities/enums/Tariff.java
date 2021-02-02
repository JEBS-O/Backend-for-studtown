package com.studmisto.entities.enums;

public enum Tariff {
    STANDART("Стандарт"),
    SPECIAL("Пільговий (півціни)"),
    ZERO("Нуль");

    String description;

    Tariff(String description) {
        this.description = description;
    }

    public static Tariff getTariff(String tariff) throws IllegalArgumentException {
        switch(tariff) {
            case "Стандарт" :
                return STANDART;
            case "Пільговий (півціни)" :
                return SPECIAL;
            case "Нуль" :
                return ZERO;
        }
        throw new IllegalArgumentException("Некоректна назва тарифу");
    }
}