package com.studmisto.entities.enums;

public enum Institute {
    IIT("Інститут інформаційних технологій"),
    INGI("Інститут нафтогазової інженерії"),
    IPNT("Інститут природничих наук і туризму"),
    IEM("Інститут економіки і менеджменту"),
    IABE("Інститут архутектури, будівництва та енергетики"),
    IGPDU("Інститут гуманітарної підготовки та державного управління"),
    IIM("Інститут інженерної механіки");

    String name;

    Institute(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Institute getInstitute(String institute) throws IllegalArgumentException {
        switch(institute) {
            case "Інститут інформаційних технологій" :
                return IIT;
            case "Інститут нафтогазової інженерії" :
                return INGI;
            case "Інститут природничих наук і туризму" :
                return IPNT;
            case "Інститут економіки і менеджменту" :
                return IEM;
            case "Інститут архутектури, будівництва та енергетики" :
                return IABE;
            case "Інститут гуманітарної підготовки та державного управління" :
                return IGPDU;
            case "Інститут інженерної механіки" :
                return IIM;
        }
        throw new IllegalArgumentException("Некоректна назва інституту");
    }
}
