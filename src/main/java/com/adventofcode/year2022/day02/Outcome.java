package com.adventofcode.year2022.day02;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
enum Outcome {
    LOSE(0) {
        Shape shapeVersus(Shape opponent) {
            return switch (opponent) {
                case ROCK -> Shape.SCISSORS;
                case PAPER -> Shape.ROCK;
                case SCISSORS -> Shape.PAPER;
            };
        }
    },
    DRAW(3) {
        Shape shapeVersus(Shape opponent) {
            return opponent;
        }
    },
    WIN(6) {
        Shape shapeVersus(Shape opponent) {
            return switch (opponent) {
                case ROCK -> Shape.PAPER;
                case PAPER -> Shape.SCISSORS;
                case SCISSORS -> Shape.ROCK;
            };
        }
    };

    @Getter
    private final int score;

    abstract Shape shapeVersus(Shape opponent);

    static Outcome fromCode(String code) {
        return switch (code) {
            case "X" -> LOSE;
            case "Y" -> DRAW;
            case "Z" -> WIN;
            default -> throw new IllegalArgumentException();
        };
    }
}
