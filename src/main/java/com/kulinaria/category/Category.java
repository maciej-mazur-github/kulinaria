package com.kulinaria.category;

import jakarta.persistence.*;
import com.kulinaria.recipe.Recipe;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String name;
    private String imageUrl;
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Recipe> recipes = new ArrayList<>();

    public Category() {
    }

    public Category(Long id, String name, String imageUrl, List<Recipe> recipes) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.recipes = recipes;
    }

    public Category(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.id = null;
        this.recipes = null;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
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


    public String getUrlName() {
        return CategoryEnum.getUrlNameByDbName(name);
    }
}
