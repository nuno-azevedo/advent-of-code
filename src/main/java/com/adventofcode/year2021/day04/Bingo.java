package com.adventofcode.year2021.day04;

import java.util.List;

public record Bingo(List<Integer> drawnNumbersOrder, List<Board> boards) {
    List<Integer> getDrawnNumbers(int round) {
        return drawnNumbersOrder.subList(0, round);
    }
}
