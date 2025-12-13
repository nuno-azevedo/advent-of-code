package com.adventofcode.common.geometry;

public record Rectangle(Point2D p1, Point2D p2) {
    public long width() {
        return Math.abs(p1.x() - p2.x()) + 1;
    }

    public long height() {
        return Math.abs(p1.y() - p2.y()) + 1;
    }

    public long perimeter() {
        return Math.multiplyExact(width() + height(), 2);
    }

    public long area() {
        return Math.multiplyExact(width(), height());
    }
}
