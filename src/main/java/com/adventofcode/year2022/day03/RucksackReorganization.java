package com.adventofcode.year2022.day03;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.input.InputReader;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

@DayPuzzle(year = 2022, day = 3)
public class RucksackReorganization extends AbstractPuzzle<List<Rucksack>> {
    private static final short GROUP = 3;
    private static final List<Character> CHARACTERS = IntStream
            .concat(IntStream.rangeClosed('a', 'z'), IntStream.rangeClosed('A', 'Z'))
            .mapToObj(c -> (char) c)
            .toList();

    static {
        puzzle = RucksackReorganization.class;
    }

    @Override
    protected long partOne(List<Rucksack> input) {
        return sumPrioritiesOfWrongItems(input);
    }

    @Override
    protected long partTwo(List<Rucksack> input) {
        return sumPrioritiesOfGroupBadges(input);
    }

    @Override
    protected List<Rucksack> readInput(Path path) {
        return InputReader.readAsLineList(path, Rucksack::new);
    }

    private static int sumPrioritiesOfWrongItems(List<Rucksack> rucksacks) {
        return rucksacks.stream()
                .mapToInt(RucksackReorganization::findWrongItemPriority)
                .sum();
    }

    private static int sumPrioritiesOfGroupBadges(List<Rucksack> rucksacks) {
        return Lists.partition(rucksacks, GROUP).stream()
                .mapToInt(RucksackReorganization::findGroupBadgePriority)
                .sum();
    }

    private static int findWrongItemPriority(Rucksack rucksack) {
        for (char item : rucksack.firstCompartment().toCharArray()) {
            if (rucksack.secondCompartment().indexOf(item) != -1) {
                return getPriorityOfItem(item);
            }
        }
        return 0;
    }

    private static int findGroupBadgePriority(List<Rucksack> group) {
        Set<Character> commonItems = new HashSet<>(CHARACTERS);
        group.forEach(rucksack -> commonItems.retainAll(rucksack.uniqueItems()));
        return getPriorityOfItem(Iterables.getOnlyElement(commonItems));
    }

    private static int getPriorityOfItem(Character item) {
        return CHARACTERS.indexOf(item) + 1;
    }
}
