package com.kulinaria.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.kulinaria.recipe.Recipe;

import java.util.Optional;

interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Recipe> {
    Optional<Category> findByNameContainingIgnoreCase(String name);
}
