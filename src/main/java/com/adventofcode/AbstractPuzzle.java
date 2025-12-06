package com.adventofcode;

import com.adventofcode.common.Timer;
import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.file.Path;

public abstract class AbstractPuzzle<T> {
    protected static Class<? extends AbstractPuzzle<?>> puzzle;

    void main() throws ReflectiveOperationException {
        puzzle.getDeclaredConstructor().newInstance().run();
    }

    public void run() {
        T input = readInput();

        var timer = new Timer();
        System.out.printf("[%04d] Day %02d: %s\n", year(), day(), getClass().getSimpleName());
        System.out.printf("Part One: %d (%s)\n", timer.time(() -> partOne(input)), timer);
        System.out.printf("Part Two: %d (%s)\n", timer.time(() -> partTwo(input)), timer);
        System.out.println();
    }

    @SneakyThrows
    T readInput() {
        var path = Path.of(String.format("src/main/resources/year%04d/day%02d/%s", year(), day(), filename()));
        return readInput(path);
    }

    private int year() {
        return getClass().getAnnotation(DayPuzzle.class).year();
    }

    private int day() {
        return getClass().getAnnotation(DayPuzzle.class).day();
    }

    private String filename() {
        return getClass().getAnnotation(DayPuzzle.class).example() ? "example.txt" : "input.txt";
    }

    protected abstract long partOne(T input);

    protected abstract long partTwo(T input);

    protected abstract T readInput(Path path) throws IOException;
}
