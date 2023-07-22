package com.adventofcode.year2020.day05;

record SeatRange(int lowerRow, int upperRow, int lowerColumn, int upperColumn) {
    boolean hasIntersected() {
        return this.lowerRow == this.upperRow && this.lowerColumn == this.upperColumn;
    }

    SeatRange withLowerRow(int lowerRow) {
        return new SeatRange(lowerRow, this.upperRow, this.lowerColumn, this.upperColumn);
    }

    SeatRange withUpperRow(int upperRow) {
        return new SeatRange(this.lowerRow, upperRow, this.lowerColumn, this.upperColumn);
    }

    SeatRange withLowerColumn(int lowerColumn) {
        return new SeatRange(this.lowerRow, this.upperRow, lowerColumn, this.upperColumn);
    }

    SeatRange withUpperColumn(int upperColumn) {
        return new SeatRange(this.lowerRow, this.upperRow, this.lowerColumn, upperColumn);
    }
}
