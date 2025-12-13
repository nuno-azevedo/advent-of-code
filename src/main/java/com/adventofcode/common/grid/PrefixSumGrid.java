package com.adventofcode.common.grid;

import com.adventofcode.common.geometry.Point2D;

public class PrefixSumGrid {
    private final int[][] prefixSumGrid;

    public static PrefixSumGrid fromIntGrid(int[][] grid) {
        return new PrefixSumGrid(grid);
    }

    private PrefixSumGrid(int[][] grid) {
        prefixSumGrid = new int[grid.length][grid[0].length];

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                prefixSumGrid[y][x] = left(y, x) + top(y, x) - topLeft(y, x) + grid[y][x];
            }
        }
    }

    public int get(Point2D p1, Point2D p2) {
        var x1 = Math.min(p1.x(), p2.x());
        var x2 = Math.max(p1.x(), p2.x());
        var y1 = Math.min(p1.y(), p2.y());
        var y2 = Math.max(p1.y(), p2.y());

        return prefixSumGrid[y2][x2] - left(y2, x1) - top(y1, x2) + topLeft(y1, x1);
    }

    private int left(int y, int x) {
        return x > 0 ? prefixSumGrid[y][x - 1] : 0;
    }

    private int top(int y, int x) {
        return y > 0 ? prefixSumGrid[y - 1][x] : 0;
    }

    private int topLeft(int y, int x) {
        return x > 0 && y > 0 ? prefixSumGrid[y - 1][x - 1] : 0;
    }
}
