package com.adventofcode.common;

import com.google.common.base.Stopwatch;

import java.util.function.Supplier;

public class Timer {
    private final Stopwatch stopwatch = Stopwatch.createUnstarted();

    public void time(Runnable runnable) {
        this.stopwatch.reset();
        this.stopwatch.start();
        runnable.run();
        this.stopwatch.stop();
    }

    public <T> T time(Supplier<T> supplier) {
        this.stopwatch.reset();
        this.stopwatch.start();
        T result = supplier.get();
        this.stopwatch.stop();
        return result;
    }

    @Override
    public String toString() {
        return this.stopwatch.toString();
    }
}
