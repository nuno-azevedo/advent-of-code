package com.adventofcode.year2023.day04;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.input.InputReader;
import com.adventofcode.common.input.TextSplitter;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@DayPuzzle(year = 2023, day = 4)
public class Scratchcards extends AbstractPuzzle<List<Card>> {
    private static final Pattern PATTERN = Pattern.compile("^Card\\h+(\\d+):\\h+((?:\\d+\\h+)+)\\|((?:\\h+\\d+)+)$");

    static {
        puzzle = Scratchcards.class;
    }

    @Override
    protected long partOne(List<Card> input) {
        return input.stream().mapToLong(Card::points).sum();
    }

    @Override
    protected long partTwo(List<Card> input) {
        return processCopiesOfScratchcards(input);
    }

    @Override
    protected List<Card> readInput(Path path) {
        return InputReader.readAsLineList(path, PATTERN, matcher -> {
            int cardNumber = Integer.parseInt(matcher.group(1));

            List<Integer> winningNumbers = TextSplitter.onSpace(matcher.group(2))
                    .map(Integer::valueOf)
                    .toList();

            List<Integer> receivedNumbers = TextSplitter.onSpace(matcher.group(3))
                    .map(Integer::valueOf)
                    .toList();

            return new Card(cardNumber, winningNumbers, receivedNumbers);
        });
    }

    private long processCopiesOfScratchcards(List<Card> scratchcards) {
        Map<Integer, Integer> cardCounter = scratchcards.stream()
                .collect(Collectors.toMap(Card::cardNumber, _ -> 1));

        scratchcards.forEach(card -> {
            int baseCardCount = cardCounter.get(card.cardNumber());
            card.wonCardCopies().forEach(copy -> cardCounter.merge(copy, baseCardCount, Integer::sum));
        });

        return cardCounter.values().stream().mapToLong(i -> i).sum();
    }
}
