package eventb;

import eventb.expressions.FunctionDefinition;
import eventb.expressions.arith.*;
import eventb.expressions.bool.ABooleanExpression;
import eventb.expressions.bool.And;
import eventb.expressions.bool.Equals;
import eventb.expressions.bool.True;
import eventb.expressions.sets.CustomSet;
import eventb.expressions.sets.NamedSet;
import eventb.expressions.sets.RangeSet;
import eventb.substitutions.*;
import eventb.tools.formatters.EventBFormatter;
import org.junit.Assert;
import org.junit.Test;
import utilities.UCharacters;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by gvoiron on 03/08/16.
 * Time : 23:49
 */
public class MachineTest {

    @Test
    public void test_getName() {
        String name = "name";
        Machine machine = new Machine(name, null, null, null, null, null);
        Assert.assertEquals(name, machine.getName());
    }

    @Test
    public void test_acceptEventBFormatter() {
        String name = "name";
        List<NamedSet> namedSets = Arrays.asList(new NamedSet("set1", new Int(0), new Int(42), new Int(64)), new NamedSet("set2", new Int(42), new Int(0)));
        List<AAssignable> assignables = Arrays.asList(new Variable("v1"), new Variable("v2"), new Variable("v3"));
        ABooleanExpression invariant = new And(new True(), new GreaterOrEqual(new Variable("v1"), new Int(42)), new Equals(new Variable("v2"), new Variable("v3")));
        ASubstitution initialization = new MultipleAssignment(new Assignment(new Variable("v1"), new Variable("v2")), new Assignment(new Variable("v2"), new Int(42)));
        Event event1 = new Event("event1", new Skip());
        Event event2 = new Event("event2", new Select(new Equals(new Variable("v1"), new Int(42)), new Assignment(new Variable("v1"), new Subtraction(new Variable("v1"), new Int(1)))));
        Event event3 = new Event("event3", new IfThenElse(new LowerThan(new Variable("v1"), new Int(42)), new MultipleAssignment(new Assignment(new Variable("v1"), new Int(0)), new Assignment(new Variable("v2"), new Int(42))), new Skip()));
        List<Event> events = Arrays.asList(event1, event2, event3);
        Machine machine = new Machine(name, namedSets, assignables, invariant, initialization, events);
        Assert.assertEquals("MACHINE" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "name" + UCharacters.LINE_SEPARATOR + UCharacters.LINE_SEPARATOR + "SETS" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "(set1 : {0, 42, 64})" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "(set2 : {42, 0})" + UCharacters.LINE_SEPARATOR + UCharacters.LINE_SEPARATOR + "VARIABLES" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "v1," + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "v2," + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "v3" + UCharacters.LINE_SEPARATOR + UCharacters.LINE_SEPARATOR + "INVARIANT" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "(_true_ " + UCharacters.LAND + " (v1 " + UCharacters.GREATER_OR_EQUAL + " 42) " + UCharacters.LAND + " (v2 = v3))" + UCharacters.LINE_SEPARATOR + UCharacters.LINE_SEPARATOR + "INITIALIZATION" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "v1 := v2 ||" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "v2 := 42" + UCharacters.LINE_SEPARATOR + UCharacters.LINE_SEPARATOR + "EVENTS" + UCharacters.LINE_SEPARATOR + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "event1 " + UCharacters.EQ_DEF + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + UCharacters.TABULATION + "SKIP" + UCharacters.LINE_SEPARATOR + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "event2 " + UCharacters.EQ_DEF + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + UCharacters.TABULATION + "SELECT" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + UCharacters.TABULATION + UCharacters.TABULATION + "(v1 = 42)" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + UCharacters.TABULATION + "THEN" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + UCharacters.TABULATION + UCharacters.TABULATION + "v1 := (v1 - 1)" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + UCharacters.TABULATION + "END" + UCharacters.LINE_SEPARATOR + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "event3 " + UCharacters.EQ_DEF + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + UCharacters.TABULATION + "IF" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + UCharacters.TABULATION + UCharacters.TABULATION + "(v1 < 42)" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + UCharacters.TABULATION + "THEN" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + UCharacters.TABULATION + UCharacters.TABULATION + "v1 := 0 ||" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + UCharacters.TABULATION + UCharacters.TABULATION + "v2 := 42" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + UCharacters.TABULATION + "ELSE" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + UCharacters.TABULATION + UCharacters.TABULATION + "SKIP" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + UCharacters.TABULATION + "END" + UCharacters.LINE_SEPARATOR, machine.accept(new EventBFormatter()));
    }

