package com.adventofcode.year2022.day05;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.input.InputReader;
import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@DayPuzzle(year = 2022, day = 5)
public class SupplyStacks extends AbstractPuzzle<RearrangementProcedure> {
    private static final Pattern PATTERN = Pattern.compile("^move (\\d+) from (\\d+) to (\\d+)$");

    static {
        puzzle = SupplyStacks.class;
    }

    @Override
    protected long partOne(RearrangementProcedure input) {
        return 0L;
    }

    @Override
    protected long partTwo(RearrangementProcedure input) {
        return 0L;
    }

    @Override
    protected RearrangementProcedure readInput(Path path) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            List<List<String>> startingStacks = new LinkedList<>();
            List<Move> moves = new LinkedList<>();

            String line;
            while (StringUtils.isNotBlank(line = reader.readLine())) {
                List<String> crates = Splitter.fixedLength(4).trimResults().splitToList(line);

                for (int i = 0; i < crates.size(); ++i) {
                    if (i >= startingStacks.size()) {
                        startingStacks.add(new LinkedList<>());
                    }
                    if (StringUtils.isNotBlank(crates.get(i))) {
                        startingStacks.get(i).addFirst(crates.get(i));
                    }
                }
            }

            while ((line = reader.readLine()) != null) {
                Matcher matcher = InputReader.matchPattern(PATTERN, line);
                moves.add(new Move(
                        Integer.parseInt(matcher.group(1)),
                        Integer.parseInt(matcher.group(2)),
                        Integer.parseInt(matcher.group(3))
                ));
            }

            return new RearrangementProcedure(startingStacks, moves);
        }
    }
}
