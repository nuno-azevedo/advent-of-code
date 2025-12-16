package com.adventofcode.common.algorithms;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class Combinations {
    public static <T> Stream<List<T>> stream(List<T> items, int k) {
        return combinations(items, k);
    }

    public static <T> List<List<T>> of(List<T> items, int k) {
        return combinations(items, k).toList();
    }

    private static <T> Stream<List<T>> combinations(List<T> items, int k) {
        if (k == 0) {
            return Stream.of(List.of());
        }
        if (k < 0 || k > items.size()) {
            return Stream.empty();
        }

        T head = items.getFirst();
        List<T> tail = items.subList(1, items.size());

        return Stream.concat(
                combinations(tail, k - 1).map(rest -> prepend(head, rest)),
                combinations(tail, k)
        );
    }

    private static <T> List<T> prepend(T head, List<T> rest) {
        var list = new ArrayList<T>(rest.size() + 1);
        list.addFirst(head);
        list.addAll(rest);
        return list;
    }
}
