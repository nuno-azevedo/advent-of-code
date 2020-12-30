package com.adventofcode.year2020.day04;

import java.util.function.Predicate;
import java.util.regex.Pattern;

record RegexConstraint(Pattern pattern) implements Predicate<String> {
    RegexConstraint(String pattern) {
        this(Pattern.compile(pattern));
    }

    public boolean test(String string) {
        return string != null && pattern.matcher(string).matches();
    }
}
