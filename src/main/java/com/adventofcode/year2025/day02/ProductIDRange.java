package com.adventofcode.year2025.day02;

import com.google.common.base.Splitter;

import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public record ProductIDRange(long firstID, long lastID) {
    List<Long> getSequenceRepeatedTwice() {
        return LongStream.rangeClosed(firstID, lastID).parallel().boxed()
                .filter(id -> {
                    var string = String.valueOf(id);
                    if (string.length() % 2 != 0) {
                        return false;
                    }
                    return checkPiecesAreEqual(string, string.length() / 2);
                })
                .toList();
    }

    List<Long> getSequenceRepeatedAtLeastTwice() {
        return LongStream.rangeClosed(firstID, lastID).parallel().boxed()
                .filter(id -> {
                    var string = String.valueOf(id);
                    return IntStream.rangeClosed(1, string.length() / 2)
                            .anyMatch(length -> checkPiecesAreEqual(string, length));
                })
                .toList();
    }

    private boolean checkPiecesAreEqual(String id, int length) {
        var parts = Splitter.fixedLength(length).splitToList(id);
        return Set.copyOf(parts).size() == 1;
    }
}
