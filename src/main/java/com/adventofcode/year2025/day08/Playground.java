package com.adventofcode.year2025.day08;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.geometry.Point3D;
import com.adventofcode.common.graph.Graph;
import com.adventofcode.common.input.InputReader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.regex.Pattern;

@DayPuzzle(year = 2025, day = 8)
public class Playground extends AbstractPuzzle<List<Point3D>> {
    private static final Pattern PATTERN = Pattern.compile("^(\\d+),(\\d+),(\\d+)$");

    private static final int CONNECTIONS = 1000;
    private static final int TOP = 3;

    static {
        puzzle = Playground.class;
    }

    @Override
    protected long partOne(List<Point3D> input) {
        var distances = generateJunctionBoxPairsFromPoints(input);
        var graph = new Graph<Point3D>(false);

        for (int i = 0; i < CONNECTIONS && !distances.isEmpty(); i++) {
            var closest = distances.poll();
            graph.addEdge(closest.p1(), closest.p2());
        }

        return graph.groupConnectedClusters().stream()
                .map(Set::size)
                .sorted(Comparator.reverseOrder())
                .limit(TOP)
                .reduce(Math::multiplyExact)
                .orElseThrow();
    }

    @Override
    protected long partTwo(List<Point3D> input) {
        var distances = generateJunctionBoxPairsFromPoints(input);
        var graph = new Graph<Point3D>(false);
        input.forEach(graph::addNode);

        int edgesUntilRecheck = graph.groupConnectedClusters().size();
        while (!distances.isEmpty()) {
            var closest = distances.poll();
            graph.addEdge(closest.p1(), closest.p2());
            edgesUntilRecheck--;

            if (edgesUntilRecheck == 1) {
                // If the graph currently has N disconnected clusters, we only need to re-check again after adding
                // (N-1) more edges, because that is the minimum required for the N clusters to be fully merged.
                edgesUntilRecheck = graph.groupConnectedClusters().size();
                if (edgesUntilRecheck == 1) {
                    return (long) closest.p1().x() * closest.p2().x();
                }
            }
        }

        throw new NoSuchElementException();
    }

    @Override
    protected List<Point3D> readInput(Path path) throws IOException {
        return InputReader.readAsLineList(path, PATTERN, matcher -> {
            var x = Integer.parseInt(matcher.group(1));
            var y = Integer.parseInt(matcher.group(2));
            var z = Integer.parseInt(matcher.group(3));
            return new Point3D(x, y, z);
        });
    }

    public PriorityQueue<JunctionBoxPair> generateJunctionBoxPairsFromPoints(List<Point3D> points) {
        var priorityQueue = new PriorityQueue<JunctionBoxPair>();

        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                priorityQueue.add(new JunctionBoxPair(points.get(i), points.get(j)));
            }
        }

        return priorityQueue;
    }
}
