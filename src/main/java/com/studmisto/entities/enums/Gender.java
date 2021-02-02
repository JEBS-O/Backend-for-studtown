package com.studmisto.entities.enums;

public enum Gender {
    MAN, WOMAN;

    public static Gender getGender(String gender) throws IllegalArgumentException {
        if(gender.equals("Чоловік")) return Gender.MAN;
        else if(gender.equals("Жінка")) return Gender.WOMAN;
        else throw new IllegalArgumentException("Некоректна стать");
    }

}
