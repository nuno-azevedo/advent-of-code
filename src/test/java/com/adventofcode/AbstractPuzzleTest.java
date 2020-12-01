package com.adventofcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class AbstractPuzzleTest<P extends AbstractPuzzle<?>> {
    private final AbstractPuzzle<?> puzzle;
    private final Object input;

    @SuppressWarnings("unchecked")
    public AbstractPuzzleTest() {
        ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        Class<P> puzzleClass = (Class<P>) superClass.getActualTypeArguments()[0];
        try {
            puzzle = puzzleClass.getDeclaredConstructor().newInstance();
            input = puzzle.readInput();
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex);
        }
    }

    @SuppressWarnings("unchecked")
    private <I> I input() {
        ParameterizedType superClass = (ParameterizedType) puzzle.getClass().getGenericSuperclass();
        Type inputType = superClass.getActualTypeArguments()[0];

        Class<I> inputClass = (Class<I>) switch (inputType) {
            case Class<?> clazz -> clazz;
            case ParameterizedType type -> type.getRawType();
            default -> throw new IllegalArgumentException();
        };
        return inputClass.cast(input);
    }

    @Test
    protected void testPartOne() {
        Assertions.assertEquals(partOneExpectedResult(), puzzle.partOne(input()));
    }

    @Test
    protected void testPartTwo() {
        Assertions.assertEquals(partTwoExpectedResult(), puzzle.partTwo(input()));
    }

    protected abstract long partOneExpectedResult();

    protected abstract long partTwoExpectedResult();
}
