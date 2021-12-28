package com.adventofcode.year2021.day04;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.input.TextSplitter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;

@DayPuzzle(year = 2021, day = 4)
public class GiantSquid extends AbstractPuzzle<Bingo> {
    static {
        puzzle = GiantSquid.class;
    }

    @Override
    protected long partOne(Bingo input) {
        return calculateBoardScores(input, (a, b) -> a);
    }

    @Override
    protected long partTwo(Bingo input) {
        return calculateBoardScores(input, (a, b) -> b);
    }

    @Override
    protected Bingo readInput(Path path) throws IOException {
        String content = Files.readString(path, Charset.defaultCharset());

        List<Integer> drawnNumbersOrder = TextSplitter.onPattern("\\n{2}", content).findFirst()
                .map(line -> TextSplitter.onComma(line).map(Integer::parseInt).toList())
                .orElseThrow();

        List<Board> boards = TextSplitter.onPattern("\\n{2}", content).skip(1L)
                .map(board -> new Board(TextSplitter.onNewLine(board)
                        .map(line -> TextSplitter.onSpace(line).map(Integer::parseInt).toList())
                        .toList()))
                .toList();

        return new Bingo(drawnNumbersOrder, boards);
    }

    private static Integer calculateBoardScores(Bingo bingo, BinaryOperator<Integer> accumulator) {
        Set<Board> winners = new HashSet<>();

        return IntStream.range(0, bingo.drawnNumbersOrder().size())
                .mapToObj(bingo::getDrawnNumbers)
                .flatMap(drawnNumbers -> bingo.boards().stream()
                        .filter(board -> board.isWinningBoard(drawnNumbers))
                        .filter(winners::add)
                        .map(board -> board.calculateBoardScore(drawnNumbers)))
                .reduce(accumulator)
                .orElseThrow();
    }
}
