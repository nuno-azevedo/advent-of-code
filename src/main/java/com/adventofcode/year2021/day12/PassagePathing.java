package com.adventofcode.year2021.day12;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.graph.Graph;
import com.adventofcode.common.input.InputReader;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Pattern;

@DayPuzzle(year = 2021, day = 12)
public class PassagePathing extends AbstractPuzzle<Graph<Cave>> {
    private static final Pattern PATTERN = Pattern.compile("^(start|end|[a-zA-Z]{1,2})-(start|end|[a-zA-Z]{1,2})$");

    private static final Cave START = new Cave("start");
    private static final Cave END = new Cave("end");

    static {
        puzzle = PassagePathing.class;
    }

    @Override
    protected long partOne(Graph<Cave> input) {
        return findNumberOfDistinctPaths(input, false);
    }

    @Override
    protected long partTwo(Graph<Cave> input) {
        return findNumberOfDistinctPaths(input, true);
    }

    @Override
    protected Graph<Cave> readInput(Path path) {
        Graph<Cave> caveMap = new Graph<>(false);

        InputReader.readAsLineList(path, PATTERN, matcher -> {
            Cave caveA = new Cave(matcher.group(1));
            Cave caveB = new Cave(matcher.group(2));
            caveMap.addEdge(caveA, caveB);
            return null;
        });

        return caveMap;
    }

    private static int findNumberOfDistinctPaths(Graph<Cave> caveMap, boolean canRepeat) {
        Queue<List<Cave>> queue = new LinkedList<>();
        queue.add(List.of(START));

        int pathCount = 0;

        while (!queue.isEmpty()) {
            List<Cave> path = queue.poll();
            Cave cave = Iterables.getLast(path);

            if (END.equals(cave)) {
                pathCount++;
            } else {
                caveMap.neighbors(cave).stream()
                        .filter(c -> c.isBig() || !path.contains(c) || allowSmallCaveTwice(canRepeat, path, c))
                        .map(c -> ImmutableList.<Cave>builder().addAll(path).add(c).build())
                        .forEach(queue::add);
            }
        }

        return pathCount;
    }

    private static boolean allowSmallCaveTwice(boolean canRepeat, List<Cave> path, Cave cave) {
        return canRepeat && !START.equals(cave) &&
                path.stream().filter(Cave::isSmall).allMatch(c -> Collections.frequency(path, c) == 1);
    }
}
