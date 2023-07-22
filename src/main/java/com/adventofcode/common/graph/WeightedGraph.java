package com.adventofcode.common.graph;

import com.google.common.collect.ImmutableList;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WeightedGraph<N extends Weighted> extends Graph<N> {
    public List<N> calculateShortestPath(N root) {
        Map<N, Integer> minimumDistances = new HashMap<>();
        Map<N, List<N>> shortestPaths = new HashMap<>();

        Set<N> settledNodes = new HashSet<>();
        Set<N> unsettledNodes = new HashSet<>();

        minimumDistances.put(root, 0);
        unsettledNodes.add(root);

        while (!unsettledNodes.isEmpty()) {
            N currentNode = getLowestDistanceNode(minimumDistances, unsettledNodes);
            unsettledNodes.remove(currentNode);

            for (N adjacentNode : getConnections(currentNode)) {
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, adjacentNode.getWeight(), currentNode, minimumDistances, shortestPaths);
                    unsettledNodes.add(adjacentNode);
                }
            }

            settledNodes.add(currentNode);
        }

        return Collections.emptyList();
    }

    private N getLowestDistanceNode(Map<N, Integer> minDistances, Set<N> unsettledNodes) {
        int lowestDistance = Integer.MAX_VALUE;
        N lowestDistanceNode = null;

        for (N node : unsettledNodes) {
            int nodeDistance = minDistances.get(node);
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }

        return lowestDistanceNode;
    }

    private void calculateMinimumDistance(N evaluationNode, int edgeWeight, N sourceNode, Map<N, Integer> minimumDistances, Map<N, List<N>> shortestPaths) {
        int sourceDistance = sourceNode.getWeight();

        if (sourceDistance + edgeWeight < evaluationNode.getWeight()) {
            minimumDistances.put(evaluationNode, sourceDistance + edgeWeight);
            shortestPaths.put(
                    evaluationNode,
                    ImmutableList.<N>builder().addAll(shortestPaths.get(sourceNode)).add(sourceNode).build()
            );
        }
    }
}
