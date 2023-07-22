package com.adventofcode.common;

import com.adventofcode.common.domain.Element;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public final class Arrays2D {
    private Arrays2D() { }

    public static int[][] clone(int[][] array) {
        return Arrays.stream(array).map(int[]::clone).toArray(int[][]::new);
    }

    public static char[][] clone(char[][] array) {
        return Arrays.stream(array).map(char[]::clone).toArray(char[][]::new);
    }

    public static boolean checkBoundaries(int[][] array, int row, int column) {
        return row >= 0 && row < array.length && column >= 0 && column < array[row].length;
    }

    public static boolean checkBoundaries(char[][] array, int row, int column) {
        return row >= 0 && row < array.length && column >= 0 && column < array[row].length;
    }

    public static <T> boolean checkBoundaries(T[][] array, int row, int column) {
        return row >= 0 && row < array.length && column >= 0 && column < array[row].length;
    }

    public static List<Element<Integer>> getAdjacentPoints(int[][] array, int row, int column, int radius, boolean diagonals) {
        return getAdjacentPoints(convertToObject(array), row, column, radius, diagonals);
    }

    public static List<Element<Character>> getAdjacentPoints(char[][] array, int row, int column, int radius, boolean diagonals) {
        return getAdjacentPoints(convertToObject(array), row, column, radius, diagonals);
    }

    public static <T> List<Element<T>> getAdjacentPoints(T[][] array, int row, int column, int radius, boolean diagonals) {
        List<Element<T>> adjacent = new LinkedList<>();

        if (checkBoundaries(array, row, column)) {
            for (int r = Math.max(0, row - radius); r < Math.min(array.length, row + radius + 1); r++) {
                for (int c = Math.max(0, column - radius); c < Math.min(array[r].length, column + radius + 1); c++) {
                    if ((r != row || c != column) && (r == row || c == column || diagonals)) {
                        adjacent.add(new Element<>(r, c, array[r][c]));
                    }
                }
            }
        }

        return adjacent;
    }

    public static Integer[][] convertToObject(int[][] array) {
        return Arrays.stream(array).map(ArrayUtils::toObject).toArray(Integer[][]::new);
    }

    public static Character[][] convertToObject(char[][] array) {
        return Arrays.stream(array).map(ArrayUtils::toObject).toArray(Character[][]::new);
    }
}
