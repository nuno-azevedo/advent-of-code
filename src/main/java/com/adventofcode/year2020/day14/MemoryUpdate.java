package com.adventofcode.year2020.day14;

import com.google.common.base.Strings;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public record MemoryUpdate(String bitmask, long address, long value) {
    private static final char ZERO = '0';
    private static final char ONE = '1';
    private static final char X = 'X';

    MemoryUpdate applyBitmaskToValue() {
        return new MemoryUpdate(bitmask, address, asDecimal(applyBitmask(value, X)));
    }

    List<MemoryUpdate> applyBitmaskToAddress() {
        List<MemoryUpdate> updates = new LinkedList<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(applyBitmask(address, ZERO));

        while (!queue.isEmpty()) {
            String address = queue.poll();

            int xIndex = address.indexOf(X);
            if (xIndex != -1) {
                char[] chars = address.toCharArray();

                chars[xIndex] = ZERO;
                queue.add(new String(chars));

                chars[xIndex] = ONE;
                queue.add(new String(chars));
            } else {
                updates.add(new MemoryUpdate(bitmask, asDecimal(address), value));
            }
        }

        return updates;
    }

    private String applyBitmask(long number, char skip) {
        StringBuilder binary = new StringBuilder(asBinary(number));

        for (int i = 0; i < binary.length(); i++) {
            if (bitmask.charAt(i) != skip) {
                binary.setCharAt(i, bitmask.charAt(i));
            }
        }

        return binary.toString();
    }

    private String asBinary(long decimal) {
        return Strings.padStart(Long.toBinaryString(decimal), bitmask.length(), ZERO);
    }

    private long asDecimal(String binary) {
        return Long.parseLong(binary, 2);
    }
}
