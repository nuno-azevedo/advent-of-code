package com.adventofcode.year2021.day12;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.graph.Graph;
import com.adventofcode.common.input.InputReader;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@DayPuzzle(year = 2021, day = 12)
public class PassagePathing extends AbstractPuzzle<Graph<Cave>> {
    private static final Pattern PATTERN = Pattern.compile("^(start|end|[a-zA-Z]{2})-(start|end|[a-zA-Z]{2})$");

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
        Map<Cave, Integer> visited = new HashMap<>();
        return countPathsDepthFirstSearch(caveMap, visited, START, canRepeat);
    }

    private static int countPathsDepthFirstSearch(
            Graph<Cave> caveMap,
            Map<Cave, Integer> visited,
            Cave current,
            boolean canRepeat
    ) {
        if (END.equals(current)) {
            return 1;
        }

        if (current.isSmall()) {
            visited.merge(current, 1, Integer::sum);
        }

        int paths = 0;

        for (Cave next : caveMap.neighbors(current)) {
            int visits = visited.getOrDefault(next, 0);

            if (canVisitCave(next, visits, canRepeat)) {
                boolean nextCanRepeat = canRepeat && visits == 0;
                paths += countPathsDepthFirstSearch(caveMap, visited, next, nextCanRepeat);
            }
        }

        if (current.isSmall()) {
            visited.merge(current, -1, Integer::sum);
        }

        return paths;
    }

    private static boolean canVisitCave(Cave cave, int visits, boolean canRepeat) {
        if (START.equals(cave)) {
            return false;
        }
        return cave.isBig() || visits == 0 || (visits == 1 && canRepeat);
    }
}
