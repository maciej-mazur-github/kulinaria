package com.kulinaria.recipe;

public class RecipeStepDescriptionPair {
    private String stepName;
    private String stepDescription;

    public RecipeStepDescriptionPair() {
        this.stepName = "";
        this.stepDescription = "";
    }

    public RecipeStepDescriptionPair(String stepName, String stepDescription) {
        this.stepName = stepName;
        this.stepDescription = stepDescription;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getStepDescription() {
        return stepDescription;
    }

    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription;
    }
}
