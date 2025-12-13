package com.adventofcode.common.geometry;

import java.util.List;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

public record Polygon2D(List<Segment2D> segments) {
    private static final int SEGMENT = 2;

    public Polygon2D(List<Segment2D> segments) {
        validatePolygon(segments);
        this.segments = List.copyOf(segments);
    }

    public static Polygon2D fromOpenLoopPoints(List<Point2D> points) {
        var closedPoints = Stream.concat(points.stream(), Stream.of(points.getFirst())).toList();
        return fromClosedLoopPoints(closedPoints);
    }

    public static Polygon2D fromClosedLoopPoints(List<Point2D> points) {
        return new Polygon2D(segments(points));
    }

    private static void validatePolygon(List<Segment2D> segments) {
        if (segments.isEmpty()) {
            throw new IllegalArgumentException("Polygon must have at least one segment.");
        }
        if (!segments.getFirst().start().equals(segments.getLast().end())) {
            throw new IllegalArgumentException("Polygon segments must form a closed loop.");
        }
    }

    private static List<Segment2D> segments(List<Point2D> points) {
        return points.stream()
                .gather(Gatherers.windowSliding(SEGMENT))
                .map(List::iterator)
                .map(window -> new Segment2D(window.next(), window.next()))
                .toList();
    }
}
