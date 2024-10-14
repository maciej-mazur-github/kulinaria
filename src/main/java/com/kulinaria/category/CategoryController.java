package com.kulinaria.category;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.kulinaria.recipe.RecipeFilters;
import com.kulinaria.recipe.RecipeService;

import java.util.Optional;

@Controller
public class CategoryController {
    private final CategoryService categoryService;
    private final RecipeService recipeService;

    private final static String CATEGORY_ALL = "wszystkie";
    private final static String CATEGORY_ALL_PAGE_TITLE = "WSZYSTKIE PRZEPISY";


    public CategoryController(CategoryService categoryService, RecipeService recipeService) {
        this.categoryService = categoryService;
        this.recipeService = recipeService;
    }

    @GetMapping("/kategorie")
    String getCategories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "categories";
    }

    @GetMapping("/kategorie/{categoryName}")
    String home(Model model,
                RecipeFilters recipeFilters,
                Pageable pageable,
                RedirectAttributes redirectAttributes) {

        if (recipeFilters.areValidFilters()) {
            model.addAttribute("page",
                    recipeService.findAllForFiltersAndSort(recipeFilters, pageable.previousOrFirst()));
        } else {
            redirectAttributes.addFlashAttribute(
                    "flashAttribute", "Wystąpił błąd związany z filtrami wyszukiwania");
            return "redirect:/blad";
        }
        addCategoryAttribute(recipeFilters, model);
        model.addAttribute("pageable", pageable.previousOrFirst());
        model.addAttribute("filters", recipeFilters);
        return "recipes";
    }

    private void addCategoryAttribute(RecipeFilters recipeFilters, Model model) {
        Optional<Category> categoryOptional = categoryService.findByName(recipeFilters.getCategoryName());
        Category category = null;
        if (categoryOptional.isPresent()) {
            category = categoryOptional.get();
        } else if (recipeFilters.getCategoryName().toUpperCase().equals(CATEGORY_ALL.toUpperCase())) {
            category = new Category(CATEGORY_ALL_PAGE_TITLE, "/categories/category5.jpg");
        }
        model.addAttribute("category", category);
    }
}
