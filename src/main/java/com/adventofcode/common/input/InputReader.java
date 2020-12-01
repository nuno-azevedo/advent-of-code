package com.adventofcode.common.input;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public final class InputReader {
    private InputReader() { }

    public static Stream<String> readAsLineStream(Path path) {
        try {
            return Files.lines(path, Charset.defaultCharset());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static List<String> readAsLineList(Path path) {
        return readAsLineStream(path).toList();
    }

    public static <T> List<T> readAsLineList(Path path, Function<String, T> converter) {
        return readAsLineStream(path).map(converter).toList();
    }

    public static <T> List<T> readAsLineList(Path path, Pattern pattern, Function<Matcher, T> converter) {
        return readAsLineStream(path).map(line -> matchPattern(pattern, line)).map(converter).toList();
    }

    public static List<Integer> readAsIntegerList(Path path) {
        return readAsLineStream(path).map(Integer::parseInt).toList();
    }

    public static List<Long> readAsLongList(Path path) {
        return readAsLineStream(path).map(Long::parseLong).toList();
    }

    public static int[][] readAsDigitGrid(Path path) {
        return readAsLineStream(path)
                .map(line -> line.chars().map(Character::getNumericValue).toArray())
                .toArray(int[][]::new);
    }

    public static char[][] readAsCharGrid(Path path) {
        return readAsLineStream(path)
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }

    public static Matcher matchPattern(Pattern pattern, String line) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.matches()) {
            return matcher;
        }
        throw new IllegalArgumentException();
    }
}
