package com.adventofcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class AbstractPuzzleTest<P extends AbstractPuzzle<?>> {
    private final AbstractPuzzle<?> puzzle;

    @SuppressWarnings("unchecked")
    public AbstractPuzzleTest() {
        ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        Class<P> puzzleClass = (Class<P>) superClass.getActualTypeArguments()[0];
        try {
            this.puzzle = puzzleClass.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex);
        }
    }

    @SuppressWarnings("unchecked")
    private <I> I input() {
        ParameterizedType superClass = (ParameterizedType) this.puzzle.getClass().getGenericSuperclass();
        Type inputType = superClass.getActualTypeArguments()[0];

        Class<I> inputClass = (Class<I>) switch (inputType) {
            case Class<?> clazz -> clazz;
            case ParameterizedType type -> type.getRawType();
            default -> throw new IllegalArgumentException();
        };
        return inputClass.cast(this.puzzle.input);
    }

    @Test
    void testPartOne() {
        Assertions.assertEquals(partOneExpectedResult(), this.puzzle.partOne(input()));
    }

    @Test
    void testPartTwo() {
        Assertions.assertEquals(partTwoExpectedResult(), this.puzzle.partTwo(input()));
    }

    protected abstract long partOneExpectedResult();

    protected abstract long partTwoExpectedResult();
}
