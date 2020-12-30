package com.adventofcode.year2020.day12;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.input.InputReader;

import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

@DayPuzzle(year = 2020, day = 12)
public class RainRisk extends AbstractPuzzle<List<NavInstruction>> {
    private static final Pattern PATTERN = Pattern.compile("^([NSEWLRF])([0-9]{1,3})$");

    static {
        puzzle = RainRisk.class;
    }

    @Override
    protected long partOne(List<NavInstruction> input) {
        return processNavInstructionsDirection(input);
    }

    @Override
    protected long partTwo(List<NavInstruction> input) {
        return processNavInstructionsWaypoint(input);
    }

    @Override
    protected List<NavInstruction> readInput(Path path) {
        return InputReader.readAsLineList(path, PATTERN, matcher -> {
            Direction direction = Direction.fromCode(matcher.group(1).charAt(0));
            int value = Integer.parseInt(matcher.group(2));
            return new NavInstruction(direction, value);
        });
    }

    private static int processNavInstructionsDirection(List<NavInstruction> instructions) {
        Point point = new Point(0, 0);
        Direction direction = Direction.EAST;

        for (NavInstruction instruction : instructions) {
            switch (instruction.direction()) {
                case FORWARD -> point = point.move(new NavInstruction(direction, instruction.value()));
                case LEFT, RIGHT -> direction = direction.rotate(instruction.value());
                case NORTH, SOUTH, EAST, WEST -> point = point.move(instruction);
            }
        }

        return point.getManhattanDistance();
    }

    private static int processNavInstructionsWaypoint(List<NavInstruction> instructions) {
        Point point = new Point(0, 0);
        Point waypoint = new Point(1, 10);

        for (NavInstruction instruction : instructions) {
            switch (instruction.direction()) {
                case FORWARD -> point = point.move(waypoint, instruction.value());
                case LEFT, RIGHT -> waypoint = waypoint.rotate(instruction.value());
                case NORTH, SOUTH, EAST, WEST -> waypoint = waypoint.move(instruction);
            }
        }

        return point.getManhattanDistance();
    }
}
