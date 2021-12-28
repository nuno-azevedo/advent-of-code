package com.adventofcode.year2021.day07;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.input.TextSplitter;
import com.google.common.math.Quantiles;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.IntStream;

@DayPuzzle(year = 2021, day = 7)
public class TreacheryWhales extends AbstractPuzzle<List<Integer>> {
    static {
        puzzle = TreacheryWhales.class;
    }

    @Override
    protected long partOne(List<Integer> input) {
        return calculateLeastAmountOfFuelMedian(input);
    }

    @Override
    protected long partTwo(List<Integer> input) {
        return calculateLeastAmountOfFuelMean(input);
    }

    @Override
    protected List<Integer> readInput(Path path) throws IOException {
        String content = Files.readString(path, Charset.defaultCharset());
        return TextSplitter.onComma(content).map(Integer::parseInt).toList();
    }

    private static int calculateLeastAmountOfFuelMedian(List<Integer> crabs) {
        int median = (int) Quantiles.median().compute(crabs);
        return crabs.stream().mapToInt(crab -> distance(crab, median)).sum();
    }

    private static int calculateLeastAmountOfFuelMean(List<Integer> crabs) {
        int average = (int) crabs.stream().mapToInt(i -> i).summaryStatistics().getAverage();
        return crabs.stream().mapToInt(crab -> distanceSummation(crab, average)).sum();
    }

    @SuppressWarnings("unused")
    private static int calculateLeastAmountOfFuelNaive(List<Integer> crabs) {
        IntSummaryStatistics statistics = crabs.stream().mapToInt(i -> i).summaryStatistics();

        return IntStream.rangeClosed(statistics.getMin(), statistics.getMax())
                .map(position -> crabs.stream()
                        .mapToInt(crab -> distanceSummation(crab, position))
                        .sum())
                .min()
                .orElseThrow();
    }

    private static int distance(int position, int target) {
        return Math.abs(position - target);
    }

    private static int distanceSummation(int position, int target) {
        int distance = distance(position, target);
        return distance * (distance + 1) / 2;
    }
}
