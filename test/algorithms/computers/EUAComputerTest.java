package algorithms.computers;

import algorithms.outputs.JSCATS;
import algorithms.tools.AbstractStatesComputer;
import com.microsoft.z3.Status;
import eventb.Machine;
import eventb.expressions.arith.Int;
import eventb.expressions.arith.Variable;
import eventb.expressions.bool.*;
import eventb.parsers.EBMParser;
import eventb.tools.formatters.ExpressionToSMTLib2Formatter;
import graphs.AbstractState;
import graphs.ConcreteState;
import org.junit.Assert;
import org.junit.Test;
import parser.noeud.AfterParserException;
import parser.noeud.BParserException;
import solvers.z3.Z3;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 17/08/16.
 * Time : 11:32
 */
public class EUAComputerTest {

    @Test
    public void test_computeEUA() throws Exception {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/threeBatteries/threeBatteries.ebm"));
        Int zero = new Int(0);
        Variable h = new Variable("h");
        Variable bat1 = new Variable("bat1");
        Variable bat2 = new Variable("bat2");
        Variable bat3 = new Variable("bat3");
        Predicate p0 = new Predicate("p0", new Equals(h, zero));
        Predicate p1 = new Predicate("p1", new Or(new And(new Equals(bat1, zero), new Equals(bat2, zero)), new And(new Equals(bat2, zero), new Equals(bat3, zero)), new And(new Equals(bat1, zero), new Equals(bat3, zero))));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, Arrays.asList(p0, p1));
        AbstractState q0 = new AbstractState("q0", new And(new Not(p0), new Not(p1)));
        AbstractState q1 = new AbstractState("q1", new And(new Not(p0), p1));
        AbstractState q2 = new AbstractState("q2", new And(p0, new Not(p1)));
        AbstractState q3 = new AbstractState("q3", new And(p0, p1));
        JSCATS eua = new EUAComputer(machine, abstractStates).compute();
        List<ConcreteState> q0Concretization = eua.getAlpha().keySet().stream().filter(concreteState -> eua.getAlpha().get(concreteState).equals(q0)).collect(Collectors.toList());
        List<ConcreteState> q1Concretization = eua.getAlpha().keySet().stream().filter(concreteState -> eua.getAlpha().get(concreteState).equals(q1)).collect(Collectors.toList());
        List<ConcreteState> q2Concretization = eua.getAlpha().keySet().stream().filter(concreteState -> eua.getAlpha().get(concreteState).equals(q2)).collect(Collectors.toList());
        List<ConcreteState> q3Concretization = eua.getAlpha().keySet().stream().filter(concreteState -> eua.getAlpha().get(concreteState).equals(q3)).collect(Collectors.toList());
        Assert.assertTrue(eua.getAlpha().containsValue(q0));
        Assert.assertTrue(eua.getAlpha().containsValue(q1));
        Assert.assertTrue(eua.getAlpha().containsValue(q2));
        Assert.assertTrue(eua.getAlpha().containsValue(q3));
        Assert.assertFalse(q0Concretization.isEmpty());
        Assert.assertFalse(q1Concretization.isEmpty());
        Assert.assertFalse(q2Concretization.isEmpty());
        Assert.assertFalse(q3Concretization.isEmpty());
        Assert.assertEquals(eua.getC().size(), eua.getAlpha().size());
        Assert.assertEquals(eua.getC().size(), eua.getKappa().size());
        Z3 z3 = new Z3();
        q0Concretization.forEach(concreteState -> {
            z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(machine.getInvariant(), (ABooleanExpression) machine.getInvariant().prime(true), concreteState, q0)));
            Assert.assertEquals(Status.SATISFIABLE, z3.checkSAT());
            z3.reset();
        });
        q1Concretization.forEach(concreteState -> {
            z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(machine.getInvariant(), (ABooleanExpression) machine.getInvariant().prime(true), concreteState, q1)));
            Assert.assertEquals(Status.SATISFIABLE, z3.checkSAT());
            z3.reset();
        });
        q2Concretization.forEach(concreteState -> {
            z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(machine.getInvariant(), (ABooleanExpression) machine.getInvariant().prime(true), concreteState, q2)));
            Assert.assertEquals(Status.SATISFIABLE, z3.checkSAT());
            z3.reset();
        });
        q3Concretization.forEach(concreteState -> {
            z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(machine.getInvariant(), (ABooleanExpression) machine.getInvariant().prime(true), concreteState, q3)));
            Assert.assertEquals(Status.SATISFIABLE, z3.checkSAT());
            z3.reset();
        });
    }

    @Test
    public void test_frontWiper1guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("/home/gvoiron/IdeaProjects/stratest/resources/eventb/frontWiper/frontWiper.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/frontWiper/frontWiper_1guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        JSCATS eua = new EUAComputer(machine, abstractStates).compute();
    }

}