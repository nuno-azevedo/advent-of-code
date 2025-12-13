package com.adventofcode.common.geometry;

public record Vector2D(long x, long y) {
    public static Vector2D from(Point2D p1, Point2D p2) {
        return new Vector2D(p2.x() - p1.x(), p2.y() - p1.y());
    }

    public long cross(Vector2D vector) {
        return x * vector.y - y * vector.x;
    }
}
