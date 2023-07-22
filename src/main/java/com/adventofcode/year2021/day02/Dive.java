package com.adventofcode.year2021.day02;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.input.InputReader;

import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

@DayPuzzle(year = 2021, day = 2)
public class Dive extends AbstractPuzzle<List<Command>> {
    private static final Pattern PATTERN = Pattern.compile("^(forward|up|down) (\\d+)$");

    static {
        puzzle = Dive.class;
    }

    @Override
    protected long partOne(List<Command> input) {
        return processCommands(input);
    }

    @Override
    protected long partTwo(List<Command> input) {
        return processCommandsWithAim(input);
    }

    @Override
    protected List<Command> readInput(Path path) {
        return InputReader.readAsLineList(path, PATTERN, matcher -> {
            Direction direction = Direction.valueOf(matcher.group(1).toUpperCase());
            int units = Integer.parseInt(matcher.group(2));
            return new Command(direction, units);
        });
    }

    private static int processCommands(List<Command> commands) {
        int horizontal = 0;
        int depth = 0;

        for (Command command : commands) {
            switch (command.direction()) {
                case FORWARD -> horizontal += command.units();
                case UP -> depth -= command.units();
                case DOWN -> depth += command.units();
            }
        }

        return horizontal * depth;
    }

    private static int processCommandsWithAim(List<Command> commands) {
        int horizontal = 0;
        int depth = 0;
        int aim = 0;

        for (Command command : commands) {
            switch (command.direction()) {
                case FORWARD -> {
                    horizontal += command.units();
                    depth += aim * command.units();
                }
                case UP -> aim -= command.units();
                case DOWN -> aim += command.units();
            }
        }

        return horizontal * depth;
    }
}
