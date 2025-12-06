package com.adventofcode.year2020.day14;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.input.InputReader;

import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@DayPuzzle(year = 2020, day = 14)
public class DockingData extends AbstractPuzzle<List<MemoryUpdate>> {
    private static final Pattern BITMASK = Pattern.compile("^mask = ([0-9X]{36})$");
    private static final Pattern MEMORY = Pattern.compile("^mem\\[([0-9]{1,11})] = ([0-9]{1,11})$");

    static {
        puzzle = DockingData.class;
    }

    @Override
    protected long partOne(List<MemoryUpdate> input) {
        return decodeProgramVersionOne(input);
    }

    @Override
    protected long partTwo(List<MemoryUpdate> input) {
        return decodeProgramVersionTwo(input);
    }

    @Override
    protected List<MemoryUpdate> readInput(Path path) {
        StringBuilder bitmask = new StringBuilder();
        List<MemoryUpdate> updates = new LinkedList<>();

        InputReader.readAsLineStream(path).forEach(line -> {
            Matcher matcherBitmask = BITMASK.matcher(line);
            Matcher matcherMemory = MEMORY.matcher(line);

            if (matcherBitmask.matches()) {
                bitmask.setLength(0);
                bitmask.append(matcherBitmask.group(1));
            } else if (matcherMemory.matches()) {
                long address = Long.parseLong(matcherMemory.group(1));
                long value = Long.parseLong(matcherMemory.group(2));
                updates.add(new MemoryUpdate(bitmask.toString(), address, value));
            } else {
                throw new IllegalArgumentException();
            }
        });

        return updates;
    }

    private static long decodeProgramVersionOne(List<MemoryUpdate> updates) {
        Map<Long, Long> memory = updates.stream()
                .map(MemoryUpdate::applyBitmaskToValue)
                .collect(Collectors.toMap(MemoryUpdate::address, MemoryUpdate::value, (_, v) -> v));

        return memory.values().stream().mapToLong(n -> n).sum();
    }

    private static long decodeProgramVersionTwo(List<MemoryUpdate> updates) {
        Map<Long, Long> memory = updates.stream()
                .map(MemoryUpdate::applyBitmaskToAddress)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(MemoryUpdate::address, MemoryUpdate::value, (_, v) -> v));

        return memory.values().stream().mapToLong(n -> n).sum();
    }
}
