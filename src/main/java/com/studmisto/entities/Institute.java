package com.studmisto.entities;

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
}
