package com.kulinaria.recipe;

import com.kulinaria.category.CategoryEnum;
import com.kulinaria.shared.MealType;

import java.util.ArrayList;
import java.util.List;

public class RecipeFilters {
    private String categoryName;
    private String mealType;
    private static final List<String> validNames = new ArrayList<>();
    public static final List<String> sortDbColumns = new ArrayList<>();
    public static final List<String> sortUrlParams = new ArrayList<>();

    public static final String ALL_CATEGORIES = "wszystkie";

    public static final String SORT_BY_TITLE_PARAM = "tytul";
    public static final String SORT_BY_VOTES_PARAM = "glosy";
    public static final String SORT_BY_TIME_PARAM = "czas";

    public static final String SORT_BY_TITLE_DB_COLUMN = "title";
    public static final String SORT_BY_VOTES_DB_COLUMN = "result";
    public static final String SORT_BY_TIME_DB_COLUMN = "prepTime";

    public RecipeFilters() {
        this.validNames.addAll(CategoryEnum.getUrlNames());
        validNames.add(ALL_CATEGORIES);
        validNames.add(SORT_BY_TITLE_PARAM);
        validNames.add(SORT_BY_VOTES_PARAM);
        validNames.add(SORT_BY_TIME_PARAM);

        sortDbColumns.add(SORT_BY_TITLE_DB_COLUMN);
        sortDbColumns.add(SORT_BY_VOTES_DB_COLUMN);
        sortDbColumns.add(SORT_BY_TIME_DB_COLUMN);

        sortUrlParams.add(SORT_BY_TITLE_PARAM);
        sortUrlParams.add(SORT_BY_VOTES_PARAM);
        sortUrlParams.add(SORT_BY_TIME_PARAM);
    }

    public String getCategoryName() {
        if (categoryName.equals(ALL_CATEGORIES)) {
            return ALL_CATEGORIES;
        } else {
            return CategoryEnum.getDbNameByUrlName(categoryName);
        }
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public boolean areValidFilters() {
        return (categoryName == null || validNames.contains(categoryName))
                && (mealType == null || MealType.isValidType(mealType));
    }

}
