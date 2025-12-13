package com.adventofcode.common.geometry;

public record Point2D(int x, int y) implements Point<Point2D> {
    public long manhattanDistance(Point2D point) {
        long dx = (long) x - point.x();
        long dy = (long) y - point.y();
        return Math.abs(dx) + Math.abs(dy);
    }

    public double euclideanDistance(Point2D point) {
        long dx = (long) x - point.x();
        long dy = (long) y - point.y();
        return Math.sqrt(dx * dx + dy * dy);
    }
}
