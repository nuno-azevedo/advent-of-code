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

    public boolean contains(Point2D point) {
        var vectorSegment = Vector2D.from(start, end);
        var vectorToPoint = Vector2D.from(start, point);

        if (vectorSegment.cross(vectorToPoint) != 0) {
            return false;
        }

        return point.x() >= minX()
               && point.x() <= maxX()
               && point.y() >= minY()
               && point.y() <= maxY();
    }

    public boolean intersectsRayFrom(Point2D point) {
        var low = start.y() <= end.y() ? start : end;
        var high = start.y() <= end.y() ? end : start;

        if (point.y() <= low.y() || point.y() > high.y()) {
            return false;
        }

        long verticalOffset = point.y() - low.y();
        long segmentHeight = high.y() - low.y();
        long segmentWidth = high.x() - low.x();

        long intersectX = low.x() + verticalOffset * segmentWidth / segmentHeight;

        return intersectX > point.x();
    }
}
