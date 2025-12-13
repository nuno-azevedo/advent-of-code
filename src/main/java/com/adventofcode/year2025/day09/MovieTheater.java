package com.adventofcode.year2025.day09;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.algorithms.CoordinateCompressor2D;
import com.adventofcode.common.geometry.Point2D;
import com.adventofcode.common.geometry.Polygon2D;
import com.adventofcode.common.geometry.Rectangle;
import com.adventofcode.common.geometry.Segment2D;
import com.adventofcode.common.grid.PrefixSumGrid;
import com.adventofcode.common.input.InputReader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

// TODO: Explore Pick’s Theorem and Shoelace Formula
@DayPuzzle(year = 2025, day = 9)
public class MovieTheater extends AbstractPuzzle<List<Point2D>> {
    private static final Pattern PATTERN = Pattern.compile("^(\\d+),(\\d+)$");

    private static final int FILL = 1;
    private static final int EMPTY = 0;

    static {
        puzzle = MovieTheater.class;
    }

    @Override
    protected long partOne(List<Point2D> input) {
        var rectangles = generateRectanglesFromPoints(input);
        return rectangles.stream()
                .mapToLong(Rectangle::area)
                .max()
                .orElseThrow();
    }

    @Override
    protected long partTwo(List<Point2D> input) {
        var polygon = Polygon2D.fromOpenLoopPoints(input);
        var compressedPoints = CoordinateCompressor2D.fromPoints(input);
        var grid = drawGrid(polygon, compressedPoints);
        var redGreenPrefixSumArray = PrefixSumGrid.fromIntArray(grid);

        var rectangles = generateRectanglesFromPoints(input);
        return rectangles.stream()
                .filter(rectangle -> validateRectangle(compressedPoints, redGreenPrefixSumArray, rectangle))
                .mapToLong(Rectangle::area)
                .max()
                .orElseThrow();
    }

    @Override
    protected List<Point2D> readInput(Path path) throws IOException {
        return InputReader.readAsLineList(path, PATTERN, matcher -> {
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            return new Point2D(x, y);
        });
    }

    private static List<Rectangle> generateRectanglesFromPoints(List<Point2D> points) {
        var rectangles = new ArrayList<Rectangle>();

        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                rectangles.add(new Rectangle(points.get(i), points.get(j)));
            }
        }

        return rectangles;
    }

    private static int[][] drawGrid(Polygon2D polygon, CoordinateCompressor2D compressedPoints) {
        var grid = new int[compressedPoints.sizeY()][compressedPoints.sizeX()];

        polygon.segments().forEach(segment -> {
            var cp1 = compressedPoints.compress(segment.start());
            var cp2 = compressedPoints.compress(segment.end());

            var compressedSegment = new Segment2D(cp1, cp2);
            drawSegment(grid, compressedSegment);
        });

        Arrays.stream(grid).forEach(MovieTheater::fillInsidePolygonRow);

        return grid;
    }

    private static void drawSegment(int[][] grid, Segment2D segment) {
        int minX = segment.minX();
        int maxX = segment.maxX();
        int minY = segment.minY();
        int maxY = segment.maxY();
        IntStream.rangeClosed(minX, maxX).forEach(x -> grid[minY][x] = FILL);
        IntStream.rangeClosed(minY, maxY).forEach(y -> grid[y][minX] = FILL);
    }

    private static void fillInsidePolygonRow(int[] row) {
        var indexes = IntStream.range(0, row.length).boxed().filter(x -> row[x] != EMPTY).toList();

        if (indexes.isEmpty()) {
            return;
        }

        int minX = indexes.getFirst();
        int maxX = indexes.getLast();
        IntStream.rangeClosed(minX, maxX).forEach(x -> row[x] = FILL);
    }

    private static boolean validateRectangle(
            CoordinateCompressor2D compressedPoints,
            PrefixSumGrid prefixSumArray,
            Rectangle rectangle) {
        var cp1 = compressedPoints.compress(rectangle.p1());
        var cp2 = compressedPoints.compress(rectangle.p2());

        var count = prefixSumArray.get(cp1, cp2);
        var area = new Rectangle(cp1, cp2).area();
        return count == area;
    }
}
