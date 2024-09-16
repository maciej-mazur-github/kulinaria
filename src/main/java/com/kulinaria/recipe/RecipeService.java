package com.kulinaria.recipe;

import com.kulinaria.preparation.PreparationStepService;
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
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static com.kulinaria.recipe.RecipeFilters.*;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final CategoryService categoryService;
    private final PreparationStepService preparationStepService;

    public RecipeService(RecipeRepository recipeRepository, CategoryService categoryService, PreparationStepService preparationStepService) {
        this.recipeRepository = recipeRepository;
        this.categoryService = categoryService;
        this.preparationStepService = preparationStepService;
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
        String randomSuffix = "?v." + Instant.now().getEpochSecond();
        String destFilePath = System.getProperty("user.dir") + "/images/recipes/" + fileName;
        newPhoto.transferTo(new File(destFilePath));
        Recipe recipeFromDb = recipeRepository.findById(recipe.getId()).orElseThrow(IllegalArgumentException::new);
        if (recipe.getImageUrl() == null) {
            recipeFromDb.setImageUrl("/recipes/" + fileName + randomSuffix);
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

    public Optional<Recipe> updatePreparationSteps(Recipe recipe,
                                                   Long deleteStepId) {

        Optional<Recipe> recipeOptional = overwritePreparationStepsInDb(recipe);

        if (deleteStepId != null) {
            preparationStepService.deleteStep(deleteStepId);
        }

        return recipeOptional;
    }

    private Optional<Recipe> overwritePreparationStepsInDb(Recipe recipe) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipe.getId());
        if (recipeOptional.isPresent()) {
            AtomicInteger counter = new AtomicInteger(0);

            Recipe recipeFromDb = recipeOptional.get();
            recipeFromDb.getPreparationSteps().stream()
                    .forEach(step -> {
                        String newStepValue = recipe.getPreparationSteps()
                                .get(counter.getAndIncrement())
                                .getPreparationStep();
                        step.setPreparationStep(newStepValue);
                    });
            recipeRepository.save(recipeFromDb);
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

    public void saveInDb(Recipe recipeFromDb) {
        recipeRepository.save(recipeFromDb);
    }
}

