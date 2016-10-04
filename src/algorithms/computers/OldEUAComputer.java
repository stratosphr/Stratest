package algorithms.computers;

import algorithms.outputs.JSCATS;
import algorithms.tools.ConcreteStateComputer;
import algorithms.tools.EStateColor;
import algorithms.tools.ModalityChecker;
import com.microsoft.z3.Status;
import eventb.Event;
import eventb.Machine;
import eventb.expressions.arith.Variable;
import eventb.expressions.bool.ABooleanExpression;
import eventb.expressions.bool.And;
import eventb.expressions.bool.Exists;
import eventb.expressions.bool.Or;
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
 * Created by gvoiron on 06/09/16.
 * Time : 09:42
 */
public final class OldEUAComputer implements IComputer<JSCATS> {

    private final Machine machine;
    private final List<AbstractState> abstractStates;

    public OldEUAComputer(Machine machine, List<AbstractState> abstractStates) {
        this.machine = machine;
        this.abstractStates = abstractStates;
    }

    @Override
    public JSCATS compute() {
        // Time measurement
        long startTime;
        long endTime;
        double computationTime;
        startTime = System.nanoTime();
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
        Set<ConcreteState> CR = new LinkedHashSet<>();
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
            }
        }
        C = new LinkedHashSet<>(IC0);
        // Step 2: Computation of the reachable states, the transitions in delta, delta+, delta-
        RQ.addAll(Q0);
        while (!RQ.isEmpty()) {
            AbstractState q = RQ.iterator().next();
            RQ.remove(q);
            Q.add(q);
            for (AbstractState qPrime : orderStates(abstractStates, q)) {
                for (Event e : orderEvents(machine.getEvents())) {
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
                        CR.clear();
                        CR.addAll(Alpha.keySet().stream().filter(concreteState -> Alpha.get(concreteState).equals(q)).collect(Collectors.toList()));
                        ConcreteState cPrime;
                        z3.reset();
                        z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And((ABooleanExpression) machine.getInvariant().prime(true), new Exists(new And(machine.getInvariant(), e.getSubstitution().getPrd(machine), new Or(CR.toArray(new ABooleanExpression[CR.size()]))), machine.getVariables().toArray(new Variable[machine.getAssignables().size()])), (ABooleanExpression) qPrime.getExpression().prime())));
                        Status cPrimeSAT = z3.checkSAT();
                        if (cPrimeSAT == Status.SATISFIABLE) {
                            cPrime = ConcreteStateComputer.computeConcreteState("c_" + qPrime.getName(), z3.getModel(), false);
                            z3.reset();
                            z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(machine.getInvariant(), (ABooleanExpression) machine.getInvariant().prime(true), e.getSubstitution().getPrd(machine), (ABooleanExpression) cPrime.prime(), new Or(CR.toArray(new ABooleanExpression[CR.size()])))));
                            z3.checkSAT();
                            ConcreteState c = ConcreteStateComputer.computeConcreteState("c_" + q.getName(), z3.getModel(), true);
                            DeltaC.add(new ConcreteTransition(c, e, cPrime));
                            if (C.add(cPrime)) {
                                Alpha.put(cPrime, qPrime);
                            }
                        } else {
                            z3.reset();
                            z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And((ABooleanExpression) machine.getInvariant().prime(true), new Exists(new And(machine.getInvariant(), e.getSubstitution().getPrd(machine), witness), machine.getVariables().toArray(new Variable[machine.getAssignables().size()])), (ABooleanExpression) qPrime.prime())));
                            z3.checkSAT();
                            cPrime = ConcreteStateComputer.computeConcreteState("c_" + qPrime.getName(), z3.getModel(), false);
                            Alpha.put(cPrime, qPrime);
                            DeltaC.add(new ConcreteTransition(witness, e, cPrime));
                        }
                        if (C.add(witness)) {
                            Alpha.put(witness, q);
                        }
                        if (!Q.contains(qPrime)) {
                            RQ.add(qPrime);
                        }
                    }
                }
            }
        }
        // Time measurement
        endTime = System.nanoTime();
        computationTime = (1.0 * endTime - startTime) / 1000000000;
        return new JSCATS(Q, Q0, C, IC0, Delta, DeltaPlus, DeltaMinus, Alpha, Kappa, DeltaC, computationTime);
    }

    private List<AbstractState> orderStates(List<AbstractState> abstractStates, AbstractState q) {
        /*Set<AbstractState> sortedAbstractStates = new LinkedHashSet<>();
        sortedAbstractStates.add(q);
        sortedAbstractStates.addAll(abstractStates);
        return new ArrayList<>(sortedAbstractStates);*/
        return abstractStates;
    }

    private List<Event> orderEvents(List<Event> events) {
        List<Event> orderedEvents = new ArrayList<>(events);
        Collections.sort(orderedEvents);
        return orderedEvents;
    }

    public Machine getMachine() {
        return machine;
    }

    public List<AbstractState> getAbstractStates() {
        return abstractStates;
    }

}
