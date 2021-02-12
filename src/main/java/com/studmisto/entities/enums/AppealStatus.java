package com.studmisto.entities.enums;

public enum AppealStatus {
    CREATED("Створено"),
    IN_PROCESS("У процесі"),
    CLOSED("Закрито");

    private String name;

    AppealStatus(String name) {
        this.name = name;
    }

    public static AppealStatus getAppealStatus(String name) throws IllegalArgumentException {
        switch(name) {
            case "Створено" :
                return CREATED;
            case "У процесі" :
                return IN_PROCESS;
            case "Закрито" :
                return CLOSED;
        }
        throw new IllegalArgumentException("Некоретна назва статусу для звернення");
    }

    public String getName() {
        return name;
    }
}
