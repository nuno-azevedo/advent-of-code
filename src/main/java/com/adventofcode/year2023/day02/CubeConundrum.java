package com.adventofcode.year2023.day02;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.input.InputReader;
import com.adventofcode.common.input.TextSplitter;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@DayPuzzle(year = 2023, day = 2)
public class CubeConundrum extends AbstractPuzzle<List<Game>> {
    private static final Pattern PATTERN = Pattern.compile("^Game (\\d+): (((\\d+ (red|green|blue)(, )?)+(; )?)+)$");
    private static final Pattern CUBES = Pattern.compile("^(\\d+) (red|green|blue)$");

    private static final Map<Cube, Integer> BAG = Map.of(
            Cube.RED, 12,
            Cube.GREEN, 13,
            Cube.BLUE, 14
    );

    static {
        puzzle = CubeConundrum.class;
    }

    @Override
    protected long partOne(List<Game> input) {
        return sumPossibleGamesIDs(input);
    }

    @Override
    protected long partTwo(List<Game> input) {
        return sumPowerOfMinimumSets(input);
    }

    @Override
    protected List<Game> readInput(Path path) {
        return InputReader.readAsLineList(path, PATTERN, matcher -> {
            int id = Integer.parseInt(matcher.group(1));

            List<Map<Cube, Integer>> subsets = TextSplitter.onSemicolon(matcher.group(2))
                    .map(TextSplitter::onComma)
                    .map(subsetStream -> subsetStream
                            .map(subset -> InputReader.matchPattern(CUBES, subset))
                            .collect(Collectors.toMap(
                                    cubes -> Cube.valueOf(cubes.group(2).toUpperCase()),
                                    cubes -> Integer.parseInt(cubes.group(1))
                            ))
                    )
                    .toList();

            return new Game(id, subsets);
        });
    }

    private static int sumPossibleGamesIDs(List<Game> games) {
        return games.stream()
                .filter(game -> game.isGamePossible(BAG))
                .mapToInt(Game::id)
                .sum();
    }

    private static int sumPowerOfMinimumSets(List<Game> games) {
        return games.stream()
                .mapToInt(Game::calculatePowerOfMinimumSetOfCubes)
                .sum();
    }
}
