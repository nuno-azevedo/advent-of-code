package com.adventofcode.year2023.day05;

import java.util.List;

public record Almanac(List<Long> seeds, List<CategoryMap> categoryMaps) {
    long convert(long seed) {
        long destinationCategory = seed;
        for (CategoryMap categoryMap : categoryMaps) {
            destinationCategory = categoryMap.convert(destinationCategory);
        }
        return destinationCategory;
    }
}
