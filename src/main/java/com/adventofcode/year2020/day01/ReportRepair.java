package com.adventofcode.year2020.day01;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.input.InputReader;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@DayPuzzle(year = 2020, day = 1)
public class ReportRepair extends AbstractPuzzle<List<Integer>> {
    private static final int VALUE = 2020;

    static {
        puzzle = ReportRepair.class;
    }

    @Override
    protected long partOne(List<Integer> input) {
        return findPairWithSumSorting(input).orElseThrow();
    }

    @Override
    protected long partTwo(List<Integer> input) {
        return findTrioWithSumSorting(input).orElseThrow();
    }

    @Override
    protected List<Integer> readInput(Path path) {
        return InputReader.readAsIntegerList(path);
    }

    @SuppressWarnings("unused")
    private static Optional<Integer> findPairWithSumNaive(List<Integer> numbers) {
        for (int i = 0; i < numbers.size() - 1; i++) {
            for (int j = i + 1; j < numbers.size(); j++) {
                if (numbers.get(i) + numbers.get(j) == VALUE) {
                    return Optional.of(numbers.get(i) * numbers.get(j));
                }
            }
        }

        return Optional.empty();
    }

    @SuppressWarnings("unused")
    private static Optional<Integer> findPairWithSumHashing(List<Integer> numbers) {
        Map<Integer, Integer> map = numbers.stream()
                .collect(Collectors.toMap(Function.identity(), k -> 1, Integer::sum));

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int number = entry.getKey();
            int complement = VALUE - number;

            if (number * 2 == VALUE && entry.getValue() >= 2) {
                return Optional.of(number * number);
            }
            if (map.containsKey(complement)) {
                return Optional.of(number * complement);
            }
        }

        return Optional.empty();
    }

    private static Optional<Integer> findPairWithSumSorting(List<Integer> numbers) {
        List<Integer> sorted = new ArrayList<>(numbers);
        Collections.sort(sorted);

        int i = 0;
        int j = numbers.size() - 1;

        while (i < j) {
            int number = sorted.get(i);
            int complement = sorted.get(j);

            if (number + complement < VALUE) {
                i++;
            } else if (number + complement > VALUE) {
                j--;
            } else {
                return Optional.of(number * complement);
            }
        }

        return Optional.empty();
    }

    @SuppressWarnings("unused")
    private static Optional<Integer> findTrioWithSumNaive(List<Integer> numbers) {
        for (int i = 0; i < numbers.size() - 2; i++) {
            for (int j = i + 1; j < numbers.size() - 1; j++) {
                for (int k = j + 1; k < numbers.size(); k++) {
                    if (numbers.get(i) + numbers.get(j) + numbers.get(k) == VALUE) {
                        return Optional.of(numbers.get(i) * numbers.get(j) * numbers.get(k));
                    }
                }
            }
        }

        return Optional.empty();
    }

    @SuppressWarnings("unused")
    private static Optional<Integer> findTrioWithSumHashing(List<Integer> numbers) {
        Map<Integer, Integer> map = numbers.stream()
                .collect(Collectors.toMap(Function.identity(), k -> 1, Integer::sum));

        for (Map.Entry<Integer, Integer> entryX : map.entrySet()) {
            int numberX = entryX.getKey();

            if (numberX * 3 == VALUE && entryX.getValue() >= 3) {
                return Optional.of(numberX * numberX * numberX);
            }

            for (Map.Entry<Integer, Integer> entryY : map.entrySet()) {
                int numberY = entryY.getKey();
                int numberZ = VALUE - numberX - numberY;

                if (numberX * 2 + numberY == VALUE && entryX.getValue() >= 2) {
                    return Optional.of(numberX * numberX * numberY);
                }
                if (numberX + numberY * 2 == VALUE && entryY.getValue() >= 2) {
                    return Optional.of(numberX * numberY * numberY);
                }
                if (map.containsKey(numberZ)) {
                    return Optional.of(numberX * numberY * numberZ);
                }
            }
        }

        return Optional.empty();
    }

    private static Optional<Integer> findTrioWithSumSorting(List<Integer> numbers) {
        List<Integer> sorted = new ArrayList<>(numbers);
        Collections.sort(sorted);

        for (int k = 0; k < sorted.size() - 2; ++k) {
            int numberZ = sorted.get(k);
            int i = k + 1;
            int j = numbers.size() - 1;

            while (i < j) {
                int numberX = sorted.get(i);
                int numberY = sorted.get(j);
                if (numberX + numberY + numberZ < VALUE) {
                    i++;
                } else if (numberX + numberY + numberZ > VALUE) {
                    j--;
                } else {
                    return Optional.of(numberX * numberY * numberZ);
                }
            }
        }

        return Optional.empty();
    }
}
