package com.adventofcode.year2021.day04;

import com.google.common.collect.Iterables;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

record Board(List<List<Integer>> board) {
    boolean isWinningBoard(List<Integer> drawnNumbers) {
        Set<Integer> drawnNumbersSet = new HashSet<>(drawnNumbers);

        for (int i = 0; i < board.size(); i++) {
            if (drawnNumbersSet.containsAll(getRow(i)) || drawnNumbersSet.containsAll(getColumn(i))) {
                return true;
            }
        }

        return false;
    }

    int calculateBoardScore(List<Integer> drawnNumbers) {
        int unmarkedNumbersSum = board.stream()
                .flatMap(Collection::stream)
                .filter(Predicate.not(drawnNumbers::contains))
                .reduce(0, Integer::sum);

        return unmarkedNumbersSum * Iterables.getLast(drawnNumbers);
    }

    private List<Integer> getRow(int row) {
        return board.get(row);
    }

    private List<Integer> getColumn(int column) {
        return board.stream().map(row -> row.get(column)).toList();
    }
}
