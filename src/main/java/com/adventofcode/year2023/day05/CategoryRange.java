package com.adventofcode.year2023.day05;

import java.util.Optional;

record CategoryRange(long destinationStart, long sourceStart, long length) {
    Optional<Long> convert(long sourceCategory) {
        if (sourceCategory >= sourceStart && sourceCategory < sourceStart + length) {
            long offset = destinationStart - sourceStart;
            return Optional.of(sourceCategory + offset);
        }
        return Optional.empty();
    }
}
