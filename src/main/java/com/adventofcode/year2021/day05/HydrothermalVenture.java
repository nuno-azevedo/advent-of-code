package com.adventofcode.year2021.day05;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.geometry.Point2D;
import com.adventofcode.common.input.InputReader;

import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

@DayPuzzle(year = 2021, day = 5)
public class HydrothermalVenture extends AbstractPuzzle<List<LineSegment>> {
    private static final Pattern PATTERN = Pattern.compile("^(\\d+),(\\d+) -> (\\d+),(\\d+)$");

    static {
        puzzle = HydrothermalVenture.class;
    }

    @Override
    protected long partOne(List<LineSegment> input) {
        return countPointsWithOverlap(input, false);
    }

    @Override
    protected long partTwo(List<LineSegment> input) {
        return countPointsWithOverlap(input, true);
    }

    @Override
    protected List<LineSegment> readInput(Path path) {
        return InputReader.readAsLineList(path, PATTERN, matcher -> {
            Point2D p1 = new Point2D(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
            Point2D p2 = new Point2D(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));
            return new LineSegment(p1, p2);
        });
    }

    private static long countPointsWithOverlap(List<LineSegment> lineSegments, boolean allowDiagonals) {
        int rows = lineSegments.stream()
                .mapToInt(line -> Math.max(line.p1().y(), line.p2().y()) + 1)
                .max()
                .orElseThrow();

        int columns = lineSegments.stream()
                .mapToInt(line -> Math.max(line.p1().x(), line.p2().x()) + 1)
                .max()
                .orElseThrow();

        Diagram diagram = new Diagram(rows, columns);
        lineSegments.stream().filter(line -> !line.isDiagonal() || allowDiagonals).forEach(diagram::applyLineSegment);
        return diagram.countPointsWithOverlap();
    }
}
