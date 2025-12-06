package com.adventofcode.year2022;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.year2022.day01.CalorieCounting;
import com.adventofcode.year2022.day02.RockPaperScissors;
import com.adventofcode.year2022.day03.RucksackReorganization;
import com.adventofcode.year2022.day04.CampCleanup;

import java.util.List;

/**
 * <a href="https://adventofcode.com/2022">Advent of Code 2022</a>
 */
public class Main {
    private static final List<AbstractPuzzle<?>> PUZZLES = List.of(
            new CalorieCounting(),
            new RockPaperScissors(),
            new RucksackReorganization(),
            new CampCleanup()
    );

    void main() {
        PUZZLES.forEach(AbstractPuzzle::run);
    }
}
