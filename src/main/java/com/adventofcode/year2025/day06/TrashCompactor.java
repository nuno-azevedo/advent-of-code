package com.adventofcode.year2025.day06;

import com.adventofcode.AbstractPuzzle;
import com.adventofcode.DayPuzzle;
import com.adventofcode.common.input.TableParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.function.UnaryOperator;

@DayPuzzle(year = 2025, day = 6)
public class TrashCompactor extends AbstractPuzzle<List<List<String>>> {
    static {
        puzzle = TrashCompactor.class;
    }

    @Override
    protected long partOne(List<List<String>> input) {
        var mathWorksheet = makeMathWorksheet(input, UnaryOperator.identity());
        return mathWorksheet.addAnswersOfProblems();
    }

    @Override
    protected long partTwo(List<List<String>> input) {
        var mathWorksheet = makeMathWorksheet(input, this::parseNumbersRightToLeft);
        return mathWorksheet.addAnswersOfProblems();
    }

    @Override
    protected List<List<String>> readInput(Path path) throws IOException {
        var input = Files.readString(path);
        var worksheet = TableParser.parse(input, "\\h+");
        return TableParser.transpose(worksheet);
    }

    private List<String> parseNumbersRightToLeft(List<String> numbers) {
        var rightToLeftNumbers = new LinkedList<String>();

        for (int i = numbers.getFirst().length() - 1; i >= 0; i--) {
            var stringBuilder = new StringBuilder();

            for (String number : numbers) {
                stringBuilder.append(number.charAt(i));
            }

            rightToLeftNumbers.add(stringBuilder.toString());
        }

        return rightToLeftNumbers;
    }

    private MathWorksheet makeMathWorksheet(List<List<String>> worksheet, UnaryOperator<List<String>> numbersMapper) {
        var problems = worksheet.stream()
                .map(problem -> makeProblem(problem, numbersMapper))
                .toList();

        return new MathWorksheet(problems);
    }

    private MathWorksheet.Problem makeProblem(List<String> problem, UnaryOperator<List<String>> numbersMapper) {
        var numbers = numbersMapper.apply(problem.subList(0, problem.size() - 1));
        var numbersParsed = numbers.stream().map(String::trim).map(Long::parseLong).toList();

        var operator = problem.getLast();
        var mathOperator = MathWorksheet.MathOperator.fromSymbol(operator.trim().charAt(0));

        return new MathWorksheet.Problem(numbersParsed, mathOperator);
    }
}
