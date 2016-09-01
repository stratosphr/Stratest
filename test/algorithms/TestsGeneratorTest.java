package algorithms;

import algorithms.computers.EUAComputer;
import algorithms.computers.UUAComputer;
import algorithms.outputs.JSCATS;
import algorithms.outputs.JSCATSStatisticsReporter;
import algorithms.tools.AbstractStatesComputer;
import algorithms.tools.TestsGenerator;
import eventb.Machine;
import eventb.expressions.arith.Int;
import eventb.expressions.arith.Variable;
import eventb.expressions.bool.And;
import eventb.expressions.bool.Equals;
import eventb.expressions.bool.Or;
import eventb.expressions.bool.Predicate;
import eventb.parsers.EBMParser;
import graphs.AbstractState;
import org.junit.Test;
import parser.noeud.AfterParserException;
import parser.noeud.BParserException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 18/08/16.
 * Time : 11:28
 */
public class TestsGeneratorTest {

    @Test
    public void test_testGeneration_simple() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/simple/simple.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/simple/simple_1.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        JSCATS eua = new EUAComputer(machine, abstractStates).compute();
        List<algorithms.outputs.Test> tests = TestsGenerator.generateTests(new UUAComputer(machine, eua));
        JSCATSStatisticsReporter JSCATSStatisticsReporter = new JSCATSStatisticsReporter(tests, machine, new ArrayList<>(abstractionPredicates), null);
    }

    @Test
    public void test_testGeneration_default() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/threeBatteries/threeBatteries.ebm"));
        Int zero = new Int(0);
        Variable h = new Variable("h");
        Variable bat1 = new Variable("bat1");
        Variable bat2 = new Variable("bat2");
        Variable bat3 = new Variable("bat3");
        Predicate p0 = new Predicate("p0", new Equals(h, zero));
        Predicate p1 = new Predicate("p1", new Or(new And(new Equals(bat1, zero), new Equals(bat2, zero)), new And(new Equals(bat2, zero), new Equals(bat3, zero)), new And(new Equals(bat1, zero), new Equals(bat3, zero))));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, Arrays.asList(p0, p1));
        JSCATS eua = new EUAComputer(machine, abstractStates).compute();
        List<algorithms.outputs.Test> tests = TestsGenerator.generateTests(new UUAComputer(machine, eua));
        JSCATSStatisticsReporter JSCATSStatisticsReporter = new JSCATSStatisticsReporter(tests, machine, Arrays.asList(p0, p1), null);
    }

    @Test
    public void test_threeBatteries1guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/threeBatteries/threeBatteries.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/threeBatteries/threeBatteries_1guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        JSCATS eua = new EUAComputer(machine, abstractStates).compute();
        List<algorithms.outputs.Test> tests = TestsGenerator.generateTests(new UUAComputer(machine, eua));
        JSCATSStatisticsReporter JSCATSStatisticsReporter = new JSCATSStatisticsReporter(tests, machine, new ArrayList<>(abstractionPredicates), null);
    }

    @Test
    public void test_threeBatteries2guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/threeBatteries/threeBatteries.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/threeBatteries/threeBatteries_2guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        JSCATS eua = new EUAComputer(machine, abstractStates).compute();
        List<algorithms.outputs.Test> tests = TestsGenerator.generateTests(new UUAComputer(machine, eua));
        JSCATSStatisticsReporter JSCATSStatisticsReporter = new JSCATSStatisticsReporter(tests, machine, new ArrayList<>(abstractionPredicates), null);
    }

    @Test
    public void test_threeBatteries1post() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/threeBatteries/threeBatteries.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/threeBatteries/threeBatteries_1post.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        JSCATS eua = new EUAComputer(machine, abstractStates).compute();
        List<algorithms.outputs.Test> tests = TestsGenerator.generateTests(new UUAComputer(machine, eua));
        JSCATSStatisticsReporter JSCATSStatisticsReporter = new JSCATSStatisticsReporter(tests, machine, new ArrayList<>(abstractionPredicates), null);
    }

    @Test
    public void test_phone1guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/phone/phone.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/phone/phone_1guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        JSCATS eua = new EUAComputer(machine, abstractStates).compute();
        List<algorithms.outputs.Test> tests = TestsGenerator.generateTests(new UUAComputer(machine, eua));
        JSCATSStatisticsReporter JSCATSStatisticsReporter = new JSCATSStatisticsReporter(tests, machine, new ArrayList<>(abstractionPredicates), null);
    }

    @Test
    public void test_phone2guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/phone/phone.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/phone/phone_2guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        JSCATS eua = new EUAComputer(machine, abstractStates).compute();
        List<algorithms.outputs.Test> tests = TestsGenerator.generateTests(new UUAComputer(machine, eua));
        JSCATSStatisticsReporter JSCATSStatisticsReporter = new JSCATSStatisticsReporter(tests, machine, new ArrayList<>(abstractionPredicates), null);
    }

    @Test
    public void test_phone1post() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/phone/phone.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/phone/phone_1post.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        JSCATS eua = new EUAComputer(machine, abstractStates).compute();
        List<algorithms.outputs.Test> tests = TestsGenerator.generateTests(new UUAComputer(machine, eua));
        JSCATSStatisticsReporter JSCATSStatisticsReporter = new JSCATSStatisticsReporter(tests, machine, new ArrayList<>(abstractionPredicates), null);
    }

    @Test
    public void test_phone2post() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/phone/phone.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/phone/phone_2post.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        JSCATS eua = new EUAComputer(machine, abstractStates).compute();
        List<algorithms.outputs.Test> tests = TestsGenerator.generateTests(new UUAComputer(machine, eua));
        JSCATSStatisticsReporter JSCATSStatisticsReporter = new JSCATSStatisticsReporter(tests, machine, new ArrayList<>(abstractionPredicates), null);
    }

    @Test
    public void test_carAlarmBug1guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/carAlarm/carAlarmBug.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/carAlarm/carAlarm_1guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        JSCATS eua = new EUAComputer(machine, abstractStates).compute();
        List<algorithms.outputs.Test> tests = TestsGenerator.generateTests(new UUAComputer(machine, eua));
        JSCATSStatisticsReporter JSCATSStatisticsReporter = new JSCATSStatisticsReporter(tests, machine, new ArrayList<>(abstractionPredicates), null);
    }

    @Test
    public void test_carAlarm1guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/carAlarm/carAlarm.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/carAlarm/carAlarm_1guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        JSCATS eua = new EUAComputer(machine, abstractStates).compute();
        List<algorithms.outputs.Test> tests = TestsGenerator.generateTests(new UUAComputer(machine, eua));
        JSCATSStatisticsReporter JSCATSStatisticsReporter = new JSCATSStatisticsReporter(tests, machine, new ArrayList<>(abstractionPredicates), null);
    }

    @Test
    public void test_carAlarm2guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/carAlarm/carAlarm.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/carAlarm/carAlarm_2guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        JSCATS eua = new EUAComputer(machine, abstractStates).compute();
        List<algorithms.outputs.Test> tests = TestsGenerator.generateTests(new UUAComputer(machine, eua));
        JSCATSStatisticsReporter JSCATSStatisticsReporter = new JSCATSStatisticsReporter(tests, machine, new ArrayList<>(abstractionPredicates), null);
    }

    @Test
    public void test_carAlarm1post() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/carAlarm/carAlarm.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/carAlarm/carAlarm_1post.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        JSCATS eua = new EUAComputer(machine, abstractStates).compute();
        List<algorithms.outputs.Test> tests = TestsGenerator.generateTests(new UUAComputer(machine, eua));
        JSCATSStatisticsReporter JSCATSStatisticsReporter = new JSCATSStatisticsReporter(tests, machine, new ArrayList<>(abstractionPredicates), null);
    }

    @Test
    public void test_carAlarm2post() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/carAlarm/carAlarm.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/carAlarm/carAlarm_2post.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        JSCATS eua = new EUAComputer(machine, abstractStates).compute();
        List<algorithms.outputs.Test> tests = TestsGenerator.generateTests(new UUAComputer(machine, eua));
        JSCATSStatisticsReporter JSCATSStatisticsReporter = new JSCATSStatisticsReporter(tests, machine, new ArrayList<>(abstractionPredicates), null);
    }

    @Test
    public void test_coffeeMachine1guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/coffeeMachine/coffeeMachine.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/coffeeMachine/coffeeMachine_1guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        JSCATS eua = new EUAComputer(machine, abstractStates).compute();
        List<algorithms.outputs.Test> tests = TestsGenerator.generateTests(new UUAComputer(machine, eua));
        JSCATSStatisticsReporter JSCATSStatisticsReporter = new JSCATSStatisticsReporter(tests, machine, new ArrayList<>(abstractionPredicates), null);
    }

    @Test
    public void test_coffeeMachine2guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/coffeeMachine/coffeeMachine.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/coffeeMachine/coffeeMachine_2guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        JSCATS eua = new EUAComputer(machine, abstractStates).compute();
        List<algorithms.outputs.Test> tests = TestsGenerator.generateTests(new UUAComputer(machine, eua));
        JSCATSStatisticsReporter JSCATSStatisticsReporter = new JSCATSStatisticsReporter(tests, machine, new ArrayList<>(abstractionPredicates), null);
    }

    @Test
    public void test_coffeeMachine1post() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/coffeeMachine/coffeeMachine.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/coffeeMachine/coffeeMachine_1post.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        JSCATS eua = new EUAComputer(machine, abstractStates).compute();
        List<algorithms.outputs.Test> tests = TestsGenerator.generateTests(new UUAComputer(machine, eua));
        JSCATSStatisticsReporter JSCATSStatisticsReporter = new JSCATSStatisticsReporter(tests, machine, new ArrayList<>(abstractionPredicates), null);
    }

    @Test
    public void test_coffeeMachine2post() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/coffeeMachine/coffeeMachine.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/coffeeMachine/coffeeMachine_2post.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        JSCATS eua = new EUAComputer(machine, abstractStates).compute();
        List<algorithms.outputs.Test> tests = TestsGenerator.generateTests(new UUAComputer(machine, eua));
        JSCATSStatisticsReporter JSCATSStatisticsReporter = new JSCATSStatisticsReporter(tests, machine, new ArrayList<>(abstractionPredicates), null);
    }

    @Test
    public void test_frontWiper1guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("/home/gvoiron/IdeaProjects/stratest/resources/eventb/frontWiper/frontWiper.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/frontWiper/frontWiper_1guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        JSCATS eua = new EUAComputer(machine, abstractStates).compute();
        List<algorithms.outputs.Test> tests = TestsGenerator.generateTests(new UUAComputer(machine, eua));
        JSCATSStatisticsReporter JSCATSStatisticsReporter = new JSCATSStatisticsReporter(tests, machine, new ArrayList<>(abstractionPredicates), null);
    }

    @Test
    public void test_frontWiper2guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("/home/gvoiron/IdeaProjects/stratest/resources/eventb/frontWiper/frontWiper.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/frontWiper/frontWiper_2guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        JSCATS eua = new EUAComputer(machine, abstractStates).compute();
        List<algorithms.outputs.Test> tests = TestsGenerator.generateTests(new UUAComputer(machine, eua));
        JSCATSStatisticsReporter JSCATSStatisticsReporter = new JSCATSStatisticsReporter(tests, machine, new ArrayList<>(abstractionPredicates), null);
    }

    @Test
    public void test_creditCard1guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("/home/gvoiron/IdeaProjects/stratest/resources/eventb/creditCard/creditCard.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/creditCard/creditCard_1guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        JSCATS eua = new EUAComputer(machine, abstractStates).compute();
        System.out.println();
        List<algorithms.outputs.Test> tests = TestsGenerator.generateTests(new UUAComputer(machine, eua));
        JSCATSStatisticsReporter JSCATSStatisticsReporter = new JSCATSStatisticsReporter(tests, machine, new ArrayList<>(abstractionPredicates), null);
    }

    @Test
    public void test_creditCard2guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("/home/gvoiron/IdeaProjects/stratest/resources/eventb/creditCard/creditCard.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/creditCard/creditCard_2guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        JSCATS eua = new EUAComputer(machine, abstractStates).compute();
        List<algorithms.outputs.Test> tests = TestsGenerator.generateTests(new UUAComputer(machine, eua));
        JSCATSStatisticsReporter JSCATSStatisticsReporter = new JSCATSStatisticsReporter(tests, machine, new ArrayList<>(abstractionPredicates), null);
    }

    @Test
    public void test_creditCard1post() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("/home/gvoiron/IdeaProjects/stratest/resources/eventb/creditCard/creditCard.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/creditCard/creditCard_1post.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        JSCATS eua = new EUAComputer(machine, abstractStates).compute();
        List<algorithms.outputs.Test> tests = TestsGenerator.generateTests(new UUAComputer(machine, eua));
        JSCATSStatisticsReporter JSCATSStatisticsReporter = new JSCATSStatisticsReporter(tests, machine, new ArrayList<>(abstractionPredicates), null);
    }

    @Test
    public void test_creditCard2post() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("/home/gvoiron/IdeaProjects/stratest/resources/eventb/creditCard/creditCard.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/creditCard/creditCard_2post.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        JSCATS eua = new EUAComputer(machine, abstractStates).compute();
        List<algorithms.outputs.Test> tests = TestsGenerator.generateTests(new UUAComputer(machine, eua));
        JSCATSStatisticsReporter JSCATSStatisticsReporter = new JSCATSStatisticsReporter(tests, machine, new ArrayList<>(abstractionPredicates), null);
    }

}