package com.adventofcode.year2025.day01;

import lombok.Getter;

public class SafeDial {
    private int dialPointer = 50;
    @Getter
    private int zeroStopsCount = 0;
    @Getter
    private int zeroPassesCount = 0;

    public void rotate(Rotation rotation) {
        int nextPointer;

        boolean crossedZero = switch (rotation.direction()) {
            case L -> {
                nextPointer = Math.floorMod(dialPointer - rotation.distance(), 100);
                yield nextPointer == 0 || nextPointer > dialPointer;
            }
            case R -> {
                nextPointer = Math.floorMod(dialPointer + rotation.distance(), 100);
                yield nextPointer < dialPointer;
            }
        };

        if (dialPointer != 0 && crossedZero) {
            zeroPassesCount++;
        }
        zeroPassesCount += rotation.distance() / 100;

        if (nextPointer == 0) {
            zeroStopsCount++;
        }
        dialPointer = nextPointer;
    }
}
