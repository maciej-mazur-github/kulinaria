package com.kulinaria.utils;

import com.kulinaria.shared.MealType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestDataCreator {
    private static final String DB_RELATIVE_PATH = "/src/main/resources/db/";
    private static final String LINE_SEPARATOR = "****";
    private static final String RECIPE_RELATIVE_IMAGE_PATH = "/recipes/";
    private static final String CATEGORY_RELATIVE_IMAGE_PATH = "/categories/";
    private static final String RECIPE_ABSOLUTE_IMAGE_PATH = "/src/main/resources/static/images/recipes/";

    private final String categoriesInputFile = this.getClass().getResource( "/db/input_txt_files/categories.txt").getFile();
    private final String recipesInputFile = this.getClass().getResource( "/db/input_txt_files/recipes.txt").getFile();
    private final String ingredientAmountInputFile = this.getClass().getResource( "/db/input_txt_files/ingredients_and_amounts.txt").getFile();
    private final String votesInputFile = this.getClass().getResource( "/db/input_txt_files/votes.txt").getFile();

    private final String categoriesOutputFile = System.getProperty("user.dir") + DB_RELATIVE_PATH + "category.sql";
    private final String recipesOutputFile = System.getProperty("user.dir") + DB_RELATIVE_PATH + "recipe.sql";
    private final String ingredientsOutputFile = System.getProperty("user.dir") + DB_RELATIVE_PATH + "ingredient.sql";
    private final String amountsOutputFile = System.getProperty("user.dir") + DB_RELATIVE_PATH + "amount.sql";
    private final String votesOutputFile = System.getProperty("user.dir") + DB_RELATIVE_PATH + "vote.sql";

    public static void main(String[] args) {
        TestDataCreator testDataCreator = new TestDataCreator();
        testDataCreator.createTestDataSqlFiles();
    }
    private void createTestDataSqlFiles() {
        createCategorySqlFile();
        createRecipesSqlFile();
        createIngredientsAndAmountsSqlFile();
        createVotesSqlFile();
    }

    private void addVoteToQueryList(String nextLine, List<String> votesQueryList) {
        String[] split = nextLine.split(",");
        int recipeId = Integer.parseInt(split[0]);
        int result = Integer.parseInt(split[1]);
        votesQueryList.add(String.format("INSERT INTO vote (result, recipe_id) VALUES (%d, %d);",
                result, recipeId));
    }

    private void createVotesSqlFile() {
        try (
                var fileReader = new FileReader(votesInputFile);
                var reader = new BufferedReader(fileReader)) {
            List<String> votesQueryList = new ArrayList<>();
            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                if (nextLine.isEmpty()) {
                } else {
                    addVoteToQueryList(nextLine, votesQueryList);
                }
            }
            saveQueryListToFile(votesQueryList, votesOutputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createCategorySqlFile() {
        try (
                var fileReader = new FileReader(categoriesInputFile);
                var reader = new BufferedReader(fileReader)) {
            List<String> categoryQueryList = new ArrayList<>();
            String nextLine = null;
            int categoryId = 1;
            while ((nextLine = reader.readLine()) != null) {
                if (!nextLine.isEmpty()) {
                    String name = nextLine;
                    String imageUrl = String.format("%scategory%d.jpg", CATEGORY_RELATIVE_IMAGE_PATH, categoryId);
                    categoryQueryList.add(String.format("INSERT INTO category (name, image_url) VALUES ('%s', '%s');",
                            nextLine, imageUrl));
                    categoryId++;
                }
            }
            saveQueryListToFile(categoryQueryList, categoriesOutputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createIngredientsAndAmountsSqlFile() {
        try (
                var fileReader = new FileReader(ingredientAmountInputFile);
                var reader = new BufferedReader(fileReader)) {
            int recipeId = 0;
            int amountId = 1;
            List<String> ingredientQueryList = new ArrayList<>();
            List<String> amountQueryList = new ArrayList<>();
            String nextLine;
            int lineCounter = 0;
            while ((nextLine = reader.readLine()) != null) {
                if (nextLine.isEmpty()) {
                    continue;
                } else if (nextLine.equals(LINE_SEPARATOR)) {
                    recipeId++;
                    reader.readLine();
                    continue;
                } else if (lineCounter % 2 == 0) {
                    ingredientQueryList.add(String.format("INSERT INTO ingredient(name, recipe_id, amount_id) VALUES ('%s', %d, %d);",
                            nextLine.replaceAll("'", "''"), recipeId, amountId++));
                } else {
                    amountQueryList.add(String.format("INSERT INTO amount (amount_value) VALUES ('%s' );", nextLine.replaceAll("'", "\'")));
                }
                lineCounter++;
            }
            saveQueryListToFile(ingredientQueryList, ingredientsOutputFile);
            saveQueryListToFile(amountQueryList, amountsOutputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readRecipeDescription(BufferedReader reader) throws IOException {
        String description = "";
        String nextLine;
        while ((!LINE_SEPARATOR.equals(nextLine = reader.readLine())) && (nextLine != null)) {
            description += nextLine.replaceAll("'", "''");
            description += "\n";
        }
        return formatDescription(description);
    }

    private String formatDescription(String description) {
        Pattern pattern1 = Pattern.compile("\n([A-Ża-ż, ]+(\\([A-Ża-ż]+\\) ?)?(- VIDEO)? - Krok [1-9][0-9]?)");
        Matcher matcher1 = pattern1.matcher(description);
        if (matcher1.find()) {
            description = matcher1.replaceAll("\n");
        }
        Pattern pattern2 = Pattern.compile("(Krok [2-9][0-9]?)");
        Matcher matcher2 = pattern2.matcher(description);
        if (matcher2.find()) {
            description = matcher2.replaceAll("\n$1");
        }
        return description;
    }

    private void addRecipeSqlQuery(List<String> recipeQueryList, String title, int prepTime,
                                   String description, String imageUrl, int categoryId, String mealType) {
        recipeQueryList.add(String.format("INSERT INTO recipe (title, prep_time, description, image_url, category_id, meal_type) VALUES ('%s', %d, '%s', '%s', %d, '%s');",
                title, prepTime, description, imageUrl, categoryId, mealType));
    }

    private void saveQueryListToFile(List<String> recipeList, String outputFileName) throws IOException {
        try (
                var fileWriter = new FileWriter(outputFileName);
                var writer = new BufferedWriter(fileWriter);
        ) {
            for (String s : recipeList) {
                writer.append(s);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Nie udało się zapisać pliku " + outputFileName);
        }
    }

    private void renameRecipeImageFiles() {
        File dir = new File(System.getProperty("user.dir") + RECIPE_ABSOLUTE_IMAGE_PATH);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File file : directoryListing) {
                String fileNumber = file.getName().split("\\.")[0];
                if (fileNumber.contains("recipe")) {
                    continue;
                }
                file.renameTo(new File(file.getParent() + String.format("/recipe%s.jpg", fileNumber)));
            }
        }
    }

    private String readMealType(String s) {
        return switch (s) {
            case "m" -> MealType.MEAT.name();
            case "v" -> MealType.VEGETARIAN.name();
            default -> throw new IllegalArgumentException("Podano błędny typ posiłku");
        };
    }

    private void createRecipesSqlFile() {
        try (
                var fileReader = new FileReader(recipesInputFile);
                var reader = new BufferedReader(fileReader)) {
            List<String> recipeQueryList = new ArrayList<>();
            String nextLine = null;
            while ((nextLine = reader.readLine()) != null) {
                if (nextLine.isEmpty()) {
                    continue;
                } else {
                    String title = nextLine;
                    int recipeId = Integer.parseInt(reader.readLine());
                    int prepTime = Integer.parseInt(reader.readLine());
                    int categoryId = Integer.parseInt(reader.readLine());
                    String mealType = readMealType(reader.readLine());
                    String description = readRecipeDescription(reader);
                    String imageUrl = String.format("%srecipe%d.jpg", RECIPE_RELATIVE_IMAGE_PATH, recipeId);
                    addRecipeSqlQuery(recipeQueryList, title, prepTime, description, imageUrl, categoryId, mealType);
                }
            }
            saveQueryListToFile(recipeQueryList, recipesOutputFile);
            renameRecipeImageFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
