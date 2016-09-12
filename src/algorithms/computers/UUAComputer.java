package algorithms.computers;

import algorithms.outputs.JSCATS;
import algorithms.tools.ConcreteStateComputer;
import algorithms.tools.EStateColor;
import eventb.Machine;
import eventb.expressions.arith.Variable;
import eventb.expressions.bool.ABooleanExpression;
import eventb.expressions.bool.And;
import eventb.expressions.bool.Exists;
import eventb.tools.formatters.ExpressionToSMTLib2Formatter;
import graphs.AbstractTransition;
import graphs.ConcreteState;
import graphs.ConcreteTransition;
import solvers.z3.Z3;

import java.util.*;
import java.util.stream.Collectors;

import static com.microsoft.z3.Status.SATISFIABLE;

/**
 * Created by gvoiron on 18/08/16.
 * Time : 11:01
 */
public final class UUAComputer implements IComputer<JSCATS> {

    private final Machine machine;
    private final JSCATS abstraction;
    private static Map<AbstractTransition, Boolean> MPlus;
    private static Map<AbstractTransition, Boolean> MMinus;

    public UUAComputer(Machine machine, JSCATS abstraction) {
        this.machine = machine;
        this.abstraction = abstraction;
    }

    @Override
    public JSCATS compute() {
        // Time measurement
        long startTime;
        long endTime;
        double computationTime;
        startTime = System.nanoTime();
        // Step 0: Variables declaration
        JSCATS connectedJSCATS = new JSCATS(new LinkedHashSet<>(abstraction.getQ()), new LinkedHashSet<>(abstraction.getQ0()), new LinkedHashSet<>(abstraction.getC()), new LinkedHashSet<>(abstraction.getIc0()), new LinkedHashSet<>(abstraction.getDelta()), new LinkedHashSet<>(abstraction.getDeltaPlus()), new LinkedHashSet<>(abstraction.getDeltaMinus()), new LinkedHashMap<>(abstraction.getAlpha()), new LinkedHashMap<>(abstraction.getKappa()), new LinkedHashSet<>(abstraction.getDeltaC()), 0);
        MPlus = new LinkedHashMap<>();
        MMinus = new LinkedHashMap<>();
        connectedJSCATS.getDeltaPlus().forEach(abstractTransition -> MPlus.put(abstractTransition, false));
        connectedJSCATS.getDeltaMinus().forEach(abstractTransition -> MMinus.put(abstractTransition, false));
        // Step 1: t has not been concretized yet and is an entry point for a must+ structure
        MPlus.forEach((t, marked) -> {
            if (!marked) {
                if (isMustPlusStructureEntryPoint(t, connectedJSCATS)) {
                    Optional<ConcreteState> optionalC = connectedJSCATS.getAlpha().keySet().stream().filter(concreteState -> connectedJSCATS.getAlpha().get(concreteState).equals(t.getSource())).findFirst();
                    if (optionalC.isPresent()) {
                        ConcreteState c = optionalC.get();
                        mustPlusConcretization(t, c, machine, connectedJSCATS);
                    }
                }
            }
        });
        // Step 2: t has not been concretized yet and is an entry point for a must- structure
        MMinus.forEach((t, marked) -> {
            if (!marked) {
                if (isMustMinusStructureEntryPoint(t, connectedJSCATS)) {
                    Optional<ConcreteState> optionalCPrime = connectedJSCATS.getAlpha().keySet().stream().filter(concreteState -> connectedJSCATS.getAlpha().get(concreteState).equals(t.getTarget())).findFirst();
                    if (optionalCPrime.isPresent()) {
                        ConcreteState cPrime = optionalCPrime.get();
                        mustMinusConcretization(t, cPrime, machine, connectedJSCATS);
                    }
                }
            }
        });
        // Step 3: concretisation of the must+ structures with no entry point
        MPlus.forEach((t, marked) -> {
            if (!marked) {
                Optional<ConcreteState> optionalC = connectedJSCATS.getAlpha().keySet().stream().filter(concreteState -> connectedJSCATS.getAlpha().get(concreteState).equals(t.getSource())).findFirst();
                if (optionalC.isPresent()) {
                    ConcreteState c = optionalC.get();
                    mustPlusConcretization(t, c, machine, connectedJSCATS);
                }
            }
        });
        // Step 4: concretisation of the must- structures with no entry point
        MMinus.forEach((t, marked) -> {
            if (!marked) {
                Optional<ConcreteState> optionalCPrime = connectedJSCATS.getAlpha().keySet().stream().filter(concreteState -> connectedJSCATS.getAlpha().get(concreteState).equals(t.getTarget())).findFirst();
                if (optionalCPrime.isPresent()) {
                    ConcreteState cPrime = optionalCPrime.get();
                    mustMinusConcretization(t, cPrime, machine, connectedJSCATS);
                }
            }
        });
        // Time measurement
        endTime = System.nanoTime();
        computationTime = (1.0 * endTime - startTime) / 1000000000;
        return new JSCATS(new LinkedHashSet<>(connectedJSCATS.getQ()), new LinkedHashSet<>(connectedJSCATS.getQ0()), new LinkedHashSet<>(connectedJSCATS.getC()), new LinkedHashSet<>(connectedJSCATS.getIc0()), new LinkedHashSet<>(connectedJSCATS.getDelta()), new LinkedHashSet<>(connectedJSCATS.getDeltaPlus()), new LinkedHashSet<>(connectedJSCATS.getDeltaMinus()), new LinkedHashMap<>(connectedJSCATS.getAlpha()), new LinkedHashMap<>(connectedJSCATS.getKappa()), new LinkedHashSet<>(connectedJSCATS.getDeltaC()), computationTime);
    }

