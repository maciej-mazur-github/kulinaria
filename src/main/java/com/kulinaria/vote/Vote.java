package com.kulinaria.vote;

import jakarta.persistence.*;
import com.kulinaria.recipe.Recipe;

@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer result = 0;
    @OneToOne
    @JoinColumn(name = "recipe_id", unique = true)
    private Recipe recipe;

    public Vote() {
    }

    public Vote(Integer result) {
        this.result = result;
    }

    public Vote(Long id, Integer result, Recipe recipe) {
        this.id = id;
        this.result = result;
        this.recipe = recipe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void upVote() {
        result++;
    }

    public void downVote() {
        result--;
    }
}
