package com.adventofcode.year2021.day12;

import org.apache.commons.lang3.StringUtils;

public record Cave(String name) {
    boolean isBig() {
        return StringUtils.isAllUpperCase(name);
    }

    boolean isSmall() {
        return StringUtils.isAllLowerCase(name);
    }
}