    private static void mustPlusConcretization(AbstractTransition t, ConcreteState c, Machine machine, JSCATS abstraction) {
        Set<ConcreteTransition> TC = new LinkedHashSet<>();
        ConcreteTransition tc;
        abstraction.getAlpha().keySet().stream().filter(concreteState -> abstraction.getAlpha().get(concreteState).equals(t.getTarget())).forEach(concreteState -> TC.addAll(abstraction.getDeltaC().stream().filter(concreteTransition -> concreteTransition.getSource().equals(c) && concreteTransition.getEvent().getName().equals(t.getEvent().getName()) && concreteTransition.getTarget().equals(concreteState)).collect(Collectors.toList())));
        if (!TC.isEmpty()) {
            tc = TC.iterator().next();
        } else {
            Z3 z3 = new Z3();
            z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(
                    (ABooleanExpression) machine.getInvariant().prime(true),
                    new Exists(
                            new And(
                                    machine.getInvariant(),
                                    t.getEvent().getSubstitution().getPrd(machine),
                                    c.getExpression()
                            ),
                            machine.getVariables().toArray(new Variable[machine.getVariables().size()])
                    ),
                    (ABooleanExpression) t.getTarget().prime()
            )));
            if (z3.checkSAT() == SATISFIABLE) {
                ConcreteState cPrime = ConcreteStateComputer.computeConcreteState("c_" + t.getTarget().getName(), z3.getModel(), false); //new ConcreteState("c_" + t.getTarget().getName(), getConcreteState(new EventBModel(machine, z3.getModel(), z3), false));
                if (abstraction.getC().add(cPrime)) {
                    abstraction.getAlpha().put(cPrime, t.getTarget());
                    abstraction.getKappa().put(cPrime, EStateColor.BLUE);
                }
                tc = new ConcreteTransition(c, t.getEvent(), cPrime);
                abstraction.getDeltaC().add(tc);
            } else {
                throw new Error("Impossible in mustPlusConcretization");
            }
        }
        MPlus.put(t, true);
        MPlus.forEach((t2, marked) -> {
            if (!marked && t2.getSource().equals(t.getTarget())) {
                mustPlusConcretization(t2, tc.getTarget(), machine, abstraction);
            }
        });
    }

    private static void mustMinusConcretization(AbstractTransition t, ConcreteState cPrime, Machine machine, JSCATS abstraction) {
        Set<ConcreteTransition> TC = new LinkedHashSet<>();
        ConcreteTransition tc;
        abstraction.getAlpha().keySet().stream().filter(concreteState -> abstraction.getAlpha().get(concreteState).equals(t.getSource())).forEach(concreteState -> TC.addAll(abstraction.getDeltaC().stream().filter(concreteTransition -> concreteTransition.getSource().equals(concreteState) && concreteTransition.getEvent().getName().equals(t.getEvent().getName()) && concreteTransition.getTarget().equals(cPrime)).collect(Collectors.toList())));
        if (!TC.isEmpty()) {
            tc = TC.iterator().next();
        } else {
            Z3 z3 = new Z3();
            z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(
                    machine.getInvariant(),
                    new Exists(
                            new And(
                                    (ABooleanExpression) machine.getInvariant().prime(true),
                                    (ABooleanExpression) cPrime.getExpression().prime(),
                                    t.getEvent().getSubstitution().getPrd(machine)
                            ),
                            machine.getVariables().stream().map(variable -> (Variable) variable.prime()).toArray(Variable[]::new)
                    ),
                    t.getSource()
            )));
            if (z3.checkSAT() == SATISFIABLE) {
                ConcreteState c = ConcreteStateComputer.computeConcreteState("c_" + t.getSource().getName(), z3.getModel(), true); //new ConcreteState("c_" + t.getSource().getName(), getConcreteState(new EventBModel(machine, z3.getModel(), z3), true));
                if (abstraction.getC().add(c)) {
                    abstraction.getAlpha().put(c, t.getSource());
                    abstraction.getKappa().put(c, EStateColor.BLUE);
                }
                tc = new ConcreteTransition(c, t.getEvent(), cPrime);
                abstraction.getDeltaC().add(tc);
            } else {
                throw new Error("Impossible in mustMinusConcretization");
            }
        }
        MMinus.put(t, true);
        MMinus.forEach((t2, marked) -> {
            if (!marked && t2.getTarget().equals(t.getSource())) {
                mustMinusConcretization(t2, tc.getSource(), machine, abstraction);
            }
        });
    }

    private static boolean isMustPlusStructureEntryPoint(AbstractTransition t, JSCATS abstraction) {
        return abstraction.getDeltaPlus().stream().noneMatch(abstractTransition -> t.getSource().equals(abstractTransition.getTarget()));
    }

    private static boolean isMustMinusStructureEntryPoint(AbstractTransition t, JSCATS abstraction) {
        return abstraction.getDeltaMinus().stream().noneMatch(abstractTransition -> t.getTarget().equals(abstractTransition.getSource()));
    }

    public Machine getMachine() {
        return machine;
    }

    public JSCATS getAbstraction() {
        return abstraction;
    }

}

