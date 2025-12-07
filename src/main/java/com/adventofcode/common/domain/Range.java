package com.adventofcode.common.domain;

import lombok.NonNull;

import java.util.Comparator;

public record Range<T extends Comparable<T>>(T min, T max) implements Comparable<Range<T>> {
    public static <T extends Comparable<T>> Range<T> between(T start, T end) {
        return start.compareTo(end) <= 0 ? new Range<>(start, end) : new Range<>(end, start);
    }

    public boolean contains(T element) {
        return min.compareTo(element) <= 0 && max.compareTo(element) >= 0;
    }

    public boolean contains(Range<T> range) {
        return contains(range.min) && contains(range.max);
    }

    public boolean overlaps(Range<T> range) {
        return contains(range.min) || range.contains(min) || range.contains(max);
    }

    public int compareTo(@NonNull Range<T> o) {
        var comparator = Comparator.<Range<T>, T>comparing(Range::min).thenComparing(Range::max);
        return comparator.compare(this, o);
    }
}
