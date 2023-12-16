package com.adventofcode.year2023.day03;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.grid.Cell;
import com.adventofcode.common.grid.CharGrid;
import com.adventofcode.common.input.InputReader;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@DayPuzzle(year = 2023, day = 3)
public class GearRatios extends AbstractPuzzle<CharGrid> {
    private static final char PERIOD = '.';
    private static final char GEAR = '*';

    static {
        puzzle = GearRatios.class;
    }

    @Override
    protected long partOne(CharGrid input) {
        return findPartNumbersOfEngine(input).stream().mapToInt(PartNumber::value).sum();
    }

    @Override
    protected long partTwo(CharGrid input) {
        return findGearRatiosOfEngine(input).stream().mapToInt(Gear::value).sum();
    }

    @Override
    protected CharGrid readInput(Path path) {
        var grid = InputReader.readAsCharGrid(path);
        return new CharGrid(grid);
    }

    private static List<PartNumber> findPartNumbersOfEngine(CharGrid engineSchematic) {
        List<PartNumber> partNumbers = new LinkedList<>();

        for (int r = 0; r < engineSchematic.getRowsLength(); r++) {
            List<Cell<Character>> partNumberBuilder = new LinkedList<>();

            for (int c = 0; c < engineSchematic.getColumnsLength(); c++) {
                if (Character.isDigit(engineSchematic.get(r, c))) {
                    partNumberBuilder.add(engineSchematic.getCell(r, c));
                } else {
                    verifyPartNumber(partNumberBuilder, engineSchematic).ifPresent(partNumbers::add);
                }
            }

            verifyPartNumber(partNumberBuilder, engineSchematic).ifPresent(partNumbers::add);
        }

        return partNumbers;
    }

    private static Optional<PartNumber> verifyPartNumber(
            List<Cell<Character>> partNumberBuilder,
            CharGrid engineSchematic
    ) {
        PartNumber partNumber = new PartNumber(List.copyOf(partNumberBuilder), engineSchematic);
        partNumberBuilder.clear();

        boolean hasSymbol = partNumber.border().stream()
                .map(Cell::value)
                .anyMatch(GearRatios::isSymbol);

        return Optional.of(partNumber).filter(p -> hasSymbol);
    }

    private static List<Gear> findGearRatiosOfEngine(CharGrid engineSchematic) {
        List<PartNumber> partNumbers = findPartNumbersOfEngine(engineSchematic);

        Set<Cell<Character>> gearCells = partNumbers.stream()
                .flatMap(partNumber -> partNumber.border().stream())
                .filter(cell -> cell.value() == GEAR)
                .collect(Collectors.toSet());

        return gearCells.stream()
                .map(gearCell -> verifyGear(gearCell, partNumbers))
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

    private static Optional<Gear> verifyGear(Cell<Character> gearCell, List<PartNumber> partNumbers) {
        List<PartNumber> gearPartNumbers = partNumbers.stream()
                .filter(partNumber -> partNumber.border().contains(gearCell))
                .toList();

        Gear gear = new Gear(gearPartNumbers.getFirst(), gearPartNumbers.getLast());

        return Optional.of(gear).filter(g -> gearPartNumbers.size() == 2);
    }

    private static boolean isSymbol(char character) {
        return character != PERIOD && !Character.isLetterOrDigit(character) && !Character.isWhitespace(character);
    }
}
