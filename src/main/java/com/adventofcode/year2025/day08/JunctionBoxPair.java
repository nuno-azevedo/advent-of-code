package com.adventofcode.year2025.day08;

import com.adventofcode.common.geometry.Point3D;

public record JunctionBoxPair(Point3D p1, Point3D p2) implements Comparable<JunctionBoxPair> {
    double distance() {
        return p1.euclideanDistance(p2);
    }

    @Override
    public int compareTo(JunctionBoxPair o) {
        return Double.compare(distance(), o.distance());
    }
}
