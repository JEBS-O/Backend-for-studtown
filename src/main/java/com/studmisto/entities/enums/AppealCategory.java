package com.studmisto.entities.enums;

public enum AppealCategory {
    GENERAL("Загальні"),
    COMPLAINT("Скарга"),
    SUGGESTION("Пропозиція"),
    CHANGE_ROOM("Зміна кімнати для проживання"),
    CHANGE_TARIFF("Зміна тарифу оплати за гуртожиток"),
    PAYMENT_ISSUES("Проблеми та питання щодо оплати"),
    TEMPORARY_NON_RESIDENCE("Заява на тимчасове непроживання у гуртожитку"),
    OTHER("Інше");

    private String name;

    AppealCategory(String name) {
        this.name = name;
    }

    public static AppealCategory getAppealCategory(String name) throws IllegalArgumentException {
        switch(name) {
            case "Загальні" :
                return GENERAL;
            case "Скарга" :
                return COMPLAINT;
            case "Пропозиція" :
                return SUGGESTION;
            case "Зміна кімнати для проживання" :
                return CHANGE_ROOM;
            case "Зміна тарифу оплати за гуртожиток" :
                return CHANGE_TARIFF;
            case "Проблеми та питання щодо оплати" :
                return PAYMENT_ISSUES;
            case "Заява на тимчасове непроживання у гуртожитку" :
                return TEMPORARY_NON_RESIDENCE;
            case "Інше" :
                return OTHER;
        }
        throw new IllegalArgumentException("Некоретна назва категорії для звернення");
    }

    public String getName() {
        return name;
    }
}
