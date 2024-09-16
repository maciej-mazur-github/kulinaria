package com.kulinaria.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.kulinaria.category.CategoryService;
import com.kulinaria.recipe.RecipeService;

@Controller
public class HomeController {
    private final RecipeService recipeService;

    public HomeController(CategoryService categoryService, RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/")
    String home(Model model) {
        model.addAttribute("top4Recipes", recipeService.findTop4());
        return "home";
    }
}
