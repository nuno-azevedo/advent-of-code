package com.adventofcode.year2021.day12;

import org.apache.commons.lang3.StringUtils;

record Cave(String name) {
    boolean isBig() {
        return StringUtils.isAllUpperCase(this.name);
    }

    boolean isSmall() {
        return StringUtils.isAllLowerCase(this.name);
    }
}
