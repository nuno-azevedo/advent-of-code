package com.adventofcode.common.geometry;

public record Point3D(int x, int y, int z) implements Point<Point3D> {
    public long manhattanDistance(Point3D point) {
        long dx = (long) x - point.x();
        long dy = (long) y - point.y();
        long dz = (long) z - point.z();
        return Math.abs(dx) + Math.abs(dy) + Math.abs(dz);
    }

    public double euclideanDistance(Point3D point) {
        long dx = (long) x - point.x();
        long dy = (long) y - point.y();
        long dz = (long) z - point.z();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
}
