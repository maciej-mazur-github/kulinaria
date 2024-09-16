package com.kulinaria.recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface RecipeRepository extends JpaRepository<Recipe, Long>, JpaSpecificationExecutor<Recipe> {
    @Query("SELECT r FROM Recipe r WHERE r.vote.result IN (SELECT v.result FROM Vote v ORDER BY v.result DESC LIMIT 4) ORDER BY r.vote.result DESC")
    List<Recipe> findTop4Voted();

    @Query("SELECT COUNT(*) FROM Recipe r")
    Long findAllRecipesCount();
}
