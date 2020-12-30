package com.adventofcode.year2020.day09;

import java.util.Collections;
import java.util.LinkedList;

class SortedLinkedList<E extends Comparable<E>> extends LinkedList<E> {
    @Override
    public boolean add(E e) {
        int index = Collections.binarySearch(this, e);
        super.add(index >= 0 ? index : ~index, e);
        return true;
    }
}
