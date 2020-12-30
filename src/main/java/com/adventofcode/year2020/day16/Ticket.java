package com.adventofcode.year2020.day16;

import java.util.List;
import java.util.function.Predicate;

record Ticket(List<Integer> values) implements Predicate<List<TicketRule>> {
    public boolean test(List<TicketRule> rules) {
        return values.stream().allMatch(value -> rules.stream().anyMatch(rule -> rule.test(value)));
    }
}
