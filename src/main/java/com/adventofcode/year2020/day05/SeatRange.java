package com.adventofcode.year2020.day05;

record SeatRange(int lowerRow, int upperRow, int lowerColumn, int upperColumn) {
    boolean hasIntersected() {
        return lowerRow == upperRow && lowerColumn == upperColumn;
    }

    SeatRange withLowerRow(int newLowerRow) {
        return new SeatRange(newLowerRow, upperRow, lowerColumn, upperColumn);
    }

    SeatRange withUpperRow(int newUpperRow) {
        return new SeatRange(lowerRow, newUpperRow, lowerColumn, upperColumn);
    }

    SeatRange withLowerColumn(int newLowerColumn) {
        return new SeatRange(lowerRow, upperRow, newLowerColumn, upperColumn);
    }

    SeatRange withUpperColumn(int newUpperColumn) {
        return new SeatRange(lowerRow, upperRow, lowerColumn, newUpperColumn);
    }
}
