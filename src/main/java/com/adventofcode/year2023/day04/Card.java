package com.adventofcode.year2023.day04;

import java.util.List;
import java.util.stream.Stream;

public record Card(int cardNumber, List<Integer> winningNumbers, List<Integer> receivedNumbers) {
    long matches() {
        return receivedNumbers.stream().filter(winningNumbers::contains).count();
    }

    long points() {
        return (long) Math.pow(2, matches() - 1);
    }

    List<Integer> wonCardCopies() {
        return Stream.iterate(cardNumber + 1, i -> i <= cardNumber + matches(), i -> i + 1).toList();
    }
}
