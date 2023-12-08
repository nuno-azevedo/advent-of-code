package com.adventofcode.common.domain;

public record Range<T extends Comparable<T>>(T min, T max) {
    public static <T extends Comparable<T>> Range<T> between(T start, T end) {
        return start.compareTo(end) <= 0 ? new Range<>(start, end) : new Range<>(end, start);
    }

    public boolean contains(T element) {
        return this.min.compareTo(element) <= 0 && this.max.compareTo(element) >= 0;
    }

    public boolean contains(Range<T> range) {
        return this.contains(range.min) && this.contains(range.max);
    }

    public boolean overlaps(Range<T> range) {
        return this.contains(range.min) || range.contains(this.min) || range.contains(this.max);
    }
}
