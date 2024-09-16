package com.kulinaria.recipe;

import jakarta.persistence.criteria.Join;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.kulinaria.category.Category;
import com.kulinaria.category.CategoryEnum;
import com.kulinaria.category.CategoryService;
import com.kulinaria.ingredient.Ingredient;
import com.kulinaria.shared.MealType;
import com.kulinaria.vote.Vote;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.kulinaria.recipe.RecipeFilters.*;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final CategoryService categoryService;

    public RecipeService(RecipeRepository recipeRepository, CategoryService categoryService) {
        this.recipeRepository = recipeRepository;
        this.categoryService = categoryService;
    }

    public List<Recipe> findTop4() {
        return recipeRepository.findTop4Voted();
    }

    public Page<Recipe> findAllForFiltersAndSort(RecipeFilters recipeFilters, Pageable pageable) {
        PageRequest pageRequest = adaptPageable(pageable);
        Specification<Recipe> specification = Specification
                .where(StringUtils.isAllBlank(
                        recipeFilters.getCategoryName()) ? null : categoryNameLike(recipeFilters.getCategoryName()))
                .and(StringUtils.isAllBlank(
                        recipeFilters.getMealType()) ? null : mealTypeEquals(recipeFilters.getMealType()))
                .and(sortedByVotesResult(pageRequest));

        if (pageRequest.getSort().getOrderFor(SORT_BY_VOTES_DB_COLUMN) != null) {
            return recipeRepository.findAll(specification,
                    PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize(), Sort.unsorted()));
        } else {
            return recipeRepository.findAll(specification, pageRequest);
        }
    }

    private Specification<Recipe> sortedByVotesResult(PageRequest pageRequest) {
        Sort.Order order = pageRequest.getSort().getOrderFor(SORT_BY_VOTES_DB_COLUMN);
        if (order != null) {
            return (root, query, builder) -> {
                Join<Vote, Recipe> voteRecipeJoin = root.join("vote");
                if (order.isAscending()) {
                    query.orderBy(builder.asc(voteRecipeJoin.get(SORT_BY_VOTES_DB_COLUMN)));
                } else {
                    query.orderBy(builder.desc(voteRecipeJoin.get(SORT_BY_VOTES_DB_COLUMN)));
                }
                Specification<Recipe> newSpecification = Specification.where(null);
                return newSpecification.toPredicate(root, query, builder);
            };
        }
        return null;
    }

    private PageRequest adaptPageable(Pageable pageable) {
        Sort currentSort = pageable.getSort();
        Sort targetSort = createNewSort(currentSort);
        return PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                targetSort);
    }

    private Sort createNewSort(Sort currentSort) {
        if (!currentSort.isSorted()) {
            return currentSort;
        }

        for (String sortUrlParam : sortUrlParams) {
            Sort.Order targetOrder = currentSort.getOrderFor(sortUrlParam);
            if (targetOrder != null) {
                return Sort.by(new Sort.Order(targetOrder.getDirection(), convertUrlSortToDbSort(sortUrlParam)));
            }
        }
        throw new IllegalArgumentException("Wystąpił błąd związany z parametrem sortowania.");
    }

    private String convertUrlSortToDbSort(String urlSort) {
        return switch (urlSort) {
            case SORT_BY_TITLE_PARAM -> SORT_BY_TITLE_DB_COLUMN;
            case SORT_BY_VOTES_PARAM -> SORT_BY_VOTES_DB_COLUMN;
            case SORT_BY_TIME_PARAM -> SORT_BY_TIME_DB_COLUMN;
            default -> throw new IllegalArgumentException("Podano nieprawidłowy typ sortowania");
        };
    }

    private Specification<Recipe> mealTypeEquals(String mealType) {
        return (root, query, builder) -> builder.equal(root.get("mealType"), MealType.getMealTypeEnumNameFromUrlName(mealType));
    }

    private Specification<Recipe> categoryNameLike(String categoryName) {
        if (categoryName.equals("wszystkie")) {
            return null;
        }
        return (root, query, builder) -> {
            Join<Category, Recipe> category = root.join("category");
            return builder.like(builder.upper(category.get("name")), "%" + categoryName.toUpperCase() + "%");
        };
    }

    public Optional<Recipe> findById(long recipeId) {
         return recipeRepository.findById(recipeId);
    }

    public RecipeStepDescriptionPairs createStepDescriptionPairsList(String description) {
        Pattern pattern = Pattern.compile("(^Krok [1-9][0-9]?)|(.+)");
        Matcher matcher = pattern.matcher(description);
        List<String> steps = new ArrayList<>();
        List<String> descriptions = new ArrayList<>();

        int counter = 0;
        while (matcher.find()) {
            if (counter % 2 == 0) {
                steps.add(matcher.group(0));
            } else {
                descriptions.add(matcher.group(0));
            }
            counter++;
        }
        return joinStepsDescriptionsLists(steps, descriptions);
    }

    private static RecipeStepDescriptionPairs joinStepsDescriptionsLists(List<String> steps, List<String> descriptions) {
        List<RecipeStepDescriptionPair> pairs = new ArrayList<>();
        for (int i = 0; i < steps.size(); i++) {
            pairs.add(new RecipeStepDescriptionPair(steps.get(i), descriptions.get(i)));
        }
        return new RecipeStepDescriptionPairs(pairs);
    }

    @Transactional
    public Recipe updateTitleCategoryType(Recipe recipe, CategoryEnum categoryEnum) {
        Recipe recipeFromDb = recipeRepository.findById(recipe.getId()).orElseThrow(IllegalArgumentException::new);
        if (!recipeFromDb.getCategory().getName().equals(categoryEnum.getDbName())) {
            Category category = categoryService.findByName(categoryEnum.getDbName())
                    .orElseThrow(IllegalArgumentException::new);
            recipeFromDb.setCategory(category);
        }
        recipeFromDb.setTitle(recipe.getTitle());
        recipeFromDb.setPrepTime(recipe.getPrepTime());
        recipeFromDb.setMealType(recipe.getMealType());
        return recipeFromDb;
    }

    @Transactional
    public Recipe updateImage(Recipe recipe, MultipartFile newPhoto) throws IOException {
        String fileName = String.format("recipe%d.jpg", recipe.getId());
        String destFilePath = System.getProperty("user.dir") + "/images/recipes/" + fileName;
        newPhoto.transferTo(new File(destFilePath));
        Recipe recipeFromDb = recipeRepository.findById(recipe.getId()).orElseThrow(IllegalArgumentException::new);
        if (recipe.getImageUrl() == null) {
            recipeFromDb.setImageUrl("/recipes/" + fileName);
        }
        return recipeFromDb;
    }

    @Transactional
    public Optional<Recipe> updateIngredients(Recipe recipe, Boolean addIngredient, Integer deleteIngredientIndex) {
        if (addIngredient != null && addIngredient == true) {
            recipe.addEmptyIngredient();
        } else if (deleteIngredientIndex != null) {
            recipe.deleteIngredient(deleteIngredientIndex);
        }
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipe.getId());
        if (recipeOptional.isPresent()) {
            Recipe recipeFromDb = recipeOptional.get();
            List<Ingredient> ingredients = recipeFromDb.getIngredients();
            for (int i = ingredients.size() - 1; i >= 0 ; i--) {
                recipeFromDb.deleteIngredient(i);
            }
            recipe.getIngredients().forEach(i -> recipeFromDb.addIngredient(i));
        }
        return recipeOptional;
    }

    @Transactional
    public Optional<Recipe> updateRecipeSteps(Recipe recipe,
                                              Integer deleteStepIndex,
                                              RecipeStepDescriptionPairs recipeStepDescriptionPairs) {
        if (deleteStepIndex != null) {
            recipeStepDescriptionPairs.deletePair(deleteStepIndex);
        }
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipe.getId());
        if (recipeOptional.isPresent()) {
            Recipe recipeFromDb = recipeOptional.get();
            recipeFromDb.setDescription(recipeStepDescriptionPairs.createResultString());
        }
        return recipeOptional;
    }

    public boolean deleteRecipe(Recipe recipe) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipe.getId());
        if (recipeOptional.isPresent()) {
            recipeRepository.delete(recipeOptional.get());
            return true;
        }
        return false;
    }

    public Recipe createNewRecipe(String recipeTitle, CategoryEnum categoryFromForm) {
        Recipe recipe = new Recipe();
        recipe.setTitle(recipeTitle);
        Category category = categoryService.findByName(categoryFromForm.getDbName()).orElseThrow(IllegalArgumentException::new);
        recipe.setCategory(category);
        Vote vote = new Vote();
        vote.setRecipe(recipe);
        recipe.setVote(vote);
        return recipeRepository.save(recipe);
    }
}

