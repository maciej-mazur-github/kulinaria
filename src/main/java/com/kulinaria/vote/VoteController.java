package com.kulinaria.vote;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.kulinaria.category.CategoryEnum;
import com.kulinaria.recipe.Recipe;
import com.kulinaria.recipe.RecipeService;
import com.kulinaria.utils.LinkCreator;

@Controller
public class VoteController {
    private final VoteService voteService;
    private final RecipeService recipeService;

    public VoteController(VoteService voteService, RecipeService recipeService) {
        this.voteService = voteService;
        this.recipeService = recipeService;
    }

    @PostMapping("/glosuj")
    String addVote(@RequestParam(name = "id") long recipeId,
                   @RequestParam String vote) {
        voteService.addVote(vote, recipeId);
        Recipe recipe = recipeService.findById(recipeId).orElseThrow(IllegalArgumentException::new);
        String recipeUrlTitle = LinkCreator.convertRecipeTitleToUrlTail(recipe.getTitle(), recipeId);
        String categoryName = CategoryEnum.getUrlNameByDbName(recipe.getCategory().getName());
        String redirectString = String.format("redirect:/kategorie/%s/przepis/%s", categoryName, recipeUrlTitle);
        return redirectString;
    }
}
