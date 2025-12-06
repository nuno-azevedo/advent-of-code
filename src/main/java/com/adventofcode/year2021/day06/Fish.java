package com.adventofcode.year2021.day06;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.Optional;

@AllArgsConstructor
@EqualsAndHashCode
public class Fish {
    private static final short ZERO = 0;
    private static final short RESET = 6;
    private static final short INITIAL = 8;

    private short timer;

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
}
