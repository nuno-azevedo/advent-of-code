package com.adventofcode.year2021.day05;

import com.adventofcode.common.domain.Point;
import com.google.common.collect.Streams;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

record LineSegment(Point p1, Point p2) {
    boolean isDiagonal() {
        return this.p1.x() != this.p2.x() && this.p1.y() != this.p2.y();
    }

    List<Point> getCoveredPoints() {
        int limit = Math.max(Math.abs(this.p1.x() - this.p2.x()), Math.abs(this.p1.y() - this.p2.y())) + 1;

        Stream<Integer> xStream = generateRange(this.p1.x(), this.p2.x(), limit);
        Stream<Integer> yStream = generateRange(this.p1.y(), this.p2.y(), limit);

        return Streams.zip(xStream, yStream, Point::new).toList();
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
