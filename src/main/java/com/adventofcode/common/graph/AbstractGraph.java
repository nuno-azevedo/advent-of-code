package com.adventofcode.common.graph;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.SequencedSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class AbstractGraph<N> {
    abstract SequencedSet<N> nodes();

    abstract SequencedSet<N> neighbors(N node);

    public boolean hasNode(N node) {
        return nodes().contains(node);
    }

    public boolean hasNeighbor(N node, N neighbor) {
        return hasNode(node) && hasNode(neighbor) && neighbors(node).contains(neighbor);
    }

    public SequencedSet<N> nodesView() {
        return Collections.unmodifiableSequencedSet(new LinkedHashSet<>(nodes()));
    }

    public SequencedSet<N> neighborsView(N node) {
        var neighbors = Objects.requireNonNullElse(neighbors(node), new LinkedHashSet<N>());
        return Collections.unmodifiableSequencedSet(new LinkedHashSet<>(neighbors));
    }

    public boolean breadthFirstSearch(N root, N target) {
        return search(root, target, Deque::pollFirst);
    }

    public boolean depthFirstSearch(N root, N target) {
        return search(root, target, Deque::pollLast);
    }

    public boolean depthFirstSearchRecursive(N root, N target) {
        if (anyNodeMissing(root, target)) {
            return false;
        }

        var visited = new HashSet<N>();
        visited.add(root);

        return searchRecursive(root, target, visited);
    }

    boolean anyNodeMissing(N root, N target) {
        return !hasNode(root) || !hasNode(target);
    }

    private boolean search(N root, N target, Function<Deque<N>, N> poll) {
        if (anyNodeMissing(root, target)) {
            return false;
        }

        var deque = new ArrayDeque<N>();
        deque.push(root);

        var visited = new HashSet<N>();
        visited.add(root);

        while (!deque.isEmpty()) {
            N node = poll.apply(deque);

            if (node.equals(target)) {
                return true;
            }

            neighborsNonVisited(node, visited).forEach(deque::addLast);
        }

        return false;
    }

    private boolean searchRecursive(N root, N target, Set<N> visited) {
        if (root.equals(target)) {
            return true;
        }

        return neighborsNonVisited(root, visited).anyMatch(node -> searchRecursive(node, target, visited));
    }

    private Stream<N> neighborsNonVisited(N node, Set<N> visited) {
        return neighbors(node).stream().filter(visited::add);
    }
}
