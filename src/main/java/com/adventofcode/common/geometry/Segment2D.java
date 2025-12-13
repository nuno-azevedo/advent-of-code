package com.adventofcode.common.geometry;

public record Segment2D(Point2D start, Point2D end) {
    public int minX() {
        return Math.min(start.x(), end.x());
    }

    public int maxX() {
        return Math.max(start.x(), end.x());
    }

    public int minY() {
        return Math.min(start.y(), end.y());
    }

    public int maxY() {
        return Math.max(start.y(), end.y());
    }

    public boolean contains(Point2D p) {
        var vectorSegment = Vector2D.from(start, end);
        var vectorToPoint = Vector2D.from(start, p);

        if (vectorSegment.cross(vectorToPoint) != 0) {
            return false;
        }

        return p.x() >= Math.min(start.x(), end.x())
               && p.x() <= Math.max(start.x(), end.x())
               && p.y() >= Math.min(start.y(), end.y())
               && p.y() <= Math.max(start.y(), end.y());
    }

    public boolean intersectsRayFrom(Point2D p) {
        var low = start.y() <= end.y() ? start : end;
        var high = start.y() <= end.y() ? end : start;

        if (p.y() <= low.y() || p.y() > high.y()) {
            return false;
        }

        long verticalOffset = p.y() - low.y();
        long segmentHeight = high.y() - low.y();
        long segmentWidth = high.x() - low.x();

        long intersectX = low.x() + verticalOffset * segmentWidth / segmentHeight;

        return intersectX > p.x();
    }
}
