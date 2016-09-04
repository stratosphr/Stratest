package algorithms.computers;

import algorithms.outputs.JSCATS;
import algorithms.tools.*;
import com.microsoft.z3.Status;
import eventb.Event;
import eventb.Machine;
import eventb.expressions.arith.Variable;
import eventb.expressions.bool.*;
import eventb.tools.formatters.ExpressionToSMTLib2Formatter;
import graphs.AbstractState;
import graphs.AbstractTransition;
import graphs.ConcreteState;
import graphs.ConcreteTransition;
import solvers.z3.Model;
import solvers.z3.Z3;
import utilities.UTuple;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 17/08/16.
 * Time : 10:33
 */
public final class EUAComputer implements IComputer<JSCATS> {

    private final Machine machine;
    private final List<AbstractState> abstractStates;
    private final IRelevancyChecker relevanceChecker;
    private final boolean useRelevanceChecker;
    private final boolean usePropagation;

    public EUAComputer(Machine machine, List<AbstractState> abstractStates) {
        this(machine, abstractStates, false);
    }

    public EUAComputer(Machine machine, List<AbstractState> abstractStates, boolean usePropagation) {
        this.machine = machine;
        this.abstractStates = abstractStates;
        this.useRelevanceChecker = false;
        this.relevanceChecker = (concreteState, computedConcreteStates, concreteStatesColors) -> false;
        this.usePropagation = usePropagation;
    }

    public EUAComputer(Machine machine, List<AbstractState> abstractStates, IRelevancyChecker relevanceChecker) {
        this(machine, abstractStates, relevanceChecker, false);
    }

    public EUAComputer(Machine machine, List<AbstractState> abstractStates, IRelevancyChecker relevanceChecker, boolean usePropagation) {
        this.machine = machine;
        this.abstractStates = abstractStates;
        this.useRelevanceChecker = true;
        this.relevanceChecker = relevanceChecker;
        this.usePropagation = usePropagation;
    }

