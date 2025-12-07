package com.adventofcode.year2025.day05;

import com.adventofcode.common.domain.Range;

import java.util.Comparator;
import java.util.List;

public record IngredientDatabase(List<Range<Long>> freshIngredientsRanges, List<Long> availableIngredients) {
    long countAvailableAndFreshIngredients() {
        return availableIngredients.stream().filter(this::isIngredientFresh).count();
    }

    long countFreshIngredients() {
        freshIngredientsRanges.sort(Comparator.naturalOrder());

        long freshIngredientsCount = 0;
        long currentIngredient = 0;

        for (Range<Long> range : freshIngredientsRanges) {
            currentIngredient = Math.max(currentIngredient, range.min());
            if (currentIngredient <= range.max()) {
                freshIngredientsCount += range.max() + 1 - currentIngredient;
                currentIngredient = range.max() + 1;
            }
        }

        return freshIngredientsCount;
    }

    private boolean isIngredientFresh(long ingredient) {
        return freshIngredientsRanges.stream().anyMatch(range -> range.contains(ingredient));
    }
}
