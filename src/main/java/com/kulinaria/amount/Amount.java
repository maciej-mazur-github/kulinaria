package com.kulinaria.amount;

import jakarta.persistence.*;
import com.kulinaria.ingredient.Ingredient;

@Entity
public class Amount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String amountValue = "";
    @OneToOne(mappedBy = "amount")
    private Ingredient ingredient;

    public Amount() {
    }

    public Amount(String amountValue) {
        this.amountValue = amountValue;
    }

    public Amount(Long id, String amountValue, Ingredient ingredient) {
        this.id = id;
        this.amountValue = amountValue;
        this.ingredient = ingredient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAmountValue() {
        return amountValue;
    }

    public void setAmountValue(String amountValue) {
        this.amountValue = amountValue;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
}
