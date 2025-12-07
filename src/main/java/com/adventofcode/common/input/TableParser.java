package com.adventofcode.common.input;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class TableParser {
    private static final int DEFAULT = 0;

    public static List<List<String>> parse(String input, String tokenSeparatorRegex) {
        var lines = input.lines().toList();
        var columnWidths = computeColumnWidths(lines, tokenSeparatorRegex);
        return lines.stream().map(line -> sliceRowByWidths(line, columnWidths)).toList();
    }

    public static <T> List<List<T>> transpose(List<List<T>> table) {
        int maxColumnCount = findMaxColumnCount(table);

        return IntStream.range(0, maxColumnCount)
                .mapToObj(columnIndex -> transposeColumn(table, columnIndex))
                .toList();
    }

    private static <T> List<T> transposeColumn(List<List<T>> table, int columnIndex) {
        return table.stream()
                .map(row -> columnIndex < row.size() ? row.get(columnIndex) : null)
                .toList();
    }

    private static <T> int findMaxColumnCount(List<List<T>> table) {
        return table.stream().mapToInt(List::size).max().orElse(DEFAULT);
    }

    private static int[] computeColumnWidths(List<String> lines, String tokenSeparatorRegex) {
        var tokensTable = lines.stream()
                .map(line -> TextSplitter.onPattern(tokenSeparatorRegex, line).toList())
                .toList();

        int maxColumnCount = findMaxColumnCount(tokensTable);

        return IntStream.range(0, maxColumnCount)
                .map(columnIndex -> findMaxWidthForColumn(tokensTable, columnIndex))
                .toArray();
    }

    private static int findMaxWidthForColumn(List<List<String>> tokensTable, int columnIndex) {
        return tokensTable.stream()
                .mapToInt(row -> columnIndex < row.size() ? row.get(columnIndex).length() : DEFAULT)
                .max()
                .orElse(DEFAULT);
    }

    private static List<String> sliceRowByWidths(String row, int[] widths) {
        var tokens = new ArrayList<String>(widths.length);
        int index = 0;

        for (int width : widths) {
            int end = index + width;

            if (index >= row.length()) {
                tokens.add(null);
            } else if (end >= row.length()) {
                tokens.add(row.substring(index));
            } else {
                tokens.add(row.substring(index, end));
            }

            // Skip the column separator (assumed to be a single character).
            index = end + 1;
        }

        return tokens;
    }
}
