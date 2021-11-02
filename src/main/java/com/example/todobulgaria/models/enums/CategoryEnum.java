package com.example.todobulgaria.models.enums;

public enum CategoryEnum {

    MOUNTAIN("MOUNTAIN"),
    CITY("CITY"),
    SEASIDE("SEASIDE"),
    ECO_FRIENDLY("ECO-FRIENDLY");

    private String value;

    private CategoryEnum(String value){
        this.value = value;
    }

    public String toString()
    {
        return this.value;
    }
}
