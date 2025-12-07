package com.adventofcode.year2025.day06;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.function.LongBinaryOperator;

public record MathWorksheet(List<Problem> problems) {
    long addAnswersOfProblems() {
        return problems.stream().mapToLong(Problem::solve).sum();
    }

    record Problem(List<Long> numbers, MathOperator operator) {
        long solve() {
            return numbers.stream().reduce(operator::applyAsLong).orElseThrow();
        }
    }

    @AllArgsConstructor
    enum MathOperator implements LongBinaryOperator {
        ADD('+', Math::addExact),
        MULTIPLY('*', Math::multiplyExact);

        private final char symbol;
        private final LongBinaryOperator operator;

        @Override
        public long applyAsLong(long left, long right) {
            return operator.applyAsLong(left, right);
        }

        static MathOperator fromSymbol(char symbol) {
            return Arrays.stream(MathOperator.values())
                    .filter(mathOperator ->  mathOperator.symbol == symbol)
                    .findFirst()
                    .orElseThrow();
        }
    }
}
