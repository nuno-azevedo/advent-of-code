package com.adventofcode.year2020.day16;

import com.adventofcode.common.domain.Range;

import javax.annotation.Nullable;
import java.util.function.Predicate;

record TicketRule(String field, Range<Integer> lowerRange, Range<Integer> upperRange) implements Predicate<Integer> {
    public boolean test(@Nullable Integer value) {
        return this.lowerRange.contains(value) || this.upperRange.contains(value);
    }
}
