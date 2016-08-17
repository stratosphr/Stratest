package algorithms.tools;

import eventb.Machine;
import eventb.expressions.arith.Int;
import eventb.expressions.arith.Variable;
import eventb.expressions.bool.*;
import eventb.parsers.EBMParser;
import graphs.AbstractState;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

/**
 * Created by gvoiron on 17/08/16.
 * Time : 09:13
 */
public class AbstractStatesComputerTest {

    @Test
    public void test_computeAbstractStates() {
        Machine machine = (Machine) new EBMParser().parse(new File("resources/eventb/threeBatteries/threeBatteries.ebm"));
        Int zero = new Int(0);
        Variable h = new Variable("h");
        Variable bat1 = new Variable("bat1");
        Variable bat2 = new Variable("bat2");
        Variable bat3 = new Variable("bat3");
        Predicate p0 = new Predicate("p0", new Equals(h, zero));
        Predicate p1 = new Predicate("p1", new Or(new And(new Equals(bat1, zero), new Equals(bat2, zero)), new And(new Equals(bat2, zero), new Equals(bat3, zero)), new And(new Equals(bat1, zero), new Equals(bat3, zero))));
        AbstractState q0 = new AbstractState("q0", new And(new Not(p0), new Not(p1)));
        AbstractState q1 = new AbstractState("q1", new And(new Not(p0), p1));
        AbstractState q2 = new AbstractState("q2", new And(p0, new Not(p1)));
        AbstractState q3 = new AbstractState("q3", new And(p0, p1));
        Assert.assertEquals(Arrays.asList(q0, q1, q2, q3), AbstractStatesComputer.computeAbstractStates(machine, Arrays.asList(p0, p1)));
    }

    @Test
    public void test_computeAbstractStatesWithImpossibleCombinations() {
        Machine machine = (Machine) new EBMParser().parse(new File("resources/eventb/threeBatteries/threeBatteries.ebm"));
        Int zero = new Int(0);
        Variable h = new Variable("h");
        Variable bat1 = new Variable("bat1");
        Variable bat2 = new Variable("bat2");
        Variable bat3 = new Variable("bat3");
        Machine modifiedMachine = new Machine(
                machine.getName(),
                machine.getSets(),
                machine.getAssignables(),
                new And(new Equals(h, zero), machine.getInvariant()),
                machine.getInitialization(),
                machine.getEvents()
        );
        Predicate p0 = new Predicate("p0", new Equals(h, zero));
        Predicate p1 = new Predicate("p1", new Or(new And(new Equals(bat1, zero), new Equals(bat2, zero)), new And(new Equals(bat2, zero), new Equals(bat3, zero)), new And(new Equals(bat1, zero), new Equals(bat3, zero))));
        AbstractState q2 = new AbstractState("q2", new And(p0, new Not(p1)));
        AbstractState q3 = new AbstractState("q3", new And(p0, p1));
        Assert.assertEquals(Arrays.asList(q2, q3), AbstractStatesComputer.computeAbstractStates(modifiedMachine, Arrays.asList(p0, p1)));
    }

}