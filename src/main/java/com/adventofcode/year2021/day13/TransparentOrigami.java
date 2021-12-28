package com.adventofcode.year2021.day13;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.geometry.Point2D;
import com.adventofcode.common.input.InputReader;
import com.google.common.primitives.Chars;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@DayPuzzle(year = 2021, day = 13)
public class TransparentOrigami extends AbstractPuzzle<Paper> {
    private static final Pattern PATTERN = Pattern.compile("^fold along ([xy])=(\\d+)$");

    private static final char DOT = '#';
    private static final char EMPTY = ' ';

    static {
        puzzle = TransparentOrigami.class;
    }

    @Override
    protected long partOne(Paper input) {
        return makeFirstFoldAndCountDots(input);
    }

    @Override
    protected long partTwo(Paper input) {
        return processEveryFold(input);
    }

    @Override
    protected Paper readInput(Path path) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            List<Point2D> dots = reader.lines()
                    .takeWhile(StringUtils::isNotBlank)
                    .map(line -> line.split(","))
                    .map(split -> new Point2D(Integer.parseInt(split[0]), Integer.parseInt(split[1])))
                    .toList();

            List<Fold> folds = reader.lines()
                    .takeWhile(StringUtils::isNotBlank)
                    .map(line -> InputReader.matchPattern(PATTERN, line))
                    .map(matcher -> new Fold(Axis.valueOf(matcher.group(1).toUpperCase()), Integer.parseInt(matcher.group(2))))
                    .toList();

            return new Paper(dots, folds);
        }
    }

    private static int makeFirstFoldAndCountDots(Paper paper) {
        char[][] pattern = initializePattern(paper);
        return paper.folds().stream().findFirst()
                .map(fold -> makeFoldOnPattern(pattern, fold))
                .map(TransparentOrigami::countDotsOnPattern)
                .orElseThrow();
    }

    private static long processEveryFold(Paper paper) {
        char[][] folded = initializePattern(paper);

        for (Fold fold : paper.folds()) {
            folded = makeFoldOnPattern(folded, fold);
        }

        return Arrays.stream(folded)
                .peek(System.out::println)
                .map(Arrays::toString)
                .mapToInt(line -> StringUtils.countMatches(line, DOT))
                .sum();
    }

    private static char[][] initializePattern(Paper paper) {
        int rows = paper.dots().stream().mapToInt(dot -> dot.y() + 1).max().orElseThrow();
        int columns = paper.dots().stream().mapToInt(dot -> dot.x() + 1).max().orElseThrow();

        char[][] pattern = new char[rows][columns];
        Arrays.stream(pattern).forEach(line -> Arrays.fill(line, EMPTY));

        paper.dots().forEach(dot -> pattern[dot.y()][dot.x()] = DOT);
        return pattern;
    }

    private static char[][] makeFoldOnPattern(char[][] pattern, Fold fold) {
        char[][] folded = switch (fold.axis()) {
            case X -> new char[pattern.length][fold.line()];
            case Y -> new char[fold.line()][pattern[0].length];
        };

        Arrays.stream(folded).forEach(line -> Arrays.fill(line, EMPTY));

        for (int r = 0; r < pattern.length; r++) {
            for (int c = 0; c < pattern[r].length; c++) {
                if (!fold.skipRow(r) && !fold.skipColumn(c)) {
                    int rf = getFoldedCoordinate(r, folded.length);
                    int rc = getFoldedCoordinate(c, folded[rf].length);

                    if (pattern[r][c] == DOT) {
                        folded[rf][rc] = DOT;
                    }
                }
            }
        }

        return folded;
    }

    private static int getFoldedCoordinate(int row, int length) {
        return row < length ? row : (length - row % length) % length;
    }

    private static int countDotsOnPattern(char[][] pattern) {
        return (int) Arrays.stream(pattern)
                .flatMap(line -> Chars.asList(line).stream())
                .filter(c -> c == DOT)
                .count();
    }
}
