package com.kulinaria.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.kulinaria.category.CategoryEnum;
import com.kulinaria.recipe.Recipe;

import java.lang.reflect.Field;
import java.util.List;

public class LinkCreator {
    private static final String QUERY_PARAM_SORT = "sort";
    private static final String QUERY_PARAM_FILTER_MEAL_TYPE = "mealType";
    public static final String PAGE_SIZE = "3";

    public static String currentQueryParams = "?size=" + PAGE_SIZE;

    public static String createTop4HomeLink(String categoryName, String recipeTitle, long recipeId) {
        ServletUriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
        categoryName = CategoryEnum.getUrlNameByDbName(categoryName);
        String recipeTitleUrlTail = convertRecipeTitleToUrlTail(recipeTitle, recipeId);
        return urlBuilder.path(
                (String.format("/kategorie/%s/przepis/%s", categoryName, recipeTitleUrlTail)))
                .toUriString();
    }

    public static String createLinkToCategoryRecipes(String categoryName) {
        ServletUriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
        String[] split = categoryName.split(" ");
        if (split.length > 1) {
            categoryName = split[0];
        }
        return urlBuilder.path("/" + categoryName.toLowerCase())
                .replaceQueryParam("size", PAGE_SIZE)
                .buildAndExpand()
                .toUriString();
    }

    public static String createLinkToCategoryRecipes(String name, String value) {
        ServletUriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
        if (QUERY_PARAM_FILTER_MEAL_TYPE.equals(name) && StringUtils.isAllBlank(value)) {
            urlBuilder.replaceQueryParam(name);
        } else if (QUERY_PARAM_SORT.equals(name)) {
            adjustSortOrder(urlBuilder, value);
        } else {
            urlBuilder.replaceQueryParam(name, value);
        }
        return urlBuilder.toUriString();
    }

    public static String createRecipeLink(String recipeTitle, long recipeId) {
        ServletUriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
        String currentUrl = urlBuilder.toUriString();

        if (currentUrl.lastIndexOf("?") >= 0) {
            currentQueryParams = currentUrl.substring(currentUrl.lastIndexOf("?"));
        }

        urlBuilder.replaceQuery(null);
        String recipeTitleUrlTail = convertRecipeTitleToUrlTail(recipeTitle, recipeId);
        return urlBuilder.path("/przepis/" + recipeTitleUrlTail).toUriString();
    }

    public static String convertRecipeTitleToUrlTail(String recipeTitle, long recipeId) {
        String[] split = StringUtils.stripAccents(recipeTitle)
                .replaceAll(",", "")
                .replaceAll("[()]", "")
                .toLowerCase()
                .split(" ");
        return String.join("-", split).concat("-" + recipeId);
    }

    private static void adjustSortOrder(ServletUriComponentsBuilder urlBuilder, String targetSortBy) {
        LinkedMultiValueMap<String, String> queryParams = getQueryParams(urlBuilder);
        List<String> sortList = queryParams.get(QUERY_PARAM_SORT);
        if (sortList != null && !sortList.isEmpty()) {
            String currentSortBy = sortList.get(0);
            if (currentSortBy.equals(targetSortBy)) {
                urlBuilder.replaceQueryParam(QUERY_PARAM_SORT, currentSortBy + ",desc");
            } else {
                urlBuilder.replaceQueryParam(QUERY_PARAM_SORT, targetSortBy);
            }
        } else {
            urlBuilder.replaceQueryParam(QUERY_PARAM_SORT, targetSortBy);
        }
    }

    private static LinkedMultiValueMap<String, String> getQueryParams(ServletUriComponentsBuilder urlBuilder) {
        Field field = ReflectionUtils.findField(ServletUriComponentsBuilder.class, "queryParams");
        ReflectionUtils.makeAccessible(field);
        return (LinkedMultiValueMap<String, String>) ReflectionUtils.getField(field, urlBuilder);
    }

    public static String createEditLink() {
        ServletUriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
        urlBuilder.replaceQuery(null);
        String resultLink = urlBuilder.path("/edycja").toUriString();
        return resultLink;
    }

    public static String createEditLinkInRecipesList(Recipe recipe) {
        ServletUriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
        urlBuilder.replaceQuery(null);
        String recipeUrlName = convertRecipeTitleToUrlTail(recipe.getTitle(), recipe.getId());
        String pathSuffix = String.format("/przepis/%s/edycja", recipeUrlName);
        String resultLink = urlBuilder.path(pathSuffix).toUriString();
        return resultLink;
    }

