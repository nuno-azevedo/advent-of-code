package com.adventofcode.year2021.day05;

import com.adventofcode.common.geometry.Point2D;
import com.google.common.collect.Streams;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public record LineSegment(Point2D p1, Point2D p2) {
    boolean isDiagonal() {
        return p1.x() != p2.x() && p1.y() != p2.y();
    }

    @SuppressWarnings("UnstableApiUsage")
    List<Point2D> getCoveredPoints() {
        int limit = Math.max(Math.abs(p1.x() - p2.x()), Math.abs(p1.y() - p2.y())) + 1;

        Stream<Integer> xStream = generateRange(p1.x(), p2.x(), limit);
        Stream<Integer> yStream = generateRange(p1.y(), p2.y(), limit);

        return Streams.zip(xStream, yStream, Point2D::new).toList();
    }

    private static Stream<Integer> generateRange(int start, int end, int limit) {
        return IntStream.iterate(start, i -> {
            if (start < end) {
                return i + 1;
            } else {
                return start > end ? i - 1 : i;
            }
        }).boxed().limit(limit);
    }
}
