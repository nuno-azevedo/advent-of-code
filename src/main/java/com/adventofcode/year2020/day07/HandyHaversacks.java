package com.adventofcode.year2020.day07;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.graph.Graph;
import com.adventofcode.common.input.InputReader;
import com.adventofcode.common.input.TextSplitter;
import com.google.common.base.Splitter;

import java.nio.file.Path;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@DayPuzzle(year = 2020, day = 7)
public class HandyHaversacks extends AbstractPuzzle<Graph<Node>> {
    private static final Pattern PATTERN = Pattern.compile("^([a-z]+ [a-z]+) bags contain (([1-9] [a-z]+ [a-z]+ bags?(, )?)+|no other bags)\\.$");

    private static final Node BAG = new Node("shiny gold", null);

    static {
        puzzle = HandyHaversacks.class;
    }

    @Override
    protected long partOne(Graph<Node> input) {
        return countValidOutermostBags(input);
    }

    @Override
    protected long partTwo(Graph<Node> input) {
        return countValidInnermostBags(input, BAG);
    }

    @Override
    protected Graph<Node> readInput(Path path) {
        Graph<Node> graph = new Graph<>(true);

        InputReader.readAsLineList(path, PATTERN, matcher -> {
            Node node = new Node(matcher.group(1), null);
            Set<Node> contents = TextSplitter.onPattern("(no other)? bags?(, )?", matcher.group(2))
                    .map(content -> Splitter.onPattern("\\h+")
                            .limit(2)
                            .splitToList(content))
                    .map(content -> new Node(content.getLast(), Integer.valueOf(content.getFirst())))
                    .collect(Collectors.toSet());

            contents.forEach(content -> graph.addEdge(node, content));

            return null;
        });

        return graph;
    }

    private static long countValidOutermostBags(Graph<Node> graph) {
        return graph.nodes().stream()
                .filter(node -> !node.equals(BAG))
                .filter(node -> graph.depthFirstSearchRecursive(node, BAG))
                .count();
    }

    private static int countValidInnermostBags(Graph<Node> graph, Node root) {
        return graph.neighbors(root).stream()
                .mapToInt(node -> node.quantity() + node.quantity() * countValidInnermostBags(graph, node))
                .sum();
    }
}
