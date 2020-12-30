package com.adventofcode.year2020;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.year2020.day01.ReportRepair;
import com.adventofcode.year2020.day02.PasswordPhilosophy;
import com.adventofcode.year2020.day03.TobogganTrajectory;
import com.adventofcode.year2020.day04.PassportProcessing;
import com.adventofcode.year2020.day05.BinaryBoarding;
import com.adventofcode.year2020.day06.CustomCustoms;
import com.adventofcode.year2020.day07.HandyHaversacks;
import com.adventofcode.year2020.day08.HandheldHalting;
import com.adventofcode.year2020.day09.EncodingError;
import com.adventofcode.year2020.day10.AdapterArray;
import com.adventofcode.year2020.day11.SeatingSystem;
import com.adventofcode.year2020.day12.RainRisk;
import com.adventofcode.year2020.day13.ShuttleSearch;
import com.adventofcode.year2020.day14.DockingData;
import com.adventofcode.year2020.day15.RambunctiousRecitation;
import com.adventofcode.year2020.day16.TicketTranslation;

import java.util.List;

/**
 * <a href="https://adventofcode.com/2020">Advent of Code 2020</a>
 */
public class Main {
    private static final List<AbstractPuzzle<?>> PUZZLES = List.of(
            new ReportRepair(),
            new PasswordPhilosophy(),
            new TobogganTrajectory(),
            new PassportProcessing(),
            new BinaryBoarding(),
            new CustomCustoms(),
            new HandyHaversacks(),
            new HandheldHalting(),
            new EncodingError(),
            new AdapterArray(),
            new SeatingSystem(),
            new RainRisk(),
            new ShuttleSearch(),
            new DockingData(),
            new RambunctiousRecitation(),
            new TicketTranslation()
    );

    public static void main(String[] args) {
        PUZZLES.forEach(AbstractPuzzle::run);
    }
}
