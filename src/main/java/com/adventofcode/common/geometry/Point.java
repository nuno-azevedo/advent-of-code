package com.adventofcode.common.geometry;

public sealed interface Point<T extends Point<T>> permits Point2D, Point3D  {
    long manhattanDistance(T point);

    double euclideanDistance(T point);
}
