package com.adventofcode.year2020.day08;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.input.InputReader;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.regex.Pattern;

@DayPuzzle(year = 2020, day = 8)
public class HandheldHalting extends AbstractPuzzle<List<CodeInstruction>> {
    private static final Pattern PATTERN = Pattern.compile("^(acc|jmp|nop) ([+\\-][0-9]{1,3})$");

    static {
        puzzle = HandheldHalting.class;
    }

    @Override
    protected long partOne(List<CodeInstruction> input) {
        return processCode(input).accumulator();
    }

    @Override
    protected long partTwo(List<CodeInstruction> input) {
        return fixCodeLoop(input).orElseThrow().accumulator();
    }

    @Override
    protected List<CodeInstruction> readInput(Path path) {
        AtomicInteger order = new AtomicInteger();

        return InputReader.readAsLineList(path, PATTERN, matcher -> {
            Operation operation = Operation.valueOf(matcher.group(1).toUpperCase());
            int argument = Integer.parseInt(matcher.group(2));
            return new CodeInstruction(operation, argument, order.getAndIncrement());
        });
    }

    private static ReturnCode processCode(List<CodeInstruction> instructions) {
        Set<Integer> processed = new HashSet<>();
        LongAccumulator accumulator = new LongAccumulator(Long::sum, 0L);
        int index = 0;

        while (index < instructions.size()) {
            if (!processed.add(index)) {
                return new ReturnCode(accumulator.get(), false);
            }

            CodeInstruction instruction = instructions.get(index);
            switch (instruction.operation()) {
                case ACC -> {
                    accumulator.accumulate(instruction.argument());
                    index++;
                }
                case JMP -> index += instruction.argument();
                case NOP -> index++;
            }
        }

        return new ReturnCode(accumulator.get(), true);
    }

    private static Optional<ReturnCode> fixCodeLoop(List<CodeInstruction> instructions) {
        return instructions.stream()
                .filter(instruction -> instruction.operation() != Operation.ACC)
                .map(candidate -> {
                    List<CodeInstruction> instructionsCopy = new ArrayList<>(instructions);
                    Operation switchedOperation = candidate.operation() == Operation.JMP ? Operation.NOP : Operation.JMP;
                    instructionsCopy.set(
                            candidate.order(),
                            new CodeInstruction(switchedOperation, candidate.argument(), candidate.order())
                    );
                    return instructionsCopy;
                })
                .map(HandheldHalting::processCode)
                .filter(ReturnCode::ended)
                .findAny();
    }
}
