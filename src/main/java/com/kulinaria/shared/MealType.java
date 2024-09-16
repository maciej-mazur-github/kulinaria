package com.kulinaria.shared;

public enum MealType {
    VEGETARIAN("Wegetariańskie", "wegetarianskie"),
    MEAT("Mięsne", "miesne");

    private String translation;
    private String urlTranslation;

    MealType(String translation, String urlTranslation) {
        this.translation = translation;
        this.urlTranslation = urlTranslation;
    }

    public String getTranslation() {
        return translation;
    }

    public String getUrlTranslation() {
        return urlTranslation;
    }

    public static boolean isValidType(String typeName) {
        for (MealType value : values()) {
            if (value.name().toUpperCase().equals(typeName.toUpperCase())
                    || typeName.equals(MEAT.urlTranslation)
                    || typeName.equals(VEGETARIAN.urlTranslation)) {
                return true;
            }
        }
        return false;
    }

    public static String getMealTypeEnumNameFromUrlName(String urlName) {
        for (MealType value : values()) {
            if (urlName.equals(value.urlTranslation)) {
                return value.name();
            }
        }
        throw new IllegalArgumentException("Podano nieprawidłowy typ posiłku");
    }
}
