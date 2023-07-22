package com.adventofcode.year2020.day04;

import java.util.function.Predicate;

record NumberConstraint(int min, int max) implements Predicate<String> {
    public boolean test(String number) {
        return number != null && this.min <= Integer.parseInt(number) && Integer.parseInt(number) <= this.max;
    }
}
