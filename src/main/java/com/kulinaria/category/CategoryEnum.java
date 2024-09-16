package com.kulinaria.category;

import java.util.ArrayList;
import java.util.List;

public enum CategoryEnum {
    DINNER(1, "Kolacja", "kolacja"),
    SALADS(2, "Sałatki na grilla", "salatki"),
    PASTA(3, "Makarony", "makarony"),
    SANDWICHES(4, "Kanapki", "kanapki");

    private int dbIndex;
    private String dbName;
    private String urlName;

    CategoryEnum(int dbIndex, String dbName, String urlName) {
        this.dbIndex = dbIndex;
        this.dbName = dbName;
        this.urlName = urlName;
    }

    public String getDbName() {
        return dbName;
    }

    public String getUrlName() {
        return urlName;
    }

    public static String getUrlNameByDbName(String dbName) {
        for (CategoryEnum value : values()) {
            if (dbName.equals(value.dbName)) {
                return value.urlName;
            }
        }
        throw new IllegalArgumentException("Wpisano nieprawidłową nazwę kategorii");
    }

    public static List<String> getUrlNames() {
        List<String> result = new ArrayList<>();
        for (CategoryEnum value : values()) {
            result.add(value.urlName);
        }
        return result;
    }

    public static String getDbNameByUrlName(String urlName) {
        for (CategoryEnum value : values()) {
            if (urlName.equals(value.urlName)) {
                return value.dbName;
            }
        }
        throw new IllegalArgumentException("Wpisano nieprawidłową nazwę kategorii");
    }
}
