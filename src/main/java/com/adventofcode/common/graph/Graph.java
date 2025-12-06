package com.adventofcode.common.graph;

import lombok.AllArgsConstructor;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.SequencedMap;
import java.util.SequencedSet;

@AllArgsConstructor
public class Graph<N> extends AbstractGraph<N> {
    private final boolean directed;
    private final SequencedMap<N, SequencedSet<N>> connections = new LinkedHashMap<>();

    @Override
    public SequencedSet<N> nodes() {
        return connections.sequencedKeySet();
    }

    @Override
    public SequencedSet<N> neighbors(N node) {
        return connections.get(node);
    }

    public void addNode(N node) {
        connections.putIfAbsent(node, new LinkedHashSet<>());
    }

    public void addEdge(N node, N target) {
        addNode(node);
        addNode(target);

        if (directed) {
            connections.get(node).add(target);
        } else {
            connections.get(node).add(target);
            connections.get(target).add(node);
        }
    }
}
