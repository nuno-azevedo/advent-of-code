package com.adventofcode.year2021.day10;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.input.InputReader;
import com.google.common.primitives.Chars;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Stack;

@DayPuzzle(year = 2021, day = 10)
public class SyntaxScoring extends AbstractPuzzle<List<List<Character>>> {
    private static final int MULTIPLIER = 5;
    private static final Map<Character, Character> PAIRS = Map.of(
            '(', ')',
            '[', ']',
            '{', '}',
            '<', '>'
    );
    private static final Map<Character, Integer> POINTS = Map.of(
            ')', 3,
            ']', 57,
            '}', 1197,
            '>', 25137
    );
    private static final Map<Character, Integer> COMPLETIONS = Map.of(
            '(', 1,
            '[', 2,
            '{', 3,
            '<', 4
    );

    static {
        puzzle = SyntaxScoring.class;
    }

    @Override
    protected long partOne(List<List<Character>> input) {
        return calculateTotalSyntaxErrorScore(input);
    }

    @Override
    protected long partTwo(List<List<Character>> input) {
        return calculateCompletionStringsMiddleScore(input);
    }

    @Override
    protected List<List<Character>> readInput(Path path) {
        return InputReader.readAsLineList(path, line -> Chars.asList(line.toCharArray()));
    }

    private static int calculateTotalSyntaxErrorScore(List<List<Character>> chunks) {
        return chunks.stream().map(SyntaxScoring::processSyntaxError).mapToInt(SyntaxError::score).sum();
    }

    private static long calculateCompletionStringsMiddleScore(List<List<Character>> chunks) {
        List<Long> scores = chunks.stream()
                .map(SyntaxScoring::processSyntaxError)
                .filter(error -> error.score() == 0)
                .map(SyntaxError::missing)
                .map(SyntaxScoring::calculateCompletionStringScore)
                .sorted()
                .toList();

        return scores.get(scores.size() / 2);
    }

    private static SyntaxError processSyntaxError(List<Character> chunk) {
        Stack<Character> stack = new Stack<>();

        for (char character : chunk) {
            if (PAIRS.containsKey(character)) {
                stack.push(character);
            } else if (!stack.empty() && character != PAIRS.get(stack.pop())) {
                return new SyntaxError(POINTS.get(character), stack);
            }
        }

        return new SyntaxError(0, stack);
    }

    private static long calculateCompletionStringScore(Stack<Character> missing) {
        long score = 0L;
        while (!missing.empty()) {
            score = score * MULTIPLIER + COMPLETIONS.get(missing.pop());
        }
        return score;
    }
}
