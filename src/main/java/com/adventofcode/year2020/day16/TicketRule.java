package com.adventofcode.year2020.day16;

import com.adventofcode.common.domain.Range;

import java.util.function.Predicate;

record TicketRule(String field, Range<Integer> lowerRange, Range<Integer> upperRange) implements Predicate<Integer> {
    public boolean test(Integer value) {
        return lowerRange.contains(value) || upperRange.contains(value);
    }
}