    @Override
    public JSCATS compute() {
        // Step 0: Variables declaration
        Set<AbstractState> Q = new LinkedHashSet<>();
        Set<AbstractState> Q0 = new LinkedHashSet<>();
        Set<ConcreteState> C;
        Set<ConcreteState> IC0 = new LinkedHashSet<>();
        Set<AbstractTransition> Delta = new LinkedHashSet<>();
        Set<AbstractTransition> DeltaPlus = new LinkedHashSet<>();
        Set<AbstractTransition> DeltaMinus = new LinkedHashSet<>();
        Map<ConcreteState, AbstractState> Alpha = new LinkedHashMap<>();
        Map<ConcreteState, EStateColor> Kappa = new LinkedHashMap<>();
        Set<ConcreteTransition> DeltaC = new LinkedHashSet<>();
        Set<AbstractState> RQ = new LinkedHashSet<>();
        Set<ConcreteState> RC = new LinkedHashSet<>();
        Set<ConcreteState> GC = new LinkedHashSet<>();
        Set<ConcreteState> BC = new LinkedHashSet<>();
        // Step 1: Computation of one concrete instance of each initial abstract state
        Z3 z3 = new Z3();
        for (AbstractState q : getAbstractStates()) {
            z3.reset();
            z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And((ABooleanExpression) machine.getInvariant().prime(true), new Exists(new And(machine.getInvariant(), machine.getInitialization().getPrd(machine)), machine.getVariables().toArray(new Variable[machine.getAssignables().size()])), (ABooleanExpression) q.prime())));
            if (z3.checkSAT() == Status.SATISFIABLE) {
                ConcreteState c = ConcreteStateComputer.computeConcreteState("c_" + q.getName(), z3.getModel(), false);
                Q0.add(q);
                IC0.add(c);
                Alpha.put(c, q);
                Kappa.put(c, EStateColor.GREEN);
                if (isUseRelevanceChecker()) {
                    RC.add(c);
                }
            }
        }
        C = new LinkedHashSet<>(IC0);
        // Step 2: Computation of the reachable states, the transitions in delta, delta+, delta-
        RQ.addAll(Q0);
        while (!RQ.isEmpty()) {
            AbstractState q = RQ.iterator().next();
            RQ.remove(q);
            Set<AbstractState> sortedAbstractStates = new LinkedHashSet<>();
            sortedAbstractStates.add(q);
            sortedAbstractStates.addAll(getAbstractStates());
            Q.add(q);
            for (AbstractState qPrime : sortedAbstractStates) {
                for (Event e : machine.getEvents()) {
                    UTuple<Boolean, Model> nc = new ModalityChecker(machine).isMayWithModel(new AbstractTransition(q, e, qPrime));
                    if (nc.getFirst()) {
                        Delta.add(new AbstractTransition(q, e, qPrime));
                        if (new ModalityChecker(machine).isMustMinus(new AbstractTransition(q, e, qPrime))) {
                            DeltaMinus.add(new AbstractTransition(q, e, qPrime));
                        }
                        if (new ModalityChecker(machine).isMustPlus(new AbstractTransition(q, e, qPrime))) {
                            DeltaPlus.add(new AbstractTransition(q, e, qPrime));
                        }
                        ConcreteState witness = ConcreteStateComputer.computeConcreteState("c_" + q.getName(), nc.getSecond(), true);
                        if (C.add(witness)) {
                            Alpha.put(witness, q);
                            Kappa.put(witness, EStateColor.BLUE);
                        }
                        GC.clear();
                        GC.addAll(Alpha.keySet().stream().filter(concreteState -> Alpha.get(concreteState).equals(q) && Kappa.get(concreteState).equals(EStateColor.GREEN)).collect(Collectors.toList()));
                        ConcreteState cPrime;
                        z3.reset();
                        z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And((ABooleanExpression) machine.getInvariant().prime(true), new Exists(new And(machine.getInvariant(), e.getSubstitution().getPrd(machine), new Or(GC.toArray(new ABooleanExpression[GC.size()]))), machine.getVariables().toArray(new Variable[machine.getAssignables().size()])), (ABooleanExpression) qPrime.getExpression().prime())));
                        Status cPrimeSAT = z3.checkSAT();
                        if (cPrimeSAT != Status.SATISFIABLE) {
                            z3.reset();
                            z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And((ABooleanExpression) machine.getInvariant().prime(true), new Exists(new And(machine.getInvariant(), e.getSubstitution().getPrd(machine), witness), machine.getVariables().toArray(new Variable[machine.getAssignables().size()])), (ABooleanExpression) qPrime.prime())));
                            z3.checkSAT();
                            cPrime = ConcreteStateComputer.computeConcreteState("c_" + qPrime.getName(), z3.getModel(), false);
                            Alpha.put(cPrime, qPrime);
                            Kappa.put(cPrime, Kappa.get(witness));
                            DeltaC.add(new ConcreteTransition(witness, e, cPrime));
                        } else {
                            BC.clear();
                            BC.addAll(Alpha.keySet().stream().filter(concreteState -> Alpha.get(concreteState).equals(qPrime) && Kappa.get(concreteState).equals(EStateColor.BLUE)).collect(Collectors.toList()));
                            z3.reset();
                            z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And((ABooleanExpression) machine.getInvariant().prime(true), new Exists(new And(machine.getInvariant(), e.getSubstitution().getPrd(machine), new Or(GC.toArray(new ABooleanExpression[GC.size()]))), machine.getVariables().toArray(new Variable[machine.getAssignables().size()])), new Or(BC.stream().map(concreteState -> (ABooleanExpression) concreteState.prime()).toArray(ABooleanExpression[]::new)))));
                            cPrimeSAT = z3.checkSAT();
                            if (cPrimeSAT == Status.SATISFIABLE) {
                                cPrime = ConcreteStateComputer.computeConcreteState("c_" + qPrime.getName(), z3.getModel(), false);
                                z3.reset();
                                z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(machine.getInvariant(), (ABooleanExpression) machine.getInvariant().prime(true), e.getSubstitution().getPrd(machine), (ABooleanExpression) cPrime.prime(), new Or(GC.toArray(new ABooleanExpression[GC.size()])))));
                                z3.checkSAT();
                                ConcreteState c = ConcreteStateComputer.computeConcreteState("c_" + q.getName(), z3.getModel(), true);
                                DeltaC.add(new ConcreteTransition(c, e, cPrime));
                                Kappa.put(cPrime, EStateColor.GREEN);
                                if (isUsePropagation()) {
                                    propagate(cPrime, DeltaC, Kappa);
                                }
                                if (isUseRelevanceChecker() && getRelevanceChecker().isRelevant(cPrime, C, Kappa)) {
                                    RC.add(cPrime);
                                }
                            }
                            z3.reset();
                            z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And((ABooleanExpression) machine.getInvariant().prime(true), new Exists(new And(machine.getInvariant(), e.getSubstitution().getPrd(machine), new Or(GC.toArray(new ABooleanExpression[GC.size()]))), machine.getVariables().toArray(new Variable[machine.getAssignables().size()])), new Not(new Or(BC.stream().map(concreteState -> (ABooleanExpression) concreteState.prime()).toArray(ABooleanExpression[]::new))), (ABooleanExpression) qPrime.prime())));
                            cPrimeSAT = z3.checkSAT();
                            if (cPrimeSAT == Status.SATISFIABLE) {
                                cPrime = ConcreteStateComputer.computeConcreteState("c_" + qPrime.getName(), z3.getModel(), false);
                                z3.reset();
                                z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(machine.getInvariant(), (ABooleanExpression) machine.getInvariant().prime(true), e.getSubstitution().getPrd(machine), (ABooleanExpression) cPrime.prime(), new Or(GC.toArray(new ABooleanExpression[GC.size()])))));
                                z3.checkSAT();
                                ConcreteState c = ConcreteStateComputer.computeConcreteState("c_" + q.getName(), z3.getModel(), true);
                                DeltaC.add(new ConcreteTransition(c, e, cPrime));
                                Kappa.put(cPrime, EStateColor.GREEN);
                                if (C.add(cPrime)) {
                                    Alpha.put(cPrime, qPrime);
                                    if (isUsePropagation()) {
                                        propagate(cPrime, DeltaC, Kappa);
                                    }
                                    if (isUseRelevanceChecker() && getRelevanceChecker().isRelevant(cPrime, C, Kappa)) {
                                        RC.add(cPrime);
                                    }
                                }
                            }
                        }
                    }
                }
                if (!Q.contains(qPrime)) {
                    RQ.add(qPrime);
                }
            }
        }
        // Step 3: Concretization of transition with relevant concrete states as source
        if (isUseRelevanceChecker()) {
            while (!RC.isEmpty()) {
                ConcreteState c = RC.iterator().next();
                RC.remove(c);
                for (Event e : machine.getEvents()) {
                    z3.reset();
                    z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(
                            (ABooleanExpression) machine.getInvariant().prime(true),
                            new Exists(
                                    new And(
                                            machine.getInvariant(),
                                            e.getSubstitution().getPrd(machine),
                                            c
                                    ),
                                    machine.getVariables().toArray(new Variable[machine.getAssignables().size()])
                            )
                    )));
                    Status cPrimeSat = z3.checkSAT();
                    if (cPrimeSat == Status.SATISFIABLE) {
                        ABooleanExpression booleanExpression = ConcreteStateComputer.computeConcreteStateExpression(z3.getModel(), false);
                        AbstractState abstractState = AbstractStateComputer.computeAbstractState(booleanExpression, machine, getAbstractStates());
                        ConcreteState cPrime = new ConcreteState("c_" + abstractState.getName(), booleanExpression);
                        Kappa.put(cPrime, EStateColor.GREEN);
                        Alpha.put(cPrime, abstractState);
                        DeltaC.add(new ConcreteTransition(c, e, cPrime));
                        if (isUsePropagation()) {
                            propagate(cPrime, DeltaC, Kappa);
                        }
                        if (getRelevanceChecker().isRelevant(cPrime, C, Kappa)) {
                            RC.add(cPrime);
                        }
                        C.add(cPrime);
                    }
                }
            }
        }
        return new JSCATS(Q, Q0, C, IC0, Delta, DeltaPlus, DeltaMinus, Alpha, Kappa, DeltaC);
    }

    private static void propagate(ConcreteState c, Set<ConcreteTransition> deltaC, Map<ConcreteState, EStateColor> kappa) {
        deltaC.stream().filter(concreteTransition -> concreteTransition.getSource().equals(c) && kappa.get(concreteTransition.getTarget()) == EStateColor.BLUE).forEach(concreteTransition -> {
            System.out.println("Propagation");
            kappa.put(concreteTransition.getTarget(), EStateColor.GREEN);
            propagate(concreteTransition.getTarget(), deltaC, kappa);
        });
    }

    public Machine getMachine() {
        return machine;
    }

    public List<AbstractState> getAbstractStates() {
        return abstractStates;
    }

    public IRelevancyChecker getRelevanceChecker() {
        return relevanceChecker;
    }

    public boolean isUseRelevanceChecker() {
        return useRelevanceChecker;
    }

    public boolean isUsePropagation() {
        return usePropagation;
    }

}