    @Test
    public void test_getSets() {
        List<NamedSet> namedSets = Arrays.asList(new NamedSet("set1", new Int(0), new Int(42), new Int(64)), new NamedSet("set2", new Int(42), new Int(0)));
        Machine machine = new Machine(null, namedSets, null, null, null, null);
        Assert.assertEquals(namedSets, machine.getSets());
        namedSets = Collections.emptyList();
        machine = new Machine(null, namedSets, null, null, null, null);
        Assert.assertEquals(namedSets, machine.getSets());
    }

    @Test
    public void test_getAssignables() {
        List<AAssignable> assignables = Arrays.asList(new Variable("v1"), new Variable("v2"), new Variable("v3"));
        Machine machine = new Machine(null, null, assignables, null, null, null);
        Assert.assertEquals(assignables, machine.getAssignables());
    }

    @Test
    public void test_getVariables() {
        FunctionDefinition functionDefinition = new FunctionDefinition("fun", new CustomSet(new Int(0), new Int(3), new Int(4)), new RangeSet(new Int(0), new Int(3)));
        Variable v1 = new Variable("v1");
        Variable v2 = new Variable("v2");
        Variable v3 = new Variable("v3");
        FunctionCall functionCall = new FunctionCall(functionDefinition, v3);
        List<AAssignable> assignables = Arrays.asList(v1, functionCall, v2, v3);
        Machine machine = new Machine(null, null, assignables, null, null, null);
        Assert.assertEquals(Arrays.asList(v1, v2, v3), machine.getVariables());
    }

    @Test
    public void test_getFunctionCalls() {
        FunctionDefinition functionDefinition = new FunctionDefinition("fun", new CustomSet(new Int(0), new Int(3), new Int(4)), new RangeSet(new Int(0), new Int(3)));
        Variable v1 = new Variable("v1");
        Variable v2 = new Variable("v2");
        Variable v3 = new Variable("v3");
        FunctionCall functionCall = new FunctionCall(functionDefinition, v3);
        List<AAssignable> assignables = Arrays.asList(v1, functionCall, v2, v3);
        Machine machine = new Machine(null, null, assignables, null, null, null);
        Assert.assertEquals(Collections.singletonList(functionCall), machine.getFunctionCalls());
    }

    @Test
    public void test_getInvariant() {
        ABooleanExpression invariant = new And(new True(), new GreaterOrEqual(new Variable("v1"), new Int(42)), new Equals(new Variable("v2"), new Variable("v3")));
        Machine machine = new Machine(null, null, null, invariant, null, null);
        Assert.assertEquals(invariant, machine.getInvariant());
    }

    @Test
    public void test_getInitialization() {
        ASubstitution initialization = new MultipleAssignment(new Assignment(new Variable("v1"), new Variable("v2")), new Assignment(new Variable("v2"), new Int(42)));
        Machine machine = new Machine(null, null, null, null, initialization, null);
        Assert.assertEquals(initialization, machine.getInitialization());
    }

    @Test
    public void test_getEvents() {
        Event event1 = new Event("event1", new Skip());
        Event event2 = new Event("event2", new Select(new Equals(new Variable("v1"), new Int(42)), new Assignment(new Variable("v1"), new Subtraction(new Variable("v1"), new Int(1)))));
        Event event3 = new Event("event3", new IfThenElse(new LowerThan(new Variable("v1"), new Int(42)), new MultipleAssignment(new Assignment(new Variable("v1"), new Int(0)), new Assignment(new Variable("v2"), new Int(42))), new Skip()));
        List<Event> events = Arrays.asList(event1, event2, event3);
        Machine machine = new Machine(null, null, null, null, null, events);
        Assert.assertEquals(events, machine.getEvents());
    }

}