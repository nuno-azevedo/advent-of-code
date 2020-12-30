package com.adventofcode.year2020.day07;

import java.util.Objects;

public record Node(String color, Integer quantity) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(color, node.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }
}
