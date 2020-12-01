package com.adventofcode.common.graph;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.PriorityQueue;
import java.util.SequencedMap;
import java.util.SequencedSet;
import java.util.stream.Collectors;

public class WeightedGraph<N> extends AbstractGraph<N> {
    private final boolean directed;
    private final SequencedMap<N, SequencedSet<Edge<N>>> connections = new LinkedHashMap<>();

    public WeightedGraph(boolean directed) {
        this.directed = directed;
    }

    @Override
    public SequencedSet<N> nodes() {
        return connections.sequencedKeySet();
    }

    @Override
    public SequencedSet<N> neighbors(N node) {
        return connections.get(node).stream()
                .map(Edge::target)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public void addNode(N node) {
        connections.putIfAbsent(node, new LinkedHashSet<>());
    }

    public void addEdge(N node, N target, Double weight) {
        addNode(node);
        addNode(target);

        if (directed) {
            connections.get(node).add(new Edge<>(target, weight));
        } else {
            connections.get(node).add(new Edge<>(target, weight));
            connections.get(target).add(new Edge<>(node, weight));
        }
    }

    public Double dijkstra(N root, N target) {
        if (anyNodeMissing(root, target)) {
            return null;
        }

        var distance = new LinkedHashMap<N, Double>();
        nodes().forEach(node -> distance.put(node, Double.MAX_VALUE));
        distance.put(root, 0.0);

        var priorityQueue = new PriorityQueue<N>(Comparator.comparingDouble(distance::get));
        priorityQueue.add(root);

        var visited = new HashSet<N>();

        while (!priorityQueue.isEmpty()) {
            var node = priorityQueue.poll();
            var currentDistance = distance.get(node);

            if (!visited.add(node)) {
                continue;
            }

            if (node.equals(target)) {
                return currentDistance;
            }

            connections.get(node).forEach(edge -> {
                double candidate = currentDistance + edge.distance();

                if (candidate < distance.get(edge.target())) {
                    distance.put(edge.target(), candidate);
                    priorityQueue.add(edge.target());
                }
            });
        }

        return null;
    }
}
