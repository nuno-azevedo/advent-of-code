package com.adventofcode.year2021.day04;

import com.google.common.collect.Iterables;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

record Board(List<List<Integer>> board) {
    boolean isWinningBoard(List<Integer> drawnNumbers) {
        Set<Integer> drawnNumbersSet = new HashSet<>(drawnNumbers);

        for (int i = 0; i < this.board.size(); i++) {
            if (drawnNumbersSet.containsAll(getRow(i)) || drawnNumbersSet.containsAll(getColumn(i))) {
                return true;
            }
        }

        return false;
    }

    int calculateBoardScore(List<Integer> drawnNumbers) {
        int unmarkedNumbersSum = this.board.stream()
                .flatMap(Collection::stream)
                .filter(number -> !drawnNumbers.contains(number))
                .reduce(0, Integer::sum);

        return unmarkedNumbersSum * Iterables.getLast(drawnNumbers);
    }

    private List<Integer> getRow(int row) {
        return this.board.get(row);
    }

    private List<Integer> getColumn(int column) {
        return this.board.stream().mapToInt(row -> row.get(column)).boxed().toList();
    }
}
