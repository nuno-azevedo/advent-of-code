package com.adventofcode.year2021.day08;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.input.InputReader;
import com.adventofcode.common.input.TextSplitter;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@DayPuzzle(year = 2021, day = 8)
public class SevenSegmentSearch extends AbstractPuzzle<List<NoteEntry>> {
    private static final Pattern PATTERN = Pattern.compile("^((?:[a-z]{2,7} ){10})\\|((?: [a-z]{2,7}){4})$");

    private static final int ONE = 2;
    private static final int FOUR = 4;
    private static final int SEVEN = 3;
    private static final int EIGHT = 7;
    private static final int ZERO_SIX_NINE = 6;
    private static final int TWO_THREE_FIVE = 5;
    private static final int NUMBERS = 10;

    private static final Set<Integer> UNIQUE = Set.of(ONE, FOUR, SEVEN, EIGHT);

    static {
        puzzle = SevenSegmentSearch.class;
    }

    @Override
    protected long partOne(List<NoteEntry> input) {
        return countOutputsWithUniqueNumberOfSegments(input);
    }

    @Override
    protected long partTwo(List<NoteEntry> input) {
        return decodeAndAddUpNotesOutputs(input);
    }

    @Override
    protected List<NoteEntry> readInput(Path path) {
        return InputReader.readAsLineList(path, PATTERN, matcher -> {
            List<String> patterns = TextSplitter.onSpace(matcher.group(1)).toList();
            List<String> outputs = TextSplitter.onSpace(matcher.group(2)).toList();
            return new NoteEntry(patterns, outputs);
        });
    }

    private static long countOutputsWithUniqueNumberOfSegments(List<NoteEntry> notes) {
        return notes.stream()
                .flatMap(note -> note.outputs().stream())
                .filter(output -> UNIQUE.contains(output.length()))
                .count();
    }

    private static int decodeAndAddUpNotesOutputs(List<NoteEntry> notes) {
        return notes.stream().mapToInt(SevenSegmentSearch::decodeNoteOutput).sum();
    }

    private static int decodeNoteOutput(NoteEntry note) {
        List<Set<Character>> patterns = note.patterns().stream().map(SevenSegmentSearch::stringToSet).toList();
        BiMap<Integer, Set<Character>> mappings = HashBiMap.create();

        while (mappings.size() < NUMBERS) {
            patterns.forEach(pattern -> {
                Set<Character> match;

                switch (pattern.size()) {
                    case ONE -> mappings.put(1, pattern);
                    case SEVEN -> mappings.put(7, pattern);
                    case FOUR -> mappings.put(4, pattern);
                    case EIGHT -> mappings.put(8, pattern);
                    case ZERO_SIX_NINE -> {
                        if ((match = mappings.get(3)) != null && pattern.containsAll(match)) {
                            mappings.put(9, pattern);
                        } else if ((match = mappings.get(5)) != null && pattern.containsAll(match)) {
                            mappings.put(6, pattern);
                        } else if (mappings.containsKey(3) && mappings.containsKey(5)) {
                            mappings.put(0, pattern);
                        }
                    }
                    case TWO_THREE_FIVE -> {
                        if ((match = mappings.get(7)) != null && pattern.containsAll(match)) {
                            mappings.put(3, pattern);
                        } else if ((match = mappings.get(9)) != null && match.containsAll(pattern)) {
                            mappings.put(5, pattern);
                        } else if (mappings.containsKey(7) && mappings.containsKey(9)) {
                            mappings.put(2, pattern);
                        }
                    }
                    default -> throw new NoSuchElementException();
                }

            });
        }

        String decoded = note.outputs().stream()
                .map(SevenSegmentSearch::stringToSet)
                .map(output -> String.valueOf(mappings.inverse().get(output)))
                .collect(Collectors.joining());

        return Integer.parseInt(decoded);
    }

    private static Set<Character> stringToSet(String string) {
        return string.chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
    }
}
