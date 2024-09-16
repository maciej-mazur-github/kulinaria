package com.kulinaria.vote;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kulinaria.recipe.Recipe;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByRecipe(Recipe recipe);
}
