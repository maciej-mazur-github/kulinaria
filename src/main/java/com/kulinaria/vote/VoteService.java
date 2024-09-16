package com.kulinaria.vote;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kulinaria.recipe.Recipe;
import com.kulinaria.recipe.RecipeService;

import java.util.Optional;

@Service
public class VoteService {
    private static final String VOTE_UP_STRING = "up";
    private static final String VOTE_DOWN_STRING = "down";

    private final VoteRepository voteRepository;
    private final RecipeService recipeService;

    public VoteService(VoteRepository voteRepository, RecipeService recipeService) {
        this.voteRepository = voteRepository;
        this.recipeService = recipeService;
    }

    @Transactional
    public boolean addVote(String value, long recipeId) {
        Optional<Recipe> recipeOptional = recipeService.findById(recipeId);
        if (recipeOptional.isPresent()) {
            Optional<Vote> voteOptional = voteRepository.findByRecipe(recipeOptional.get());
            if (voteOptional.isPresent()) {
                applyVote(voteOptional.get(), value);
            }
        }
        return false;
    }

    private void applyVote(Vote vote, String value) {
        switch (value) {
            case VOTE_UP_STRING -> vote.upVote();
            case VOTE_DOWN_STRING -> vote.downVote();
        }
    }
}
