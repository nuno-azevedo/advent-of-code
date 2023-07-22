package com.adventofcode.year2022.day02;

record Round(String opponentCode, String unknownCode) {
    int scorePartOne() {
        Shape opponent = Shape.fromCode(this.opponentCode);
        Shape mine = Shape.fromCode(this.unknownCode);
        return mine.getScore() + mine.outcomeVersus(opponent).getScore();
    }

    int scorePartTwo() {
        Shape opponent = Shape.fromCode(this.opponentCode);
        Outcome outcome = Outcome.fromCode(this.unknownCode);
        return outcome.shapeVersus(opponent).getScore() + outcome.getScore();
    }
}
