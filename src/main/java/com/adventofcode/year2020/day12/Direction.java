package com.adventofcode.year2020.day12;

import java.util.Arrays;

enum Direction {
    NORTH('N', 1) {
        Direction rotate(int degrees) {
            return switch (degrees % 360) {
                case -270, 90 -> EAST;
                case -180, 180 -> SOUTH;
                case -90, 270 -> WEST;
                default -> this;
            };
        }
    },
    SOUTH('S', -1) {
        Direction rotate(int degrees) {
            return switch (degrees % 360) {
                case -270, 90 -> WEST;
                case -180, 180 -> NORTH;
                case -90, 270 -> EAST;
                default -> this;
            };
        }
    },
    EAST('E', 1) {
        Direction rotate(int degrees) {
            return switch (degrees % 360) {
                case -270, 90 -> SOUTH;
                case -180, 180 -> WEST;
                case -90, 270 -> NORTH;
                default -> this;
            };
        }
    },
    WEST('W', -1) {
        Direction rotate(int degrees) {
            return switch (degrees % 360) {
                case -270, 90 -> NORTH;
                case -180, 180 -> EAST;
                case -90, 270 -> SOUTH;
                default -> this;
            };
        }
    },
    FORWARD('F', 1) {
        Direction rotate(int degrees) {
            throw new UnsupportedOperationException();
        }
    },
    LEFT('L', -1) {
        Direction rotate(int degrees) {
            throw new UnsupportedOperationException();
        }
    },
    RIGHT('R', 1) {
        Direction rotate(int degrees) {
            throw new UnsupportedOperationException();
        }
    };

    private final char code;
    private final int signal;

    Direction(char code, int signal) {
        this.code = code;
        this.signal = signal;
    }

    int getSignal() {
        return signal;
    }

    abstract Direction rotate(int degrees);

    static Direction fromCode(char code) {
        return Arrays.stream(values())
                .filter(direction -> direction.code == code)
                .findFirst()
                .orElseThrow();
    }
}
