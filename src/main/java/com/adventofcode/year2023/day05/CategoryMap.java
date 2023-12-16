package com.adventofcode.year2023.day05;

import java.util.List;
import java.util.Optional;

record CategoryMap(String name, List<CategoryRange> categoryRanges) {
    long convert(long sourceCategory) {
        return categoryRanges.stream()
                .map(categoryRange -> categoryRange.convert(sourceCategory))
                .flatMap(Optional::stream)
                .findAny()
                .orElse(sourceCategory);
    }
}
