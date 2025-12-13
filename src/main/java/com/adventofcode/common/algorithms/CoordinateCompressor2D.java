package com.adventofcode.common.algorithms;

import com.adventofcode.common.geometry.Point2D;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.function.ToIntFunction;

@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class CoordinateCompressor2D {
    private final List<Integer> compressedX;
    private final List<Integer> compressedY;

    public static CoordinateCompressor2D fromPoints(List<Point2D> points) {
        var compressedX = uniqueSorted(points, Point2D::x);
        var compressedY = uniqueSorted(points, Point2D::y);
        return new CoordinateCompressor2D(compressedX, compressedY);
    }

    public Point2D compress(Point2D point) {
        var cx = compressedX.indexOf(point.x());
        var cy = compressedY.indexOf(point.y());
        return new Point2D(cx, cy);
    }

    public Point2D decompress(Point2D compressedPoint) {
        var x = compressedX.get(compressedPoint.x());
        var y = compressedY.get(compressedPoint.y());
        return new Point2D(x, y);
    }

    public int sizeX() {
        return compressedX.size();
    }

    public int sizeY() {
        return compressedY.size();
    }

    private static List<Integer> uniqueSorted(List<Point2D> points, ToIntFunction<Point2D> selector) {
        return points.stream().mapToInt(selector).boxed().distinct().sorted().toList();
    }
}
