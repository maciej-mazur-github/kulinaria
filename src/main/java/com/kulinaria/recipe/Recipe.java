package com.kulinaria.recipe;

import jakarta.persistence.*;
import com.kulinaria.amount.Amount;
import com.kulinaria.category.Category;
import com.kulinaria.ingredient.Ingredient;
import com.kulinaria.shared.MealType;
import com.kulinaria.vote.Vote;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String title;
    @Column(nullable = false)
    private Integer prepTime = 0;
    @Column(length = 3000, nullable = false)
    private String description = "";
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Enumerated(EnumType.STRING)
    private MealType mealType;
    @OneToOne(mappedBy = "recipe", cascade = CascadeType.ALL)
    private Vote vote;
    @OneToMany(mappedBy = "recipe",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Ingredient> ingredients = new ArrayList<>();

    public Recipe() {
    }

    public Recipe(Long id, String title, Integer prepTime, String description, String imageUrl,
                  Category category, MealType mealType, Vote vote, List<Ingredient> ingredients) {
        this.id = id;
        this.title = title;
        this.prepTime = prepTime;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
        this.mealType = mealType;
        this.vote = vote;
        this.ingredients = ingredients;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(Integer prepTime) {
        this.prepTime = prepTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void deleteIngredient(int deleteIngredientIndex) {
        ingredients.remove(deleteIngredientIndex);
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
        ingredient.setRecipe(this);
        ingredient.getAmount().setIngredient(ingredient);
    }

    public void addEmptyIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setAmount(new Amount());
        ingredient.setRecipe(this);
        ingredients.add(ingredient);
    }
}
