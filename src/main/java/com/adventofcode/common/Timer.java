package com.adventofcode.common;

import com.google.common.base.Stopwatch;

import java.util.function.Supplier;

public class Timer {
    private final Stopwatch stopwatch = Stopwatch.createUnstarted();

    public void time(Runnable runnable) {
        stopwatch.reset();
        stopwatch.start();
        runnable.run();
        stopwatch.stop();
    }

    public <T> T time(Supplier<T> supplier) {
        stopwatch.reset();
        stopwatch.start();
        T result = supplier.get();
        stopwatch.stop();
        return result;
    }

    @Override
    public String toString() {
        return stopwatch.toString();
    }
}
