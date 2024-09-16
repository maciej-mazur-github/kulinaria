package com.kulinaria.recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeStepDescriptionPairs {
    public List<RecipeStepDescriptionPair> pairs = new ArrayList<>();

    public RecipeStepDescriptionPairs(List<RecipeStepDescriptionPair> pairs) {
        this.pairs = pairs;
    }

    public List<RecipeStepDescriptionPair> getPairs() {
        return pairs;
    }

    public void setPairs(List<RecipeStepDescriptionPair> pairs) {
        this.pairs = pairs;
    }

    public String createResultString() {
        StringBuilder builder = new StringBuilder();
        for (RecipeStepDescriptionPair pair : pairs) {
            builder.append(pair.getStepName() + "\n" + pair.getStepDescription() + "\n\n\n\n");
        }
        return builder.toString();
    }

    public void addEmptyPair() {
        pairs.add(new RecipeStepDescriptionPair());
    }

    public void deletePair(int deleteStepIndex) {
        pairs.remove(deleteStepIndex);
    }
}
