package com.studmisto.entities.enums;

public enum ContactCategory {
    GENERAL("Загальні"),
    DORM_3("Гуртожиток №3"),
    DORM_5("Гуртожиток №5"),
    DORM_6("Гуртожиток №6"),
    DORM_7("Гуртожиток №7");

    String name;

    ContactCategory(String name) {
        this.name = name;
    }

    public static ContactCategory getContactCategory(String contactCategoryName) {
        switch(contactCategoryName) {
            case "Загальні" :
                return GENERAL;
            case "Гуртожиток №3" :
                return DORM_3;
            case "Гуртожиток №5" :
                return DORM_5;
            case "Гуртожиток №6" :
                return DORM_6;
            case "Гуртожиток №7" :
                return DORM_7;
        }
        throw new IllegalArgumentException("Некоректна назва категорії для контакту");
    }

    public String getName() {
        return name();
    }
}
