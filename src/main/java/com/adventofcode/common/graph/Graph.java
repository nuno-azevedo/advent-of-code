package com.adventofcode.common.graph;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Sets;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

public class Graph<N> {
    private final Map<N, Set<N>> connections = new HashMap<>();

    public Set<N> getNodes() {
        return Set.copyOf(this.connections.keySet());
    }

    public Set<N> getConnections(N node) {
        return Set.copyOf(this.connections.get(node));
    }

    public void addNode(N node) {
        this.connections.putIfAbsent(node, new HashSet<>());
    }

    public void addConnection(N node, N target) {
        this.connections.get(node).add(target);
    }

    public void addNodeAndConnection(N node, N target) {
        this.connections.computeIfAbsent(node, k -> new HashSet<>()).add(target);
    }

    public boolean breadthFirstSearch(N root, N target) {
        return search(root, target, Deque::pollFirst);
    }

    public boolean depthFirstSearch(N root, N target) {
        return search(root, target, Deque::pollLast);
    }

    public boolean depthFirstSearchRecursive(N root, N target) {
        return depthFirstSearchRecursive(root, target, new HashSet<>());
    }

    private boolean search(N root, N target, Function<Deque<N>, N> remove) {
        Set<N> visited = new HashSet<>();
        Deque<N> deque = new LinkedList<>();
        deque.addLast(root);

        N node;
        while ((node = remove.apply(deque)) != null) {
            if (node.equals(target)) {
                return true;
            }

            if (visited.add(node)) {
                getNodeConnectionsNonVisited(node, visited).forEach(deque::addLast);
            }
        }

        return false;
    }

    private boolean depthFirstSearchRecursive(N root, N target, Set<N> visited) {
        visited.add(root);
        return getNodeConnectionsNonVisited(root, visited).stream().anyMatch(node -> {
            if (node.equals(target)) {
                return true;
            } else {
                return visited.add(node) && depthFirstSearchRecursive(node, target, visited);
            }
        });
    }

    private Set<N> getNodeConnectionsNonVisited(N node, Set<N> visits) {
        return Sets.difference(this.connections.get(node), visits);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Graph<?> graph = (Graph<?>) o;
        return Objects.equals(this.connections, graph.connections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.connections);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("nodes", this.connections.size())
                .add("edges", this.connections.values().stream().mapToInt(Set::size).sum())
                .add("connections", this.connections)
                .toString();
    }
}
