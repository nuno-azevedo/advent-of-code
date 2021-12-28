package com.adventofcode.year2021.day06;

import java.util.Objects;
import java.util.Optional;

public class Fish {
    private static final short ZERO = 0;
    private static final short RESET = 6;
    private static final short INITIAL = 8;

    private short timer;

    public Fish(short timer) {
        this.timer = timer;
    }

    Optional<Fish> process() {
        if (timer == ZERO) {
            timer = RESET;
            return Optional.of(new Fish(INITIAL));
        } else {
            timer--;
            return Optional.empty();
        }
    }

    Fish copy() {
        return new Fish(timer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fish fish = (Fish) o;
        return timer == fish.timer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(timer);
    }
}
