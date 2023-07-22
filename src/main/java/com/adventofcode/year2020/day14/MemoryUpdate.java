package com.adventofcode.year2020.day14;

import com.google.common.base.Strings;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

record MemoryUpdate(String bitmask, long address, long value) {
    private static final char ZERO = '0';
    private static final char ONE = '1';
    private static final char X = 'X';

    MemoryUpdate applyBitmaskToValue() {
        return new MemoryUpdate(this.bitmask, this.address, asDecimal(applyBitmask(this.value, X)));
    }

    List<MemoryUpdate> applyBitmaskToAddress() {
        List<MemoryUpdate> updates = new LinkedList<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(applyBitmask(this.address, ZERO));

        while (!queue.isEmpty()) {
            String address = queue.poll();

            if (address.contains(String.valueOf(X))) {
                queue.add(address.replaceFirst(String.valueOf(X), String.valueOf(ZERO)));
                queue.add(address.replaceFirst(String.valueOf(X), String.valueOf(ONE)));
            } else {
                updates.add(new MemoryUpdate(this.bitmask, asDecimal(address), this.value));
            }
        }

        return updates;
    }

    private String applyBitmask(long number, char skip) {
        StringBuilder binary = new StringBuilder(asBinary(number));

        for (int i = 0; i < binary.length(); i++) {
            if (this.bitmask.charAt(i) != skip) {
                binary.setCharAt(i, this.bitmask.charAt(i));
            }
        }

        return binary.toString();
    }

    private String asBinary(long decimal) {
        return Strings.padStart(Long.toBinaryString(decimal), this.bitmask.length(), ZERO);
    }

    private long asDecimal(String binary) {
        return Long.parseLong(binary, 2);
    }
}
