package com.adventofcode.year2023;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.year2023.day01.Trebuchet;
import com.adventofcode.year2023.day02.CubeConundrum;
import com.adventofcode.year2023.day03.GearRatios;
import com.adventofcode.year2023.day04.Scratchcards;

import java.util.List;

/**
 * <a href="https://adventofcode.com/2023">Advent of Code 2023</a>
 */
public class Main {
    private static final List<AbstractPuzzle<?>> PUZZLES = List.of(
            new Trebuchet(),
            new CubeConundrum(),
            new GearRatios(),
            new Scratchcards()
    );

    void main() {
        PUZZLES.forEach(AbstractPuzzle::run);
    }
}
