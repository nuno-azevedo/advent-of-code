package com.adventofcode.year2021.day03;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.input.InputReader;

import java.nio.file.Path;
import java.util.Arrays;

@DayPuzzle(year = 2021, day = 3)
public class BinaryDiagnostic extends AbstractPuzzle<char[][]> {
    private static final char ZERO = '0';
    private static final char ONE = '1';

    private static final int RADIX = 2;

    static {
        puzzle = BinaryDiagnostic.class;
    }

    @Override
    protected long partOne(char[][] input) {
        return calculatePowerConsumption(input);
    }

    @Override
    protected long partTwo(char[][] input) {
        return calculateLifeSupportRating(input);
    }

    @Override
    protected char[][] readInput(Path path) {
        return InputReader.readAsCharGrid(path);
    }

    private static int calculatePowerConsumption(char[][] diagnosticReport) {
        StringBuilder gammaRate = new StringBuilder();
        StringBuilder epsilonRate = new StringBuilder();

        for (int position = 0; position < diagnosticReport[0].length; ++position) {
            char mostCommonBit = findMostCommonBit(diagnosticReport, position);
            gammaRate.append(mostCommonBit);
            epsilonRate.append(mostCommonBit ^ ONE);
        }

        return Integer.parseInt(gammaRate.toString(), RADIX) * Integer.parseInt(epsilonRate.toString(), RADIX);
    }

    private static int calculateLifeSupportRating(char[][] diagnosticReport) {
        char[][] oxygenRatings = diagnosticReport;
        char[][] co2Ratings = diagnosticReport;

        for (int position = 0; position < diagnosticReport[0].length; position++) {
            int i = position;

            if (oxygenRatings.length > 1) {
                char leastCommonBit = findMostCommonBit(oxygenRatings, position);
                oxygenRatings = Arrays.stream(oxygenRatings)
                        .filter(binary -> binary[i] == leastCommonBit)
                        .toArray(char[][]::new);
            }

            if (co2Ratings.length > 1) {
                char leastCommonBit = Character.forDigit(findMostCommonBit(co2Ratings, position) ^ ONE, RADIX);
                co2Ratings = Arrays.stream(co2Ratings)
                        .filter(binary -> binary[i] == leastCommonBit)
                        .toArray(char[][]::new);
            }
        }

        return Integer.parseInt(new String(oxygenRatings[0]), RADIX) * Integer.parseInt(new String(co2Ratings[0]), RADIX);
    }

    private static char findMostCommonBit(char[][] binaries, int position) {
        int bit0 = 0;
        int bit1 = 0;

        for (char[] binary : binaries) {
            switch (binary[position]) {
                case ZERO -> ++bit0;
                case ONE -> ++bit1;
            }
        }

        return bit0 > bit1 ? ZERO : ONE;
    }
}
