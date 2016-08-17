package algorithms.eua;

import algorithms.tools.ConcreteStateComputer;
import algorithms.tools.EStateColor;
import algorithms.tools.ModalityChecker;
import com.microsoft.z3.Status;
import eventb.Event;
import eventb.Machine;
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
        Set<ConcreteState> RC = new LinkedHashSet<>();
        Set<ConcreteState> GC = new LinkedHashSet<>();
        Set<ConcreteState> BC = new LinkedHashSet<>();
        // Step 1: Computation of one concrete instance of each initial abstract state
        Z3 z3 = new Z3();
        for (AbstractState q : abstractStates) {
            z3.reset();
            z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(machine.getInitialization().getSP(new True(), machine), (ABooleanExpression) q.prime())));
            if (z3.checkSAT() == Status.SATISFIABLE) {
                ConcreteState c = ConcreteStateComputer.computeConcreteState("c_" + q.getName(), z3.getModel(), false);
                System.out.println(c);
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
                        ConcreteState witness = ConcreteStateComputer.computeConcreteState("c_" + q.getName(), nc.getSecond(), true);
                        if (!C.contains(witness)) {
                            C.add(witness);
                            Alpha.put(witness, q);
                            Kappa.put(witness, EStateColor.BLUE);
                        }
                        GC.clear();
                        GC.addAll(Alpha.keySet().stream().filter(s -> Alpha.get(s).equals(q) && Kappa.get(s).equals(EStateColor.GREEN)).collect(Collectors.toList()));
                        ConcreteState cPrime;
                        z3.reset();
                        /*z3.getSolver().add((BoolExpr) new And(
                                (ABooleanExpression) machine.getInvariant().prime(true),
                                new Exists(
                                        new And(
                                                machine.getInvariant(),
                                                e.getSubstitution().getPrd(machine),
                                                new Or(GC.toArray(new ABooleanExpression[GC.size()]))
                                        ),
                                        machine.getAssignables().toArray(new AAssignable[machine.getAssignables().size()])
                                ),
                                (ABooleanExpression) qPrime.getExpression().prime()
                        ).accept(new ToZ3Transformer(machine, z3)));*/
                        z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(
                                (ABooleanExpression) machine.getInvariant().prime(true),
                                e.getSubstitution().getSP(new Or(GC.toArray(new ABooleanExpression[GC.size()])), machine),
                                (ABooleanExpression) qPrime.getExpression().prime()
                        )));
                        Status cPrimeSat = z3.checkSAT();
                        if (cPrimeSat != Status.SATISFIABLE) {
                            z3.reset();
                            /*z3.getSolver().add((BoolExpr) new And(
                                    (ABooleanExpression) machine.getInvariant().prime(true),
                                    new Exists(
                                            new And(
                                                    machine.getInvariant(),
                                                    e.getSubstitution().getPrd(machine),
                                                    witness
                                            ),
                                            machine.getAssignables().toArray(new AAssignable[machine.getAssignables().size()])
                                    ),
                                    (ABooleanExpression) qPrime.prime()
                            ).accept(new ToZ3Transformer(machine, z3)));*/
                            z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(
                                    (ABooleanExpression) machine.getInvariant().prime(true),
                                    e.getSubstitution().getSP(witness, machine),
                                    (ABooleanExpression) qPrime.getExpression().prime()
                            )));
                            z3.checkSAT();
                            cPrime = ConcreteStateComputer.computeConcreteState("c_" + qPrime.getName(), z3.getModel(), false);
                            if (C.add(cPrime)) {
                                Alpha.put(cPrime, qPrime);
                                Kappa.put(cPrime, EStateColor.BLUE);
                            }
                            DeltaC.add(new ConcreteTransition(witness, e, cPrime));
                        } else {
                            BC.clear();
                            BC.addAll(Alpha.keySet().stream().filter(sPrime -> Alpha.get(sPrime).equals(qPrime) && Kappa.get(sPrime).equals(EStateColor.BLUE)).collect(Collectors.toList()));
                            z3.reset();
                            /*z3.getSolver().add((BoolExpr) new And(
                                    (ABooleanExpression) machine.getInvariant().prime(true),
                                    new Exists(
                                            new And(
                                                    machine.getInvariant(),
                                                    e.getSubstitution().getPrd(machine),
                                                    new Or(GC.toArray(new ABooleanExpression[GC.size()]))
                                            ),
                                            machine.getAssignables().toArray(new AAssignable[machine.getAssignables().size()])
                                    ),
                                    new Or(BC.stream().map(concreteState -> (ABooleanExpression) concreteState.prime()).toArray(ABooleanExpression[]::new))
                            ).accept(new ToZ3Transformer(machine, z3)));*/
                            z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(
                                    (ABooleanExpression) machine.getInvariant().prime(true),
                                    e.getSubstitution().getSP(new Or(GC.toArray(new ABooleanExpression[GC.size()])), machine),
                                    new Or(BC.stream().map(concreteState -> (ABooleanExpression) concreteState.prime()).toArray(ABooleanExpression[]::new))
                            )));
                            cPrimeSat = z3.checkSAT();
                            if (cPrimeSat == Status.SATISFIABLE) {
                                cPrime = ConcreteStateComputer.computeConcreteState("c_" + qPrime.getName(), z3.getModel(), false);
                                z3.reset();
                                /*z3.getSolver().add((BoolExpr) new And(
                                        machine.getInvariant(),
                                        (ABooleanExpression) machine.getInvariant().prime(true),
                                        e.getSubstitution().getPrd(machine),
                                        (ABooleanExpression) cPrime.prime(),
                                        new Or(GC.toArray(new ABooleanExpression[GC.size()]))
                                ).accept(new ToZ3Transformer(machine, z3)));*/
                                z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(
                                        machine.getInvariant(),
                                        (ABooleanExpression) machine.getInvariant().prime(true),
                                        e.getSubstitution().getWCP((ABooleanExpression) cPrime.prime()),
                                        new Or(GC.toArray(new ABooleanExpression[GC.size()]))
                                )));
                                z3.checkSAT();
                                ConcreteState c = ConcreteStateComputer.computeConcreteState("c_" + q.getName(), z3.getModel(), true);
                                DeltaC.add(new ConcreteTransition(c, e, cPrime));
                                Kappa.put(cPrime, EStateColor.GREEN);
                                C.add(cPrime);
                            }
                            z3.reset();
                            /*z3.getSolver().add((BoolExpr) new And(
                                    (ABooleanExpression) machine.getInvariant().prime(true),
                                    new Exists(
                                            new And(
                                                    machine.getInvariant(),
                                                    e.getSubstitution().getPrd(machine),
                                                    new Or(GC.toArray(new ABooleanExpression[GC.size()]))
                                            ),
                                            machine.getAssignables().toArray(new AAssignable[machine.getAssignables().size()])
                                    ),
                                    new Not(new Or(BC.stream().map(concreteState -> (ABooleanExpression) concreteState.prime()).toArray(ABooleanExpression[]::new))),
                                    (ABooleanExpression) qPrime.prime()
                            ).accept(new ToZ3Transformer(machine, z3)));*/
                            z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(
                                    (ABooleanExpression) machine.getInvariant().prime(true),
                                    e.getSubstitution().getSP(new Or(GC.toArray(new ABooleanExpression[GC.size()])), machine),
                                    new Not(new Or(BC.stream().map(concreteState -> (ABooleanExpression) concreteState.prime()).toArray(ABooleanExpression[]::new)))
                            )));
                            cPrimeSat = z3.checkSAT();
                            if (cPrimeSat == Status.SATISFIABLE) {
                                cPrime = ConcreteStateComputer.computeConcreteState("c_" + qPrime.getName(), z3.getModel(), false);
                                z3.reset();
                                /*z3.getSolver().add((BoolExpr) new And(
                                        machine.getInvariant(),
                                        (ABooleanExpression) machine.getInvariant().prime(true),
                                        e.getSubstitution().getPrd(machine),
                                        (ABooleanExpression) cPrime.prime(),
                                        new Or(GC.toArray(new ABooleanExpression[GC.size()]))
                                ).accept(new ToZ3Transformer(machine, z3)));*/
                                z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(
                                        machine.getInvariant(),
                                        (ABooleanExpression) machine.getInvariant().prime(true),
                                        e.getSubstitution().getWCP((ABooleanExpression) cPrime.prime()),
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
        DeltaC.forEach(System.out::println);
        return new JSCATS(Q, Q0, C, IC0, Delta, DeltaPlus, DeltaMinus, Alpha, Kappa, DeltaC);
    }

}
