package com.adventofcode.year2022.day02;

public record Round(String opponentCode, String unknownCode) {
    int scorePartOne() {
        Shape opponent = Shape.fromCode(opponentCode);
        Shape mine = Shape.fromCode(unknownCode);
        return mine.getScore() + mine.outcomeVersus(opponent).getScore();
    }

    int scorePartTwo() {
        Shape opponent = Shape.fromCode(opponentCode);
        Outcome outcome = Outcome.fromCode(unknownCode);
        return outcome.shapeVersus(opponent).getScore() + outcome.getScore();
    }
}
