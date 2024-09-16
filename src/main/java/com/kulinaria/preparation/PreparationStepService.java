package com.kulinaria.preparation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PreparationStepService {
    private final PreparationStepRepository preparationStepRepository;

    public PreparationStepService(PreparationStepRepository preparationStepRepository) {
        this.preparationStepRepository = preparationStepRepository;
    }

    @Transactional
    public void deleteStep(Long deleteStepId) {
        PreparationStep preparationStep = preparationStepRepository.findById(deleteStepId).orElseThrow();
        preparationStep.getRecipe().removePreparationStep(preparationStep);
        preparationStep.setRecipe(null);
        preparationStepRepository.deleteById(deleteStepId);
    }
}
