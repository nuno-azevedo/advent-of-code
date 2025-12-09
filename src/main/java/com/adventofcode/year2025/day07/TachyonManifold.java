package com.adventofcode.year2025.day07;

import com.adventofcode.common.grid.Cell;
import com.adventofcode.common.grid.CharGrid;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SequencedMap;
import java.util.stream.IntStream;

public record TachyonManifold(CharGrid diagram) {
    private static final Character START = 'S';
    private static final Character SPLITTER = '^';

    long countBeamSplits(boolean quantum) {
        long beamSplitCounter = 0;
        int startColumn = findStartColumn();

        var beamQueue = new LinkedHashMap<Cell<Character>, Long>();
        beamQueue.put(new Cell<>(1, startColumn), 1L);

        while (!beamQueue.isEmpty()) {
            var current = beamQueue.firstEntry();

            if (quantum && current.getKey().row() + 1 == diagram.getRowsLength()) {
                break;
            } else {
                beamQueue.pollFirstEntry();
            }

            char c = diagram.get(current.getKey().row(), current.getKey().column());
            if (c == SPLITTER) {
                beamSplitCounter++;
                addNewCellToQueue(beamQueue, current, -1);
                addNewCellToQueue(beamQueue, current, +1);
            } else {
                addNewCellToQueue(beamQueue, current, 0);
            }
        }

        if (quantum) {
            return beamQueue.values().stream().reduce(0L, Long::sum);
        }
        return beamSplitCounter;
    }

    private void addNewCellToQueue(
            SequencedMap<Cell<Character>, Long> beamQueue,
            Map.Entry<Cell<Character>, Long> current,
            int delta
    ) {
        int row = current.getKey().row() + 1;
        int column = current.getKey().column() + delta;

        if (diagram.inBounds(row, column)) {
            beamQueue.merge(new Cell<>(row, column), current.getValue(), Long::sum);
        }
    }

    private int findStartColumn() {
        return IntStream.range(0, diagram.getColumnsLength())
                .filter(column -> diagram.get(0, column) == START)
                .findFirst()
                .orElseThrow();
    }
}
