package com.adventofcode.year2020.day04;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@DayPuzzle(year = 2020, day = 4)
public class PassportProcessing extends AbstractPuzzle<List<Map<String, String>>> {
    private static final List<String> MANDATORY = List.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");
    private static final Map<String, Predicate<String>> CONSTRAINTS = ImmutableMap.<String, Predicate<String>>builder()
            .put("byr", new NumberConstraint(1920, 2002))
            .put("iyr", new NumberConstraint(2010, 2020))
            .put("eyr", new NumberConstraint(2020, 2030))
            .put("hgt", new RegexConstraint("^((1[5-8][0-9]|19[0-3])cm|(59|6[0-9]|7[0-6])in)$"))
            .put("hcl", new RegexConstraint("^#[0-9a-f]{6}$"))
            .put("ecl", new RegexConstraint("^(amb|blu|brn|gry|grn|hzl|oth)$"))
            .put("pid", new RegexConstraint("^[0-9]{9}$"))
            .put("cid", string -> true)
            .build();

    static {
        puzzle = PassportProcessing.class;
    }

    @Override
    protected long partOne(List<Map<String, String>> input) {
        return verifyPassportsMandatoryOnly(input);
    }

    @Override
    protected long partTwo(List<Map<String, String>> input) {
        return verifyPassportsMandatoryAndConstraints(input);
    }

    @Override
    protected List<Map<String, String>> readInput(Path path) throws IOException {
        String content = Files.readString(path, Charset.defaultCharset());

        return Arrays.stream(content.split("\\n{2}"))
                .map(lines -> Splitter.onPattern("\\s+")
                        .trimResults()
                        .omitEmptyStrings()
                        .withKeyValueSeparator(":")
                        .split(lines))
                .toList();
    }

    private static long verifyPassportsMandatoryOnly(List<Map<String, String>> passports) {
        return passports.stream()
                .filter(passport -> passport.keySet().containsAll(MANDATORY))
                .count();
    }

    private static long verifyPassportsMandatoryAndConstraints(List<Map<String, String>> passports) {
        return passports.stream()
                .filter(passport -> passport.keySet().containsAll(MANDATORY))
                .filter(passport -> passport.entrySet().stream()
                        .allMatch(entry -> CONSTRAINTS.get(entry.getKey()).test(entry.getValue())))
                .count();
    }
}
