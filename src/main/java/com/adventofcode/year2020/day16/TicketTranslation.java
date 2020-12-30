package com.adventofcode.year2020.day16;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.domain.Range;
import com.adventofcode.common.input.TextSplitter;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

@DayPuzzle(year = 2020, day = 16)
public class TicketTranslation extends AbstractPuzzle<TicketNotes> {
    private static final Pattern RULE = Pattern.compile("^([a-z]+(?: [a-z]+)?): ([0-9]{1,3})-([0-9]{1,3}) or ([0-9]{1,3})-([0-9]{1,3})$");
    private static final Pattern TICKET = Pattern.compile("^[0-9]{1,3}(,[0-9]{1,3})+$");

    private static final String DEPARTURE = "departure";

    static {
        puzzle = TicketTranslation.class;
    }

    @Override
    protected long partOne(TicketNotes input) {
        return identifyInvalidNearbyTickets(input);
    }

    @Override
    protected long partTwo(TicketNotes input) {
        return identifyDepartureValues(input);
    }

    @Override
    protected TicketNotes readInput(Path path) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            List<TicketRule> rules = new LinkedList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = RULE.matcher(line);
                if (matcher.matches()) {
                    rules.add(new TicketRule(
                            matcher.group(1),
                            new Range<>(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3))),
                            new Range<>(Integer.parseInt(matcher.group(4)), Integer.parseInt(matcher.group(5)))
                    ));
                } else if (line.equals("your ticket:")) {
                    break;
                }
            }

            Ticket myTicket = new Ticket(TextSplitter.onComma(reader.readLine()).map(Integer::parseInt).toList());

            List<Ticket> nearbyTickets = new LinkedList<>();
            while ((line = reader.readLine()) != null) {
                Matcher matcher = TICKET.matcher(line);
                if (matcher.matches()) {
                    nearbyTickets.add(new Ticket(TextSplitter.onComma(line).map(Integer::parseInt).toList()));
                }
            }

            return new TicketNotes(rules, myTicket, nearbyTickets);
        }
    }

    private static int identifyInvalidNearbyTickets(TicketNotes notes) {
        return notes.nearbyTickets().stream()
                .flatMap(ticket -> ticket.values().stream()
                        .filter(value -> notes.rules().stream().noneMatch(rule -> rule.test(value))))
                .mapToInt(i -> i)
                .sum();
    }

    private static long identifyDepartureValues(TicketNotes notes) {
        List<Ticket> validNearbyTickets = notes.nearbyTickets().stream()
                .filter(ticket -> ticket.test(notes.rules()))
                .toList();

        Multimap<TicketRule, Integer> rulePossibleIndexes = notes.rules().stream().collect(
                HashMultimap::create,
                (map, rule) -> map.putAll(rule, findPossibleIndexesOfRule(rule, validNearbyTickets)),
                (a, b) -> a.putAll(b)
        );

        while (rulePossibleIndexes.keySet().size() != rulePossibleIndexes.values().size()) {
            List<Integer> uniqueIndexes = rulePossibleIndexes.asMap().values().stream()
                    .filter(indexes -> indexes.size() == 1)
                    .flatMap(Collection::stream)
                    .toList();

            uniqueIndexes.forEach(index -> rulePossibleIndexes.asMap().values().stream()
                    .filter(indexes -> indexes.size() > 1)
                    .forEach(indexes -> indexes.remove(index))
            );
        }

        return rulePossibleIndexes.entries().stream()
                .filter(entry -> entry.getKey().field().startsWith(DEPARTURE))
                .mapToLong(entry -> notes.myTicket().values().get(entry.getValue()))
                .reduce(1L, Math::multiplyExact);
    }

    private static List<Integer> findPossibleIndexesOfRule(TicketRule rule, List<Ticket> tickets) {
        return IntStream.range(0, tickets.getFirst().values().size()).boxed()
                .filter(i -> verifyRuleAgainstIndex(rule, tickets, i))
                .toList();
    }

    private static boolean verifyRuleAgainstIndex(TicketRule rule, List<Ticket> tickets, int index) {
        return tickets.stream().map(ticket -> ticket.values().get(index)).allMatch(rule);
    }
}