    public static String createEditTitleTimeTypeLink() {
        ServletUriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
        urlBuilder.replaceQuery(null);
        return urlBuilder.path("/tytul-czas-typ").toUriString();
    }

    public static String createEditIngredientsLink() {
        ServletUriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
        urlBuilder.replaceQuery(null);
        return urlBuilder.path("/skladniki").toUriString();
    }

    public static String createEditRecipeStepsLink() {
        ServletUriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
        urlBuilder.replaceQuery(null);
        return urlBuilder.path("/sposob-przygotowania").toUriString();
    }

    public static String backFromEditTitleTimeTypeLink() {
        ServletUriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
        urlBuilder.replaceQuery(null);
        return urlBuilder.toUriString().replaceAll("/tytul-czas-typ", "");
    }

    public static String backFromEditIngredientsLink() {
        ServletUriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
        urlBuilder.replaceQuery(null);
        return urlBuilder.toUriString().replaceAll("/skladniki", "");
    }

    public static String backFromEditStepsLink() {
        ServletUriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
        urlBuilder.replaceQuery(null);
        return urlBuilder.toUriString().replaceAll("/sposob-przygotowania", "");
    }

    public static String createAfterEditSaveLink(Recipe recipe) {
        String categoryUrlName = recipe.getCategory().getUrlName();
        String recipeUrlName = convertRecipeTitleToUrlTail(recipe.getTitle(), recipe.getId());
        String link = String.format("redirect:/kategorie/%s/przepis/%s/edycja",
                categoryUrlName, recipeUrlName);
        return link;
    }

    public static String createDeleteLink(String recipeTitle, long recipeId) {
        ServletUriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
        urlBuilder.replaceQuery(null);
        String recipeTitleUrlTail = convertRecipeTitleToUrlTail(recipeTitle, recipeId);
        return urlBuilder.path("/przepis/" + recipeTitleUrlTail + "/edycja/usun").toUriString();
    }

    public static String createDeleteInRecipeDetailsLink(String recipeTitle, long recipeId) {
        ServletUriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
        urlBuilder.replaceQuery(null);
        return urlBuilder.path("/edycja/usun").toUriString();
    }

    public static String backToEditIngredientsLink(Recipe recipe) {
        String categoryUrlName = recipe.getCategory().getUrlName();
        String recipeUrlName = convertRecipeTitleToUrlTail(recipe.getTitle(), recipe.getId());
        return String.format("redirect:/kategorie/%s/przepis/%s/edycja/skladniki",
                categoryUrlName, recipeUrlName);
    }

    public static String backToEditStepsLink(Recipe recipe) {
        String categoryUrlName = recipe.getCategory().getUrlName();
        String recipeUrlName = convertRecipeTitleToUrlTail(recipe.getTitle(), recipe.getId());
        return String.format("redirect:/kategorie/%s/przepis/%s/edycja/sposob-przygotowania",
                categoryUrlName, recipeUrlName);
    }

    public static String createDeleteLinkInEditionMode() {
        ServletUriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
        urlBuilder.replaceQuery(null);
        String link = urlBuilder.path("/usun").toUriString();
        return link;
    }

    public static String backFromDeletionConfirmationLink() {
        ServletUriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
        urlBuilder.replaceQuery(null);
        return urlBuilder.toUriString().replaceAll("/usun", "");
    }

    public static String backToNormalRecipeDetailsViewLink() {
        ServletUriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
        urlBuilder.replaceQuery(null);
        return urlBuilder.toUriString().replaceAll("/edycja", "");
    }

    public static String backToNormalRecipeDetailsViewInDeletionConfirmationLink() {
        ServletUriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
        urlBuilder.replaceQuery(null);
        return urlBuilder.toUriString().replaceAll("/edycja/usun", "");
    }

    public static String backToRecipesListWithFiltersAndSorting() {
        ServletUriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
        urlBuilder.replaceQuery(null);
        String resultLink = urlBuilder.toUriString();
        resultLink = resultLink.substring(0, resultLink.indexOf("/przepis")).concat(currentQueryParams);
        return resultLink;
    }
}
