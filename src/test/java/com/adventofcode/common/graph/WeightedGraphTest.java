package com.adventofcode.common.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class WeightedGraphTest {
    private WeightedGraph<String> weightedGraph;

    @BeforeEach
    void beforeEach() {
        weightedGraph = new WeightedGraph<>(true);
    }

    @Test
    void testSimpleLinearPath() {
        weightedGraph.addEdge("A", "B", 2.0);
        weightedGraph.addEdge("B", "C", 5.0);
        weightedGraph.addEdge("C", "D", 1.0);

        assertEquals(2, weightedGraph.dijkstra("A", "B")); // A -> B = 2
        assertEquals(7, weightedGraph.dijkstra("A", "C")); // A -> B -> C = 2 + 5
        assertEquals(8, weightedGraph.dijkstra("A", "D")); // A -> B -> C -> D = 2 + 5 + 1
        assertEquals(6, weightedGraph.dijkstra("B", "D")); // B -> C -> D = 5 + 1
    }

    @Test
    void testCompetingPaths() {
        weightedGraph.addEdge("A", "B", 2.0);
        weightedGraph.addEdge("A", "C", 10.0);
        weightedGraph.addEdge("B", "D", 1.0);
        weightedGraph.addEdge("C", "D", 3.0);

        assertEquals(3, weightedGraph.dijkstra("A", "D")); // A -> B -> D = 2 + 1
        assertEquals(10, weightedGraph.dijkstra("A", "C")); // A -> C = 10
        assertEquals(2, weightedGraph.dijkstra("A", "B")); // A -> B = 2
    }

    @Test
    void testGraphWithCycles() {
        weightedGraph.addEdge("A", "B", 4.0);
        weightedGraph.addEdge("B", "C", 3.0);
        weightedGraph.addEdge("C", "A", 2.0);
        weightedGraph.addEdge("B", "D", 10.0);

        assertEquals(14, weightedGraph.dijkstra("A", "D")); // A -> B -> D = 4 + 10
        assertEquals(7, weightedGraph.dijkstra("A", "C")); // A -> B -> C = 4 + 3
        assertEquals(0, weightedGraph.dijkstra("A", "A")); // A -> A = 0
    }

    @Test
    void testZeroWeightEdges() {
        weightedGraph.addEdge("A", "B", 0.0);
        weightedGraph.addEdge("B", "C", 0.0);
        weightedGraph.addEdge("C", "D", 5.0);

        assertEquals(0, weightedGraph.dijkstra("A", "B")); // A -> B = 0
        assertEquals(0, weightedGraph.dijkstra("A", "C")); // A -> B -> C = 0 + 0
        assertEquals(5, weightedGraph.dijkstra("A", "D")); // A -> B -> C -> D = 0 + 0 + 5
    }

    @Test
    void testDisconnectedGraph() {
        weightedGraph.addEdge("A", "B", 1.0);
        weightedGraph.addEdge("C", "D", 2.0);

        assertNull(weightedGraph.dijkstra("A", "D"));
        assertNull(weightedGraph.dijkstra("C", "B"));
    }
}
