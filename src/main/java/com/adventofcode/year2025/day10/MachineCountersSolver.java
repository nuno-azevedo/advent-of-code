package com.adventofcode.year2025.day10;

import lombok.SneakyThrows;
import org.sosy_lab.java_smt.SolverContextFactory;
import org.sosy_lab.java_smt.api.IntegerFormulaManager;
import org.sosy_lab.java_smt.api.OptimizationProverEnvironment;
import org.sosy_lab.java_smt.api.SolverContext;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Objects;

import static org.sosy_lab.java_smt.api.NumeralFormula.IntegerFormula;

public class MachineCountersSolver {
    private final SolverContext solverContext;
    private final IntegerFormulaManager manager;
    private final IntegerFormula zero;

    private OptimizationProverEnvironment environment;

    @SneakyThrows
    MachineCountersSolver() {
        var solver = SolverContextFactory.Solvers.Z3;
        solverContext = SolverContextFactory.createSolverContext(solver);
        manager = solverContext.getFormulaManager().getIntegerFormulaManager();
        zero = manager.makeNumber(0);
    }

    int solve(MachineManual machineManual) {
        try (var env = initializeEnvironment()) {
            environment = env;

            // One non-negative variable per button.
            // n[b] = number of times button b is pressed
            var buttonVariables = makeVariablesWithConstraints(machineManual.buttons());

            // Each requirement equals the sum of presses of buttons that affect that index.
            // sum of n[b] for buttons b that affect index i == requirements[i]
            addConstraintsForRequirements(buttonVariables, machineManual.requirements());

            // Minimize sum of n[b] over all buttons.
            var objective = createSumObjective(buttonVariables);

            return evaluate(objective);
        }
    }

    private OptimizationProverEnvironment initializeEnvironment() {
        var options = SolverContext.ProverOptions.GENERATE_MODELS;
        return solverContext.newOptimizationProverEnvironment(options);
    }

    @SneakyThrows
    private List<ButtonVariable> makeVariablesWithConstraints(List<BitSet> buttons) {
        var variables = new ArrayList<ButtonVariable>(buttons.size());

        for (int b = 0; b < buttons.size(); b++) {
            var variable = manager.makeVariable("n[%d]".formatted(b));
            var variableGreaterOrEqualsZero = manager.greaterOrEquals(variable, zero);
            environment.addConstraint(variableGreaterOrEqualsZero);

            variables.add(new ButtonVariable(buttons.get(b), variable));
        }

        return variables;
    }

    @SneakyThrows
    private void addConstraintsForRequirements(List<ButtonVariable> buttonVariables, List<Integer> requirements) {
        for (int r = 0; r < requirements.size(); r++) {
            var terms = getVariablesAffectingIndex(buttonVariables, r);
            var requirement = manager.makeNumber(requirements.get(r));

            var sumOfTerms = terms.stream().reduce(zero, manager::add);
            var sumOfTermsEqualRequirement = manager.equal(sumOfTerms, requirement);
            environment.addConstraint(sumOfTermsEqualRequirement);
        }
    }

    private List<IntegerFormula> getVariablesAffectingIndex(List<ButtonVariable> buttonVariables, int index) {
        return buttonVariables.stream()
                .filter(buttonVariable -> buttonVariable.button().get(index))
                .map(ButtonVariable::variable)
                .toList();
    }

    private IntegerFormula createSumObjective(List<ButtonVariable> buttonVariables) {
        return buttonVariables.stream()
                .map(ButtonVariable::variable)
                .reduce(zero, manager::add);
    }

    @SneakyThrows
    private int evaluate(IntegerFormula objective) {
        environment.minimize(objective);

        // First check() establishes satisfiability and starts optimization.
        environment.check();
        // Second check() forces optimization to complete and reach the optimal model.
        // Required for deterministic results across platforms.
        environment.check();

        var model = environment.getModel();
        var solution = model.evaluate(objective);
        return Objects.requireNonNull(solution).intValue();
    }

    record ButtonVariable(BitSet button, IntegerFormula variable) { }
}
