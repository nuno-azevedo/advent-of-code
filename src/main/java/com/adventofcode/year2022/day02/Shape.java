package com.adventofcode.year2022.day02;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
enum Shape {
    ROCK(1) {
        Outcome outcomeVersus(Shape opponent) {
            return switch (opponent) {
                case ROCK -> Outcome.DRAW;
                case PAPER -> Outcome.LOSE;
                case SCISSORS -> Outcome.WIN;
            };
        }
    },
    PAPER(2) {
        Outcome outcomeVersus(Shape opponent) {
            return switch (opponent) {
                case ROCK -> Outcome.WIN;
                case PAPER -> Outcome.DRAW;
                case SCISSORS -> Outcome.LOSE;
            };
        }
    },
    SCISSORS(3) {
        Outcome outcomeVersus(Shape opponent) {
            return switch (opponent) {
                case ROCK -> Outcome.LOSE;
                case PAPER -> Outcome.WIN;
                case SCISSORS -> Outcome.DRAW;
            };
        }
    };

    @Getter
    private final int score;

    abstract Outcome outcomeVersus(Shape opponent);

    static Shape fromCode(String code) {
        return switch (code) {
            case "A", "X" -> ROCK;
            case "B", "Y" -> PAPER;
            case "C", "Z" -> SCISSORS;
            default -> throw new IllegalArgumentException();
        };
    }
}
