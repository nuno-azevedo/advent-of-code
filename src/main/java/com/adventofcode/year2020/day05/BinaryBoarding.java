package com.adventofcode.year2020.day05;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.input.InputReader;

import java.nio.file.Path;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.OptionalInt;

@DayPuzzle(year = 2020, day = 5)
public class BinaryBoarding extends AbstractPuzzle<List<String>> {
    private static final int ROWS = 128;
    private static final int COLUMNS = 8;
    private static final char FRONT = 'F';
    private static final char BACK = 'B';
    private static final char LEFT = 'L';
    private static final char RIGHT = 'R';
    private static final char ZERO = '0';
    private static final char ONE = '1';
    private static final int RADIX = 2;

    static {
        puzzle = BinaryBoarding.class;
    }

    @Override
    protected long partOne(List<String> input) {
        return findHighestSeatID(input).orElseThrow();
    }

    @Override
    protected long partTwo(List<String> input) {
        return findMissingSeatID(input).orElseThrow();
    }

    @Override
    protected List<String> readInput(Path path) {
        return InputReader.readAsLineList(path);
    }

    private static OptionalInt findHighestSeatID(List<String> boardingPasses) {
        return boardingPasses.stream()
                .mapToInt(BinaryBoarding::computeSeatIDBinarySearch)
                .max();
    }

    private static OptionalInt findMissingSeatID(List<String> boardingPasses) {
        IntSummaryStatistics statistics = boardingPasses.stream()
                .mapToInt(BinaryBoarding::computeSeatIDBinaryConversion)
                .summaryStatistics();

        int minSeatID = statistics.getMin();
        int maxSeatID = statistics.getMax();
        int actualSum = (int) statistics.getSum();
        int expectedSum = (maxSeatID + minSeatID) * (maxSeatID - minSeatID + 1) / 2;

        return boardingPasses.isEmpty() ? OptionalInt.empty() : OptionalInt.of(expectedSum - actualSum);
    }

    private static int computeSeatIDBinarySearch(String boardingPass) {
        return computeSeatIDBinarySearch(boardingPass, new SeatRange(0, ROWS - 1, 0, COLUMNS - 1));
    }

    private static int computeSeatIDBinarySearch(String boardingPass, SeatRange seatRange) {
        if (seatRange.hasIntersected()) {
            return seatRange.lowerRow() * COLUMNS + seatRange.lowerColumn();
        } else {
            String remaining = boardingPass.substring(1);
            int mediumRow = (seatRange.lowerRow() + seatRange.upperRow()) / 2;
            int mediumColumn = (seatRange.lowerColumn() + seatRange.upperColumn()) / 2;

            return switch (boardingPass.charAt(0)) {
                case FRONT -> computeSeatIDBinarySearch(remaining, seatRange.withUpperRow(mediumRow));
                case BACK -> computeSeatIDBinarySearch(remaining, seatRange.withLowerRow(mediumRow + 1));
                case LEFT -> computeSeatIDBinarySearch(remaining, seatRange.withUpperColumn(mediumColumn));
                case RIGHT -> computeSeatIDBinarySearch(remaining, seatRange.withLowerColumn(mediumColumn + 1));
                default -> throw new IllegalArgumentException();
            };
        }
    }

    private static int computeSeatIDBinaryConversion(String boardingPass) {
        String rowBinary = boardingPass.substring(0, 7).replace(FRONT, ZERO).replace(BACK, ONE);
        String columnBinary = boardingPass.substring(7, 10).replace(LEFT, ZERO).replace(RIGHT, ONE);
        int rowDecimal = Integer.parseInt(rowBinary, RADIX);
        int columnDecimal = Integer.parseInt(columnBinary, RADIX);
        return rowDecimal * COLUMNS + columnDecimal;
    }
}
