package algorithms.eua;

import algorithms.computers.EUAComputer;
import algorithms.computers.UUAComputer;
import algorithms.outputs.JSCATS;
import algorithms.tools.AbstractStatesComputer;
import eventb.Event;
import eventb.Machine;
import eventb.expressions.arith.Int;
import eventb.expressions.arith.Variable;
import eventb.expressions.bool.*;
import eventb.parsers.EBMParser;
import graphs.AbstractState;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 18/08/16.
 * Time : 11:08
 */
public class UUAComputerTest {

    @Test
    public void test_computeUniversalConcretisation() throws Exception {
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
        Event tic = machine.getEvents().stream().filter(event -> event.getName().equals("Tic")).collect(Collectors.toList()).get(0);
        Event commute = machine.getEvents().stream().filter(event -> event.getName().equals("Commute")).collect(Collectors.toList()).get(0);
        Event repair = machine.getEvents().stream().filter(event -> event.getName().equals("Repair")).collect(Collectors.toList()).get(0);
        Event fail = machine.getEvents().stream().filter(event -> event.getName().equals("Fail")).collect(Collectors.toList()).get(0);
        JSCATS abstraction = new EUAComputer(machine, abstractStates).compute();
        JSCATS JSCATS = new UUAComputer(machine, abstraction).compute();
    }

}
