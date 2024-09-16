package com.kulinaria.ingredient;

import jakarta.persistence.*;
import com.kulinaria.amount.Amount;
import com.kulinaria.recipe.Recipe;

@Entity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String name = "";
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "amount_id")
    private Amount amount;

    public Ingredient() {
    }

    public Ingredient(Long id, String name, Recipe recipe, Amount amount) {
        this.id = id;
        this.name = name;
        this.recipe = recipe;
        this.amount = amount;
    }

    public void setIngredient(String name, Amount amount) {
        this.name = name;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }
}
