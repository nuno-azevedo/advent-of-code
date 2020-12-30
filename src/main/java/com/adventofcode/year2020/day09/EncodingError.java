package com.adventofcode.year2020.day09;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.input.InputReader;

import java.nio.file.Path;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Optional;

@DayPuzzle(year = 2020, day = 9)
public class EncodingError extends AbstractPuzzle<List<Long>> {
    private static final int PREAMBLE = 25;

    static {
        puzzle = EncodingError.class;
    }

    @Override
    protected long partOne(List<Long> input) {
        return findNumberWithoutSumInPreamble(input).orElseThrow();
    }

    @Override
    protected long partTwo(List<Long> input) {
        return findContiguousNumbersWithSum(input, partOne(input)).orElseThrow();
    }

    @Override
    protected List<Long> readInput(Path path) {
        return InputReader.readAsLongList(path);
    }

    private static Optional<Long> findNumberWithoutSumInPreamble(List<Long> data) {
        SortedLinkedList<Long> preamble = new SortedLinkedList<>();

        for (int i = 0; i < data.size(); i++) {
            long number = data.get(i);

            if (i < PREAMBLE) {
                preamble.add(number);
            } else {
                if (!existsPairWithSum(preamble, number)) {
                    return Optional.of(number);
                }

                preamble.removeFirstOccurrence(data.get(i - PREAMBLE));
                preamble.add(number);
            }
        }

        return Optional.empty();
    }

    private static Optional<Long> findContiguousNumbersWithSum(List<Long> data, long target) {
        long sum = 0L;
        int start = 0;

        for (int i = 0; i < data.size(); i++) {
            sum += data.get(i);
            while (sum > target && start < i) {
                sum -= data.get(start++);
            }

            if (sum == target) {
                LongSummaryStatistics statistics = data.subList(start, i + 1).stream()
                        .mapToLong(n -> n)
                        .summaryStatistics();

                return Optional.of(statistics.getMin() + statistics.getMax());
            }
        }

        return Optional.empty();
    }

    private static boolean existsPairWithSum(SortedLinkedList<Long> numbers, long target) {
        int i = 0;
        int j = numbers.size() - 1;

        while (i < j) {
            long number = numbers.get(i);
            long complement = numbers.get(j);

            if (number + complement < target) {
                i++;
            } else if (number + complement > target) {
                j--;
            } else {
                return true;
            }
        }

        return false;
    }
}
