package algorithms.outputs;

import algorithms.computers.EUAComputer;
import algorithms.computers.OldEUAComputer;
import algorithms.tools.AbstractStatesComputer;
import eventb.Machine;
import eventb.expressions.arith.Int;
import eventb.expressions.arith.Variable;
import eventb.expressions.bool.And;
import eventb.expressions.bool.Equals;
import eventb.expressions.bool.Or;
import eventb.expressions.bool.Predicate;
import eventb.parsers.EBMParser;
import graphs.AbstractState;
import org.junit.Assert;
import org.junit.Test;
import parser.noeud.AfterParserException;
import parser.noeud.BParserException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

public class JSCATSStatisticsReporterTest2 {

    private void testAndSave(String systemName, String predicatesFolder, JSCATS eua0, JSCATS eua1, JSCATSStatisticsReporter eua0Report, JSCATSStatisticsReporter eua1Report) {
        Assert.assertEquals(0, eua0Report.getNbBlueStates());
        Assert.assertEquals(0, eua0Report.getNbGreenStates());
        Assert.assertTrue(eua1Report.getNbGreenStates() > 0);
        Assert.assertEquals(eua1.getC().size(), eua1Report.getNbBlueStates() + eua1Report.getNbGreenStates());
        Assert.assertTrue(eua0Report.getNbAbstractStates() >= 0 && eua0Report.getNbAbstractStates() <= Math.pow(2, eua0Report.getNbAbstractStates()));
        Assert.assertTrue(eua1Report.getNbAbstractStates() >= 0 && eua1Report.getNbAbstractStates() <= Math.pow(2, eua1Report.getNbAbstractStates()));
        Assert.assertTrue(eua0Report.getNbMustMinusTransitions() >= eua0Report.getNbMustSharpTransitions());
        Assert.assertTrue(eua1Report.getNbMustMinusTransitions() >= eua1Report.getNbMustSharpTransitions());
        Assert.assertTrue(eua0Report.getNbMustPlusTransitions() >= eua0Report.getNbMustSharpTransitions());
        Assert.assertTrue(eua1Report.getNbMustPlusTransitions() >= eua1Report.getNbMustSharpTransitions());
        Assert.assertEquals(eua0Report.getNbAbstractTransitions(), eua0Report.getNbPureMayTransitions() + eua0Report.getNbMustMinusTransitions() + eua0Report.getNbMustPlusTransitions() - eua0Report.getNbMustSharpTransitions());
        Assert.assertEquals(eua1Report.getNbAbstractTransitions(), eua1Report.getNbPureMayTransitions() + eua1Report.getNbMustMinusTransitions() + eua1Report.getNbMustPlusTransitions() - eua1Report.getNbMustSharpTransitions());
        Assert.assertTrue(eua0Report.getNbAbstractStates() >= eua0Report.getNbAbstractStatesInTests());
        Assert.assertTrue(eua1Report.getNbAbstractStates() >= eua1Report.getNbAbstractStatesInTests());
        Assert.assertTrue(eua0Report.getNbAbstractTransitions() >= eua0Report.getNbAbstractTransitionsInTests());
        Assert.assertTrue(eua1Report.getNbAbstractTransitions() >= eua1Report.getNbAbstractTransitionsInTests());
        try {
            File root = new File("resources/outputs/" + systemName + "/" + predicatesFolder);
            root.mkdirs();
            Files.write(Paths.get(root.getAbsolutePath() + "/eua0.txt"), eua0Report.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(root.getAbsolutePath() + "/eua0.dot"), eua0.getTestsDOTFormatting(eua0Report.getTests()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(root.getAbsolutePath() + "/eua0.table"), eua0Report.getRowRepresentation().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(root.getAbsolutePath() + "/eua1.txt"), eua1Report.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(root.getAbsolutePath() + "/eua1.dot"), eua1.getTestsDOTFormatting(eua1Report.getTests()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(root.getAbsolutePath() + "/eua1.table"), eua1Report.getRowRepresentation().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        List<Predicate> abstractionPredicates = Arrays.asList(p0, p1);
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates);
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("Tic", "Commute", "Repair", "Fail"));
        Collections.shuffle(machine.getEvents());
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        testAndSave("threeBatteries", "default", eua0, eua1, eua0Report, eua1Report);
    }

    @Test
    public void test_threeBatteries1guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/threeBatteries/threeBatteries.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/threeBatteries/threeBatteries_1guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("Commute", "Tic"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        testAndSave("threeBatteries", "1guard", eua0, eua1, eua0Report, eua1Report);
    }

    @Test
    public void test_threeBatteries2guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/threeBatteries/threeBatteries.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/threeBatteries/threeBatteries_2guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("Repair", "Fail"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        testAndSave("threeBatteries", "2guard", eua0, eua1, eua0Report, eua1Report);
    }

    @Test
    public void test_threeBatteries1post() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/threeBatteries/threeBatteries.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/threeBatteries/threeBatteries_1post.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("Commute", "Tic"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        testAndSave("threeBatteries", "1post", eua0, eua1, eua0Report, eua1Report);
    }

    @Test
    public void test_phone1guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/phone/phone.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/phone/phone_1guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("HangUp", "Start"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        testAndSave("phone", "1guard", eua0, eua1, eua0Report, eua1Report);
    }

    @Test
    public void test_phone2guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/phone/phone.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/phone/phone_2guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("HangUp", "Timeout"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        testAndSave("phone", "2guard", eua0, eua1, eua0Report, eua1Report);
    }

    @Test
    public void test_phone1post() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/phone/phone.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/phone/phone_1post.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("HangUp", "Start"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        testAndSave("phone", "1post", eua0, eua1, eua0Report, eua1Report);
    }

    @Test
    public void test_phone2post() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/phone/phone.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/phone/phone_2post.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("HangUp", "Timeout"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        testAndSave("phone", "2post", eua0, eua1, eua0Report, eua1Report);
    }

    @Test
    public void test_carAlarm1guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/carAlarm/carAlarm.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/carAlarm/carAlarm_1guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("Bell_Activation", "Doors_Opening", "User_Unauthorized", "User_Authorized"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        testAndSave("carAlarm", "1guard", eua0, eua1, eua0Report, eua1Report);
    }

    @Test
    public void test_carAlarm2guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/carAlarm/carAlarm.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/carAlarm/carAlarm_2guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("Lock_Doors", "UnLock_Doors"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        testAndSave("carAlarm", "2guard", eua0, eua1, eua0Report, eua1Report);
    }

    @Test
    public void test_carAlarm1post() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/carAlarm/carAlarm.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/carAlarm/carAlarm_1post.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("Bell_Activation", "Doors_Opening", "User_Unauthorized", "User_Authorized"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        testAndSave("carAlarm", "1post", eua0, eua1, eua0Report, eua1Report);
    }

    @Test
    public void test_carAlarm2post() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/carAlarm/carAlarm.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/carAlarm/carAlarm_2post.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("Lock_Doors", "UnLock_Doors"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        testAndSave("carAlarm", "2post", eua0, eua1, eua0Report, eua1Report);
    }

    @Test
    public void test_coffeeMachine1guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/coffeeMachine/coffeeMachine.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/coffeeMachine/coffeeMachine_1guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Collections.singletonList("ServeCoffee"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        testAndSave("coffeeMachine", "1guard", eua0, eua1, eua0Report, eua1Report);
    }

    @Test
    public void test_coffeeMachine2guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/coffeeMachine/coffeeMachine.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/coffeeMachine/coffeeMachine_2guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("takeMago", "AutoOut", "powerDown"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        testAndSave("coffeeMachine", "2guard", eua0, eua1, eua0Report, eua1Report);
    }

    @Test
    public void test_coffeeMachine1post() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/coffeeMachine/coffeeMachine.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/coffeeMachine/coffeeMachine_1post.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Collections.singletonList("ServeCoffee"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        testAndSave("coffeeMachine", "1post", eua0, eua1, eua0Report, eua1Report);
    }

    @Test
    public void test_coffeeMachine2post() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/coffeeMachine/coffeeMachine.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/coffeeMachine/coffeeMachine_2post.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("takeMago", "AutoOut", "powerDown"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        testAndSave("coffeeMachine", "2post", eua0, eua1, eua0Report, eua1Report);
    }

    @Test
    public void test_frontWiper1guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/frontWiper/frontWiper.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/frontWiper/frontWiper_1guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("power_down", "wash_action_begin", "wash_action_end"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 27));
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 27));
        testAndSave("frontWiper", "1guard", eua0, eua1, eua0Report, eua1Report);
    }

    @Test
    public void test_frontWiper2guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/frontWiper/frontWiper.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/frontWiper/frontWiper_2guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("block_FW_engine", "action_commodo_down"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 27));
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 27));
        testAndSave("frontWiper", "2guard", eua0, eua1, eua0Report, eua1Report);
    }

    @Test
    public void test_creditCard1guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/creditCard/creditCard.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/creditCard/creditCard_1guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("CARD_failed_pin", "CARD_process_pin"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        testAndSave("creditCard", "1guard", eua0, eua1, eua0Report, eua1Report);
    }

    @Test
    public void test_creditCard2guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/creditCard/creditCard.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/creditCard/creditCard_2guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("DB_ok", "CARD_success_pin", "DB_operation_not_done"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        testAndSave("creditCard", "2guard", eua0, eua1, eua0Report, eua1Report);
    }

    @Test
    public void test_creditCard1post() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/creditCard/creditCard.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/creditCard/creditCard_1post.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("CARD_failed_pin", "CARD_process_pin"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        testAndSave("creditCard", "1post", eua0, eua1, eua0Report, eua1Report);
    }

    @Test
    public void test_creditCard2post() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/creditCard/creditCard.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/creditCard/creditCard_2post.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("DB_ok", "CARD_success_pin", "DB_operation_not_done"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        testAndSave("creditCard", "2post", eua0, eua1, eua0Report, eua1Report);
    }

}

