package com.adventofcode.year2021;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.year2021.day01.SonarSweep;
import com.adventofcode.year2021.day02.Dive;
import com.adventofcode.year2021.day03.BinaryDiagnostic;
import com.adventofcode.year2021.day04.GiantSquid;
import com.adventofcode.year2021.day05.HydrothermalVenture;
import com.adventofcode.year2021.day06.LanternFish;
import com.adventofcode.year2021.day07.TreacheryWhales;
import com.adventofcode.year2021.day08.SevenSegmentSearch;
import com.adventofcode.year2021.day09.SmokeBasin;
import com.adventofcode.year2021.day10.SyntaxScoring;
import com.adventofcode.year2021.day11.DumboOctopus;
import com.adventofcode.year2021.day12.PassagePathing;
import com.adventofcode.year2021.day13.TransparentOrigami;
import com.adventofcode.year2021.day14.ExtendedPolymerization;

import java.util.List;

/**
 * <a href="https://adventofcode.com/2021">Advent of Code 2021</a>
 */
public class Main {
    private static final List<AbstractPuzzle<?>> PUZZLES = List.of(
            new SonarSweep(),
            new Dive(),
            new BinaryDiagnostic(),
            new GiantSquid(),
            new HydrothermalVenture(),
            new LanternFish(),
            new TreacheryWhales(),
            new SevenSegmentSearch(),
            new SmokeBasin(),
            new SyntaxScoring(),
            new DumboOctopus(),
            new PassagePathing(),
            new TransparentOrigami(),
            new ExtendedPolymerization()
    );

    public static void main(String[] args) {
        PUZZLES.forEach(AbstractPuzzle::run);
    }
}
