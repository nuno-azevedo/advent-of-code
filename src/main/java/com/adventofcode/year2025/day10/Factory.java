package com.adventofcode.year2025.day10;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.input.InputReader;
import com.adventofcode.common.input.TextSplitter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.BitSet;
import java.util.List;
import java.util.regex.Pattern;

@DayPuzzle(year = 2025, day = 10)
public class Factory extends AbstractPuzzle<List<MachineManual>> {
    private static final Pattern PATTERN = Pattern.compile(
            "\\[([.#]+)] " +
            "(\\(\\d(?:,\\d)*\\)(?: \\(\\d(?:,\\d)*\\))*) " +
            "\\{(\\d+(?:,\\d+)*)}"
    );

    private static final char ON = '#';

    static {
        puzzle = Factory.class;
    }

    @Override
    protected long partOne(List<MachineManual> input) {
        return input.stream()
                .mapToInt(MachineManual::fewestButtonsToCorrectlyConfigure)
                .sum();
    }

    @Override
    protected long partTwo(List<MachineManual> input) {
        var solver = new MachineCountersSolver();
        return input.stream()
                .mapToLong(solver::solve)
                .sum();
    }

    @Override
    @SuppressWarnings("SpellCheckingInspection")
    protected List<MachineManual> readInput(Path path) throws IOException {
        return InputReader.readAsLineList(path, PATTERN, matcher -> {
            var indicatorLightsDiagram = makeDiagramBitSet(matcher.group(1));

            var buttonWiringSchematics = TextSplitter.onPattern("\\(|\\)|\\) \\(", matcher.group(2))
                    .map(button -> TextSplitter.onComma(button).map(Integer::parseInt).toList())
                    .map(Factory::makeButtonBitSet)
                    .toList();

            var joltageRequirements = TextSplitter.onComma(matcher.group(3)).map(Integer::parseInt).toList();

            return new MachineManual(indicatorLightsDiagram, buttonWiringSchematics, joltageRequirements);
        });
    }

    private static BitSet makeDiagramBitSet(String diagram) {
        var bitSet = new BitSet();
        for (int i = 0; i < diagram.length(); i++) {
            if (diagram.charAt(i) == ON) {
                bitSet.set(i);
            }
        }
        return bitSet;
    }

    private static BitSet makeButtonBitSet(List<Integer> button) {
        var bitSet = new BitSet();
        button.forEach(bitSet::set);
        return bitSet;
    }
}
