package com.kulinaria.recipe;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import com.kulinaria.category.Category;
import com.kulinaria.category.CategoryEnum;
import com.kulinaria.category.CategoryService;
import com.kulinaria.utils.LinkCreator;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class RecipeController {

    private final RecipeService recipeService;
    private final CategoryService categoryService;

    public RecipeController(RecipeService recipeService, CategoryService categoryService) {
        this.recipeService = recipeService;
        this.categoryService = categoryService;
    }

    @GetMapping("/kategorie/{category}/przepis/{recipe}")
    String getRecipe(@PathVariable String recipe,
                     Model model,
                     RedirectAttributes redirectAttributes) {
        long recipeId = extractRecipeIdFromRecipeUrl(recipe);
        Optional<Recipe> recipeOptional = recipeService.findById(recipeId);
        if (recipeOptional.isPresent()) {
            Recipe recipeObject = recipeOptional.get();
            model.addAttribute("recipe", recipeObject);
            return "recipe-details";
        } else {
            redirectAttributes.addFlashAttribute("flashAttribute", "Nie znaleziono przepisu dla końcówki linku \"" + recipe + "\"");
            return "redirect:/blad";
        }
    }

    @GetMapping("/blad")
    String showError(Model model,
                     @ModelAttribute("flashAttribute") Object flashAttribute) {
        model.addAttribute("errorMessage", flashAttribute);
        return "error-message";
    }

    @GetMapping("/przepis/dodaj")
    String addRecipe(Model model) {
        return "create-recipe";
    }

    @PostMapping("/przepis/dodaj")
    String addRecipePost(String recipeTitle,
                         CategoryEnum categoryFromForm) {
        Recipe newRecipe = recipeService.createNewRecipe(recipeTitle, categoryFromForm);
        return LinkCreator.createAfterEditSaveLink(newRecipe);
    }

    @PostMapping("/kategorie/{categoryName}/przepis/{recipe}/usun")
    RedirectView deleteRecipe(@PathVariable String categoryName,
                              @PathVariable String recipe,
                              HttpSession session) {
        long recipeId = extractRecipeIdFromRecipeUrl(recipe);
        Optional<Recipe> recipeOptional = recipeService.findById(recipeId);
        if (recipeOptional.isPresent()) {
            Recipe recipeFromDb = recipeOptional.get();
            session.setAttribute("deletedRecipeTitle", recipeFromDb.getTitle());
            recipeService.deleteRecipe(recipeFromDb);
            return new RedirectView("/usunieto-przepis");
        } else {
            return new RedirectView("/blad-usuwania-przepisu");
        }
    }

    @GetMapping("/kategorie/{categoryName}/przepis/{recipeUrlName}/edycja")
    String editRecipe(Model model,
                      @PathVariable String recipeUrlName,
                      RedirectAttributes redirectAttributes) {

        long recipeId = extractRecipeIdFromRecipeUrl(recipeUrlName);
        Optional<Recipe> recipeOptional = recipeService.findById(recipeId);
        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();
            model.addAttribute("recipe", recipe);
            return "recipe-details-edition-mode";
        } else {
            redirectAttributes.addFlashAttribute("flashAttribute",
                    "Nie odnaleziono przepisu o tytule \"" + recipeUrlName + "\"");
            return "redirect:/blad";
        }
    }

    @GetMapping("/kategorie/{categoryName}/przepis/{recipeUrlName}/edycja/tytul-czas-typ")
    String editRecipeTitleTimeType(Model model,
                                   @PathVariable String recipeUrlName,
                                   RedirectAttributes redirectAttributes) {
        long recipeId = extractRecipeIdFromRecipeUrl(recipeUrlName);
        Optional<Recipe> recipeOptional = recipeService.findById(recipeId);
        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();
            model.addAttribute("recipe", recipe);
            List<Category> categories = categoryService.findAll();
            model.addAttribute("categories", categories);
            return "edit-title-time-category-type";
        } else {
            redirectAttributes.addFlashAttribute("flashAttribute",
                    "Nie odnaleziono przepisu o tytule \"" + recipeUrlName + "\"");
            return "redirect:/blad";
        }
    }

    @GetMapping("/kategorie/{categoryName}/przepis/{recipeUrlName}/edycja/skladniki")
    String editRecipeIngredients(Model model,
                                 @PathVariable String recipeUrlName,
                                 RedirectAttributes redirectAttributes) {
        long recipeId = extractRecipeIdFromRecipeUrl(recipeUrlName);
        Optional<Recipe> recipeOptional = recipeService.findById(recipeId);
        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();
            model.addAttribute("recipe", recipe);
            return "edit-ingredients";
        } else {
            redirectAttributes.addFlashAttribute("flashAttribute",
                    "Nie odnaleziono przepisu o tytule \"" + recipeUrlName + "\"");
            return "redirect:/blad";
        }
    }

    @GetMapping("/kategorie/{categoryName}/przepis/{recipeUrlName}/edycja/sposob-przygotowania")
    String editRecipeSteps(Model model,
                           @PathVariable String recipeUrlName,
                           RedirectAttributes redirectAttributes) {

        long recipeId = extractRecipeIdFromRecipeUrl(recipeUrlName);
        Optional<Recipe> recipeOptional = recipeService.findById(recipeId);
        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();
            model.addAttribute("recipe", recipe);
            return "edit-steps";
        } else {
            redirectAttributes.addFlashAttribute("flashAttribute",
                    "Nie odnaleziono przepisu o tytule \"" + recipeUrlName + "\"");
            return "redirect:/blad";
        }
    }

    @GetMapping("/kategorie/{categoryName}/przepis/{recipeUrlName}/edycja/usun")
    String getDeletionConfirmation(@PathVariable String recipeUrlName,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {

        long recipeId = extractRecipeIdFromRecipeUrl(recipeUrlName);
        Optional<Recipe> recipeOptional = recipeService.findById(recipeId);
        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();
            model.addAttribute("recipe", recipe);
            return "delete-confirmation";
        } else {
            redirectAttributes.addFlashAttribute("flashAttribute",
                    "Nie odnaleziono przepisu o tytule \"" + recipeUrlName + "\"");
            return "redirect:/blad";
        }
    }

    @PostMapping("/usun-przepis-z-bazy")
    String deleteRecipeFromDb(Recipe recipe,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        String recipeTitle = recipe.getTitle();
        Category category = recipe.getCategory();
        if (recipeService.deleteRecipe(recipe)) {
            model.addAttribute("recipeTitle", recipeTitle);
            model.addAttribute("category", category);
            return "recipe-deletion-success";
        } else {
            redirectAttributes.addFlashAttribute("flashAttribute",
                    "Wystąpił błąd podczas usuwania przepisu o tytule \"" + recipeTitle + "\"");
            return "redirect:/blad";
        }
    }

    @PostMapping("/zapisz-sposob-przygotowania")
    String updateRecipeStepsInDb(Recipe recipe,
                                 Boolean addStep,
                                 Long deleteStepId,
                                 RedirectAttributes redirectAttributes) {

        Optional<Recipe> recipeFromDbOptional = recipeService.updatePreparationSteps(recipe, deleteStepId);

        if (recipeFromDbOptional.isPresent()) {
            Recipe recipeFromDb = recipeFromDbOptional.get();

            if (addStep != null) {
                recipeFromDb.addEmptyPreparationStep();
                recipeService.saveInDb(recipeFromDb);
            }
            if (addStep != null || deleteStepId != null) {
                return LinkCreator.backToEditStepsLink(recipeFromDb);  // redirect
            } else {
                return LinkCreator.createAfterEditSaveLink(recipeFromDbOptional.get());
            }
        } else {
            redirectAttributes.addFlashAttribute("flashAttribute",
                    "Wystąpił błąd przy zapisie zmian sposobu przygotowania przepisu");
            return "redirect:/blad";
        }
    }

    @PostMapping("/zapisz-skladniki")
    String updateRecipeIngredientsInDb(Recipe recipe,
                                       Boolean addIngredient,
                                       Integer deleteIngredientIndex,
                                       RedirectAttributes redirectAttributes) {
        Optional<Recipe> recipeFromDbOptional = recipeService.updateIngredients(recipe, addIngredient, deleteIngredientIndex);
        if (recipeFromDbOptional.isPresent()) {
            if (addIngredient != null || deleteIngredientIndex != null) {
                return LinkCreator.backToEditIngredientsLink(recipeFromDbOptional.get());  // redirect z powrotem do edycji sposobu postepowania
            } else {
                return LinkCreator.createAfterEditSaveLink(recipeFromDbOptional.get());
            }
        } else {
            redirectAttributes.addFlashAttribute("flashAttribute",
                    "Wystąpił błąd przy zapisie zmian składników przepisu");
            return "redirect:/blad";
        }
    }

    @PostMapping("/zapisz-tytul-kategorie-czas-typ")
    String updateTitleCategoryTypeInDb(Recipe recipe,
                                       CategoryEnum categoryEnum) {
        Recipe recipeFromDb = recipeService.updateTitleCategoryType(recipe, categoryEnum);
        return LinkCreator.createAfterEditSaveLink(recipeFromDb);
    }

    @PostMapping("/zapisz-zdjecie")
    String updateImageInDb(Recipe recipe,
                           @RequestParam("avatar") MultipartFile multipartFile) throws IOException {
        Recipe recipeFromDb = recipeService.updateImage(recipe, multipartFile);
        return LinkCreator.createAfterEditSaveLink(recipeFromDb);
    }

    private long extractRecipeIdFromRecipeUrl(String recipe) {
        String[] split = recipe.split("-");
        return Long.parseLong(split[split.length - 1]);
    }
}
