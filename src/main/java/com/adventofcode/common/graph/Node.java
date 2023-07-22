package com.adventofcode.common.graph;

public record Node<T>(T node, int weight) implements Comparable<Node<T>> {
    public Node(T node) {
        this(node, 0);
    }

    @Override
    public int compareTo(Node o) {
        return Integer.compare(this.weight, o.weight);
    }
}
