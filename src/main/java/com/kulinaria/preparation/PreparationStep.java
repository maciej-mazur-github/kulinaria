package com.kulinaria.preparation;

import com.kulinaria.recipe.Recipe;
import jakarta.persistence.*;

@Entity
@Table(name = "preparation_step")
public class PreparationStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 3000, nullable = false)
    private String preparationStep = "";
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    public PreparationStep() {
    }

    public PreparationStep(Long id, String preparationStep, Recipe recipe) {
        this.id = id;
        this.preparationStep = preparationStep;
        this.recipe = recipe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPreparationStep() {
        return preparationStep;
    }

    public void setPreparationStep(String preparationStep) {
        this.preparationStep = preparationStep;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
