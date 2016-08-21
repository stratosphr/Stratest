package algorithms;

import algorithms.outputs.JSCATS;
import algorithms.tools.ConcreteStateComputer;
import algorithms.tools.EStateColor;
import algorithms.tools.ModalityChecker;
import com.microsoft.z3.Status;
import eventb.Event;
import eventb.Machine;
import eventb.expressions.AExpression;
import eventb.expressions.bool.*;
import eventb.tools.formatters.ExpressionToSMTLib2Formatter;
import graphs.AbstractState;
import graphs.AbstractTransition;
import graphs.ConcreteState;
import graphs.ConcreteTransition;
import solvers.z3.Model;
import solvers.z3.Z3;
import utilities.UAUninstantiable;
import utilities.UTuple;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 17/08/16.
 * Time : 10:33
 */
public final class EUAComputer extends UAUninstantiable {

    public static JSCATS computeEUA(Machine machine, List<AbstractState> abstractStates) {
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
        Set<ConcreteState> GC = new LinkedHashSet<>();
        Set<ConcreteState> BC = new LinkedHashSet<>();
        // Step 1: Computation of one concrete instance of each initial abstract state
        Z3 z3 = new Z3();
        for (AbstractState q : abstractStates) {
            z3.reset();
            z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(machine.getInitialization().getSP(new True(), machine), (ABooleanExpression) q.prime())));
            if (z3.checkSAT() == Status.SATISFIABLE) {
                ConcreteState c = ConcreteStateComputer.computeConcreteState("c_" + q.getName(), z3.getModel(), false);
                Q0.add(q);
                IC0.add(c);
                Alpha.put(c, q);
                Kappa.put(c, EStateColor.GREEN);
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
            sortedAbstractStates.addAll(abstractStates);
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
                        ConcreteState witness = new ConcreteState("c_" + q.getName(), ConcreteStateComputer.computeConcreteState("", nc.getSecond(), true).getExpression());
                        if (C.add(witness)) {
                            Alpha.put(witness, q);
                            Kappa.put(witness, EStateColor.BLUE);
                        }
                        GC.clear();
                        GC.addAll(Alpha.keySet().stream().filter(concreteState -> Alpha.get(concreteState).equals(q) && Kappa.get(concreteState).equals(EStateColor.GREEN)).collect(Collectors.toList()));
                        ConcreteState cPrime;
                        z3.reset();
                        z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(
                                (ABooleanExpression) machine.getInvariant().prime(true),
                                e.getSubstitution().getSP(new Or(GC.toArray(new ConcreteState[GC.size()])), machine),
                                (ABooleanExpression) qPrime.prime()
                        )));
                        Status cPrimeSAT = z3.checkSAT();
                        if (cPrimeSAT != Status.SATISFIABLE) {
                            z3.reset();
                            z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(
                                    (ABooleanExpression) machine.getInvariant().prime(true),
                                    e.getSubstitution().getSP(witness, machine),
                                    (ABooleanExpression) qPrime.prime()
                            )));
                            z3.checkSAT();
                            cPrime = ConcreteStateComputer.computeConcreteState("c_" + qPrime.getName(), z3.getModel(), false);
                            Alpha.put(cPrime, qPrime);
                            Kappa.put(cPrime, Kappa.get(witness));
                            DeltaC.add(new ConcreteTransition(witness, e, cPrime));
                        } else {
                            BC.clear();
                            BC.addAll(Alpha.keySet().stream().filter(concreteState -> Alpha.get(concreteState).equals(qPrime) && Kappa.get(concreteState).equals(EStateColor.BLUE)).collect(Collectors.toList()));
                            z3.reset();
                            z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(
                                    (ABooleanExpression) machine.getInvariant().prime(true),
                                    e.getSubstitution().getSP(new Or(GC.toArray(new ABooleanExpression[GC.size()])), machine),
                                    new Or(BC.stream().map(AExpression::prime).toArray(ABooleanExpression[]::new))
                            )));
                            cPrimeSAT = z3.checkSAT();
                            if (cPrimeSAT == Status.SATISFIABLE) {
                                cPrime = ConcreteStateComputer.computeConcreteState("c_" + qPrime.getName(), z3.getModel(), false);
                                z3.reset();
                                z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(
                                        machine.getInvariant(),
                                        (ABooleanExpression) machine.getInvariant().prime(true),
                                        e.getSubstitution().getWCP(cPrime),
                                        q,
                                        new Or(GC.toArray(new ABooleanExpression[GC.size()]))
                                )));
                                z3.checkSAT();
                                ConcreteState c = ConcreteStateComputer.computeConcreteState("c_" + q.getName(), z3.getModel(), true);
                                DeltaC.add(new ConcreteTransition(c, e, cPrime));
                                Kappa.put(cPrime, EStateColor.GREEN);
                            }
                            z3.reset();
                            z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(
                                    (ABooleanExpression) machine.getInvariant().prime(true),
                                    e.getSubstitution().getSP(new Or(GC.toArray(new ABooleanExpression[GC.size()])), machine),
                                    new Not(new Or(BC.stream().map(AExpression::prime).toArray(ABooleanExpression[]::new))),
                                    (ABooleanExpression) qPrime.prime()
                            )));
                            cPrimeSAT = z3.checkSAT();
                            if (cPrimeSAT == Status.SATISFIABLE) {
                                cPrime = ConcreteStateComputer.computeConcreteState("c_" + qPrime.getName(), z3.getModel(), false);
                                z3.reset();
                                z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(
                                        machine.getInvariant(),
                                        (ABooleanExpression) machine.getInvariant().prime(true),
                                        e.getSubstitution().getWCP(cPrime),
                                        q,
                                        new Or(GC.toArray(new ABooleanExpression[GC.size()]))
                                )));
                                z3.checkSAT();
                                ConcreteState c = ConcreteStateComputer.computeConcreteState("c_" + q.getName(), z3.getModel(), true);
                                DeltaC.add(new ConcreteTransition(c, e, cPrime));
                                Kappa.put(cPrime, EStateColor.GREEN);
                                if (C.add(cPrime)) {
                                    Alpha.put(cPrime, qPrime);
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
        return new JSCATS(Q, Q0, C, IC0, Delta, DeltaPlus, DeltaMinus, Alpha, Kappa, DeltaC);
    }

}
