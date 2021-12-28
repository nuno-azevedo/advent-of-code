package com.adventofcode.year2021.day15;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.graph.Graph;
import com.adventofcode.common.grid.Cell;
import com.adventofcode.common.grid.IntGrid;
import com.adventofcode.common.input.InputReader;

import java.nio.file.Path;

@DayPuzzle(year = 2021, day = 15)
public class Chiton extends AbstractPuzzle<IntGrid> {
    static {
        puzzle = Chiton.class;
    }

    @Override
    protected long partOne(IntGrid input) {
        return 0L;
    }

    @Override
    protected long partTwo(IntGrid input) {
        return 0L;
    }

    @Override
    protected IntGrid readInput(Path path) {
        var grid = InputReader.readAsDigitGrid(path);
        return new IntGrid(grid);
    }

    private static Graph<Cell<Integer>> createGraphFromMatrix(IntGrid matrix) {
        Graph<Cell<Integer>> graph = new Graph<>(true);

        for (int r = 0; r < matrix.getRowsLength(); r++) {
            for (int c = 0; c < matrix.getColumnsLength(); c++) {

                var root = matrix.getCell(r, c);
                matrix.neighborsOrthogonal(root, 1).forEach(e -> graph.addEdge(root, e));
            }
        }

        return graph;
    }

    private static int calculateLowestRiskPathDijkstra(int[][] riskLevelMap) {
        boolean[][] settled = new boolean[riskLevelMap.length][riskLevelMap[0].length];
        boolean[][] unsettled = new boolean[riskLevelMap.length][riskLevelMap[0].length];
        return 0;
    }
}
