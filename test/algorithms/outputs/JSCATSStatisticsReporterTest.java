package algorithms.outputs;

import algorithms.computers.EUAComputer;
import algorithms.computers.OldEUAComputer;
import algorithms.computers.OldUUAComputer;
import algorithms.computers.UUAComputer;
import algorithms.tools.AbstractStatesComputer;
import algorithms.tools.UniversalRelevancyChecker;
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

/**
 * Created by gvoiron on 04/09/16.
 * Time : 11:30
 */
public class JSCATSStatisticsReporterTest {

    private void testAndSave(String systemName, String predicatesFolder, JSCATS eua0, JSCATS eua1, JSCATS eua2, JSCATS eua3, JSCATS eua4, JSCATS uua0, JSCATS uua1, JSCATS uua2, JSCATS uua3, JSCATS uua4, JSCATSStatisticsReporter eua0Report, JSCATSStatisticsReporter eua1Report, JSCATSStatisticsReporter eua2Report, JSCATSStatisticsReporter eua3Report, JSCATSStatisticsReporter eua4Report, JSCATSStatisticsReporter uua0Report, JSCATSStatisticsReporter uua1Report, JSCATSStatisticsReporter uua2Report, JSCATSStatisticsReporter uua3Report, JSCATSStatisticsReporter uua4Report) {
        Assert.assertEquals(0, eua0Report.getNbBlueStates());
        Assert.assertEquals(0, eua0Report.getNbGreenStates());
        Assert.assertTrue(eua1Report.getNbGreenStates() > 0);
        Assert.assertTrue(eua2Report.getNbGreenStates() > 0);
        Assert.assertTrue(eua3Report.getNbGreenStates() > 0);
        Assert.assertTrue(eua4Report.getNbGreenStates() > 0);
        Assert.assertTrue(uua1Report.getNbGreenStates() >= eua1Report.getNbGreenStates());
        Assert.assertTrue(uua2Report.getNbGreenStates() >= eua2Report.getNbGreenStates());
        Assert.assertTrue(uua3Report.getNbGreenStates() >= eua3Report.getNbGreenStates());
        Assert.assertTrue(uua4Report.getNbGreenStates() >= eua4Report.getNbGreenStates());
        Assert.assertEquals(eua1.getC().size(), eua1Report.getNbBlueStates() + eua1Report.getNbGreenStates());
        Assert.assertEquals(eua2.getC().size(), eua2Report.getNbBlueStates() + eua2Report.getNbGreenStates());
        Assert.assertEquals(eua3.getC().size(), eua3Report.getNbBlueStates() + eua3Report.getNbGreenStates());
        Assert.assertEquals(eua4.getC().size(), eua4Report.getNbBlueStates() + eua4Report.getNbGreenStates());
        Assert.assertEquals(uua1.getC().size(), uua1Report.getNbBlueStates() + uua1Report.getNbGreenStates());
        Assert.assertEquals(uua2.getC().size(), uua2Report.getNbBlueStates() + uua2Report.getNbGreenStates());
        Assert.assertEquals(uua3.getC().size(), uua3Report.getNbBlueStates() + uua3Report.getNbGreenStates());
        Assert.assertEquals(uua4.getC().size(), uua4Report.getNbBlueStates() + uua4Report.getNbGreenStates());
        Assert.assertTrue(eua0Report.getNbAbstractStates() >= 0 && eua0Report.getNbAbstractStates() <= Math.pow(2, eua0Report.getNbAbstractStates()));
        Assert.assertTrue(eua1Report.getNbAbstractStates() >= 0 && eua1Report.getNbAbstractStates() <= Math.pow(2, eua1Report.getNbAbstractStates()));
        Assert.assertTrue(eua2Report.getNbAbstractStates() >= 0 && eua2Report.getNbAbstractStates() <= Math.pow(2, eua2Report.getNbAbstractStates()));
        Assert.assertTrue(eua3Report.getNbAbstractStates() >= 0 && eua3Report.getNbAbstractStates() <= Math.pow(2, eua3Report.getNbAbstractStates()));
        Assert.assertTrue(eua4Report.getNbAbstractStates() >= 0 && eua4Report.getNbAbstractStates() <= Math.pow(2, eua4Report.getNbAbstractStates()));
        Assert.assertTrue(uua0Report.getNbAbstractStates() >= 0 && uua0Report.getNbAbstractStates() <= Math.pow(2, uua0Report.getNbAbstractStates()));
        Assert.assertTrue(uua1Report.getNbAbstractStates() >= 0 && uua1Report.getNbAbstractStates() <= Math.pow(2, uua1Report.getNbAbstractStates()));
        Assert.assertTrue(uua2Report.getNbAbstractStates() >= 0 && uua2Report.getNbAbstractStates() <= Math.pow(2, uua2Report.getNbAbstractStates()));
        Assert.assertTrue(uua3Report.getNbAbstractStates() >= 0 && uua3Report.getNbAbstractStates() <= Math.pow(2, uua3Report.getNbAbstractStates()));
        Assert.assertTrue(uua4Report.getNbAbstractStates() >= 0 && uua4Report.getNbAbstractStates() <= Math.pow(2, uua4Report.getNbAbstractStates()));
        Assert.assertTrue(eua0Report.getNbMustMinusTransitions() >= eua0Report.getNbMustSharpTransitions());
        Assert.assertTrue(eua1Report.getNbMustMinusTransitions() >= eua1Report.getNbMustSharpTransitions());
        Assert.assertTrue(eua2Report.getNbMustMinusTransitions() >= eua2Report.getNbMustSharpTransitions());
        Assert.assertTrue(eua3Report.getNbMustMinusTransitions() >= eua3Report.getNbMustSharpTransitions());
        Assert.assertTrue(eua4Report.getNbMustMinusTransitions() >= eua4Report.getNbMustSharpTransitions());
        Assert.assertTrue(uua0Report.getNbMustMinusTransitions() >= uua0Report.getNbMustSharpTransitions());
        Assert.assertTrue(uua1Report.getNbMustMinusTransitions() >= uua1Report.getNbMustSharpTransitions());
        Assert.assertTrue(uua2Report.getNbMustMinusTransitions() >= uua2Report.getNbMustSharpTransitions());
        Assert.assertTrue(uua3Report.getNbMustMinusTransitions() >= uua3Report.getNbMustSharpTransitions());
        Assert.assertTrue(uua4Report.getNbMustMinusTransitions() >= uua4Report.getNbMustSharpTransitions());
        Assert.assertTrue(eua0Report.getNbMustPlusTransitions() >= eua0Report.getNbMustSharpTransitions());
        Assert.assertTrue(eua1Report.getNbMustPlusTransitions() >= eua1Report.getNbMustSharpTransitions());
        Assert.assertTrue(eua2Report.getNbMustPlusTransitions() >= eua2Report.getNbMustSharpTransitions());
        Assert.assertTrue(eua3Report.getNbMustPlusTransitions() >= eua3Report.getNbMustSharpTransitions());
        Assert.assertTrue(eua4Report.getNbMustPlusTransitions() >= eua4Report.getNbMustSharpTransitions());
        Assert.assertTrue(uua0Report.getNbMustPlusTransitions() >= uua0Report.getNbMustSharpTransitions());
        Assert.assertTrue(uua1Report.getNbMustPlusTransitions() >= uua1Report.getNbMustSharpTransitions());
        Assert.assertTrue(uua2Report.getNbMustPlusTransitions() >= uua2Report.getNbMustSharpTransitions());
        Assert.assertTrue(uua3Report.getNbMustPlusTransitions() >= uua3Report.getNbMustSharpTransitions());
        Assert.assertTrue(uua4Report.getNbMustPlusTransitions() >= uua4Report.getNbMustSharpTransitions());
        Assert.assertEquals(eua0Report.getNbAbstractTransitions(), eua0Report.getNbPureMayTransitions() + eua0Report.getNbMustMinusTransitions() + eua0Report.getNbMustPlusTransitions() - eua0Report.getNbMustSharpTransitions());
        Assert.assertEquals(eua1Report.getNbAbstractTransitions(), eua1Report.getNbPureMayTransitions() + eua1Report.getNbMustMinusTransitions() + eua1Report.getNbMustPlusTransitions() - eua1Report.getNbMustSharpTransitions());
        Assert.assertEquals(eua2Report.getNbAbstractTransitions(), eua2Report.getNbPureMayTransitions() + eua2Report.getNbMustMinusTransitions() + eua2Report.getNbMustPlusTransitions() - eua2Report.getNbMustSharpTransitions());
        Assert.assertEquals(eua3Report.getNbAbstractTransitions(), eua3Report.getNbPureMayTransitions() + eua3Report.getNbMustMinusTransitions() + eua3Report.getNbMustPlusTransitions() - eua3Report.getNbMustSharpTransitions());
        Assert.assertEquals(eua4Report.getNbAbstractTransitions(), eua4Report.getNbPureMayTransitions() + eua4Report.getNbMustMinusTransitions() + eua4Report.getNbMustPlusTransitions() - eua4Report.getNbMustSharpTransitions());
        Assert.assertEquals(uua0Report.getNbAbstractTransitions(), uua0Report.getNbPureMayTransitions() + uua0Report.getNbMustMinusTransitions() + uua0Report.getNbMustPlusTransitions() - uua0Report.getNbMustSharpTransitions());
        Assert.assertEquals(uua1Report.getNbAbstractTransitions(), uua1Report.getNbPureMayTransitions() + uua1Report.getNbMustMinusTransitions() + uua1Report.getNbMustPlusTransitions() - uua1Report.getNbMustSharpTransitions());
        Assert.assertEquals(uua2Report.getNbAbstractTransitions(), uua2Report.getNbPureMayTransitions() + uua2Report.getNbMustMinusTransitions() + uua2Report.getNbMustPlusTransitions() - uua2Report.getNbMustSharpTransitions());
        Assert.assertEquals(uua3Report.getNbAbstractTransitions(), uua3Report.getNbPureMayTransitions() + uua3Report.getNbMustMinusTransitions() + uua3Report.getNbMustPlusTransitions() - uua3Report.getNbMustSharpTransitions());
        Assert.assertEquals(uua4Report.getNbAbstractTransitions(), uua4Report.getNbPureMayTransitions() + uua4Report.getNbMustMinusTransitions() + uua4Report.getNbMustPlusTransitions() - uua4Report.getNbMustSharpTransitions());
        Assert.assertTrue(eua0Report.getNbAbstractStates() >= eua0Report.getNbAbstractStatesInTests());
        Assert.assertTrue(eua1Report.getNbAbstractStates() >= eua1Report.getNbAbstractStatesInTests());
        Assert.assertTrue(eua2Report.getNbAbstractStates() >= eua2Report.getNbAbstractStatesInTests());
        Assert.assertTrue(eua3Report.getNbAbstractStates() >= eua3Report.getNbAbstractStatesInTests());
        Assert.assertTrue(eua4Report.getNbAbstractStates() >= eua4Report.getNbAbstractStatesInTests());
        Assert.assertTrue(uua0Report.getNbAbstractStates() >= uua0Report.getNbAbstractStatesInTests());
        Assert.assertTrue(uua1Report.getNbAbstractStates() >= uua1Report.getNbAbstractStatesInTests());
        Assert.assertTrue(uua2Report.getNbAbstractStates() >= uua2Report.getNbAbstractStatesInTests());
        Assert.assertTrue(uua3Report.getNbAbstractStates() >= uua3Report.getNbAbstractStatesInTests());
        Assert.assertTrue(uua4Report.getNbAbstractStates() >= uua4Report.getNbAbstractStatesInTests());
        Assert.assertTrue(eua0Report.getNbAbstractTransitions() >= eua0Report.getNbAbstractTransitionsInTests());
        Assert.assertTrue(eua1Report.getNbAbstractTransitions() >= eua1Report.getNbAbstractTransitionsInTests());
        Assert.assertTrue(eua2Report.getNbAbstractTransitions() >= eua2Report.getNbAbstractTransitionsInTests());
        Assert.assertTrue(eua3Report.getNbAbstractTransitions() >= eua3Report.getNbAbstractTransitionsInTests());
        Assert.assertTrue(eua4Report.getNbAbstractTransitions() >= eua4Report.getNbAbstractTransitionsInTests());
        Assert.assertTrue(uua0Report.getNbAbstractTransitions() >= uua0Report.getNbAbstractTransitionsInTests());
        Assert.assertTrue(uua1Report.getNbAbstractTransitions() >= uua1Report.getNbAbstractTransitionsInTests());
        Assert.assertTrue(uua2Report.getNbAbstractTransitions() >= uua2Report.getNbAbstractTransitionsInTests());
        Assert.assertTrue(uua3Report.getNbAbstractTransitions() >= uua3Report.getNbAbstractTransitionsInTests());
        Assert.assertTrue(uua4Report.getNbAbstractTransitions() >= uua4Report.getNbAbstractTransitionsInTests());
        try {
            File root = new File("resources/outputs/" + systemName + "/" + predicatesFolder);
            root.mkdirs();
            Files.write(Paths.get(root.getAbsolutePath() + "/eua0.txt"), eua0Report.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(root.getAbsolutePath() + "/eua0.dot"), eua0.getTestsDOTFormatting(eua0Report.getTests()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(root.getAbsolutePath() + "/eua0.table"), eua0Report.getRowRepresentation().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(root.getAbsolutePath() + "/eua1.txt"), eua1Report.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(root.getAbsolutePath() + "/eua1.dot"), eua1.getTestsDOTFormatting(eua1Report.getTests()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(root.getAbsolutePath() + "/eua1.table"), eua1Report.getRowRepresentation().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(root.getAbsolutePath() + "/eua2.txt"), eua2Report.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(root.getAbsolutePath() + "/eua2.dot"), eua2.getTestsDOTFormatting(eua2Report.getTests()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(root.getAbsolutePath() + "/eua2.table"), eua2Report.getRowRepresentation().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(root.getAbsolutePath() + "/eua3.txt"), eua3Report.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(root.getAbsolutePath() + "/eua3.dot"), eua3.getTestsDOTFormatting(eua3Report.getTests()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(root.getAbsolutePath() + "/eua3.table"), eua3Report.getRowRepresentation().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(root.getAbsolutePath() + "/eua4.txt"), eua4Report.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(root.getAbsolutePath() + "/eua4.dot"), eua4.getTestsDOTFormatting(eua4Report.getTests()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(root.getAbsolutePath() + "/eua4.table"), eua4Report.getRowRepresentation().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(root.getAbsolutePath() + "/uua0.txt"), uua0Report.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(root.getAbsolutePath() + "/uua0.dot"), uua0.getTestsDOTFormatting(uua0Report.getTests()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(root.getAbsolutePath() + "/uua0.table"), uua0Report.getRowRepresentation().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(root.getAbsolutePath() + "/uua1.txt"), uua1Report.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(root.getAbsolutePath() + "/uua1.dot"), uua1.getTestsDOTFormatting(uua1Report.getTests()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(root.getAbsolutePath() + "/uua1.table"), uua1Report.getRowRepresentation().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(root.getAbsolutePath() + "/uua2.txt"), uua2Report.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(root.getAbsolutePath() + "/uua2.dot"), uua2.getTestsDOTFormatting(uua2Report.getTests()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(root.getAbsolutePath() + "/uua2.table"), uua2Report.getRowRepresentation().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(root.getAbsolutePath() + "/uua3.txt"), uua3Report.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(root.getAbsolutePath() + "/uua3.dot"), uua3.getTestsDOTFormatting(uua3Report.getTests()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(root.getAbsolutePath() + "/uua3.table"), uua3Report.getRowRepresentation().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(root.getAbsolutePath() + "/uua4.txt"), uua4Report.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(root.getAbsolutePath() + "/uua4.dot"), uua4.getTestsDOTFormatting(uua4Report.getTests()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(root.getAbsolutePath() + "/uua4.table"), uua4Report.getRowRepresentation().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_testGeneration_simple() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/simple/simple.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/simple/simple_1.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Collections.singletonList("event1"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATS eua2 = new EUAComputer(machine, abstractStates, true).compute();
        JSCATS eua3 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50)).compute();
        JSCATS eua4 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50), true).compute();
        JSCATS uua0 = new OldUUAComputer(machine, eua0).compute();
        JSCATS uua1 = new UUAComputer(machine, eua1).compute();
        JSCATS uua2 = new UUAComputer(machine, eua2).compute();
        JSCATS uua3 = new UUAComputer(machine, eua3).compute();
        JSCATS uua4 = new UUAComputer(machine, eua4).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, 2);
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, 2);
        JSCATSStatisticsReporter eua2Report = new JSCATSStatisticsReporter(eua2, machine, new ArrayList<>(abstractionPredicates), eventNames, 2);
        JSCATSStatisticsReporter eua3Report = new JSCATSStatisticsReporter(eua3, machine, new ArrayList<>(abstractionPredicates), eventNames, 2);
        JSCATSStatisticsReporter eua4Report = new JSCATSStatisticsReporter(eua4, machine, new ArrayList<>(abstractionPredicates), eventNames, 2);
        JSCATSStatisticsReporter uua0Report = new JSCATSStatisticsReporter(uua0, machine, new ArrayList<>(abstractionPredicates), eventNames, 2);
        JSCATSStatisticsReporter uua1Report = new JSCATSStatisticsReporter(uua1, machine, new ArrayList<>(abstractionPredicates), eventNames, 2);
        JSCATSStatisticsReporter uua2Report = new JSCATSStatisticsReporter(uua2, machine, new ArrayList<>(abstractionPredicates), eventNames, 2);
        JSCATSStatisticsReporter uua3Report = new JSCATSStatisticsReporter(uua3, machine, new ArrayList<>(abstractionPredicates), eventNames, 2);
        JSCATSStatisticsReporter uua4Report = new JSCATSStatisticsReporter(uua4, machine, new ArrayList<>(abstractionPredicates), eventNames, 2);
        testAndSave("simple", "default", eua0, eua1, eua2, eua3, eua4, uua0, uua1, uua2, uua3, uua4, eua0Report, eua1Report, eua2Report, eua3Report, eua4Report, uua0Report, uua1Report, uua2Report, uua3Report, uua4Report);
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
        JSCATS eua2 = new EUAComputer(machine, abstractStates, true).compute();
        JSCATS eua3 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50)).compute();
        JSCATS eua4 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50), true).compute();
        JSCATS uua0 = new OldUUAComputer(machine, eua0).compute();
        JSCATS uua1 = new UUAComputer(machine, eua1).compute();
        JSCATS uua2 = new UUAComputer(machine, eua2).compute();
        JSCATS uua3 = new UUAComputer(machine, eua3).compute();
        JSCATS uua4 = new UUAComputer(machine, eua4).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter eua2Report = new JSCATSStatisticsReporter(eua2, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter eua3Report = new JSCATSStatisticsReporter(eua3, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter eua4Report = new JSCATSStatisticsReporter(eua4, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter uua0Report = new JSCATSStatisticsReporter(uua0, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter uua1Report = new JSCATSStatisticsReporter(uua1, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter uua2Report = new JSCATSStatisticsReporter(uua2, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter uua3Report = new JSCATSStatisticsReporter(uua3, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter uua4Report = new JSCATSStatisticsReporter(uua4, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        testAndSave("threeBatteries", "default", eua0, eua1, eua2, eua3, eua4, uua0, uua1, uua2, uua3, uua4, eua0Report, eua1Report, eua2Report, eua3Report, eua4Report, uua0Report, uua1Report, uua2Report, uua3Report, uua4Report);
    }

    @Test
    public void test_threeBatteries1guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/threeBatteries/threeBatteries.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/threeBatteries/threeBatteries_1guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("Commute", "Tic"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATS eua2 = new EUAComputer(machine, abstractStates, true).compute();
        JSCATS eua3 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50)).compute();
        JSCATS eua4 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50), true).compute();
        JSCATS uua0 = new OldUUAComputer(machine, eua0).compute();
        JSCATS uua1 = new UUAComputer(machine, eua1).compute();
        JSCATS uua2 = new UUAComputer(machine, eua2).compute();
        JSCATS uua3 = new UUAComputer(machine, eua3).compute();
        JSCATS uua4 = new UUAComputer(machine, eua4).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter eua2Report = new JSCATSStatisticsReporter(eua2, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter eua3Report = new JSCATSStatisticsReporter(eua3, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter eua4Report = new JSCATSStatisticsReporter(eua4, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter uua0Report = new JSCATSStatisticsReporter(uua0, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter uua1Report = new JSCATSStatisticsReporter(uua1, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter uua2Report = new JSCATSStatisticsReporter(uua2, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter uua3Report = new JSCATSStatisticsReporter(uua3, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter uua4Report = new JSCATSStatisticsReporter(uua4, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        testAndSave("threeBatteries", "1guard", eua0, eua1, eua2, eua3, eua4, uua0, uua1, uua2, uua3, uua4, eua0Report, eua1Report, eua2Report, eua3Report, eua4Report, uua0Report, uua1Report, uua2Report, uua3Report, uua4Report);
    }

    @Test
    public void test_threeBatteries2guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/threeBatteries/threeBatteries.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/threeBatteries/threeBatteries_2guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("Repair", "Fail"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATS eua2 = new EUAComputer(machine, abstractStates, true).compute();
        JSCATS eua3 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50)).compute();
        JSCATS eua4 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50), true).compute();
        JSCATS uua0 = new OldUUAComputer(machine, eua0).compute();
        JSCATS uua1 = new UUAComputer(machine, eua1).compute();
        JSCATS uua2 = new UUAComputer(machine, eua2).compute();
        JSCATS uua3 = new UUAComputer(machine, eua3).compute();
        JSCATS uua4 = new UUAComputer(machine, eua4).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter eua2Report = new JSCATSStatisticsReporter(eua2, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter eua3Report = new JSCATSStatisticsReporter(eua3, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter eua4Report = new JSCATSStatisticsReporter(eua4, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter uua0Report = new JSCATSStatisticsReporter(uua0, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter uua1Report = new JSCATSStatisticsReporter(uua1, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter uua2Report = new JSCATSStatisticsReporter(uua2, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter uua3Report = new JSCATSStatisticsReporter(uua3, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter uua4Report = new JSCATSStatisticsReporter(uua4, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        testAndSave("threeBatteries", "2guard", eua0, eua1, eua2, eua3, eua4, uua0, uua1, uua2, uua3, uua4, eua0Report, eua1Report, eua2Report, eua3Report, eua4Report, uua0Report, uua1Report, uua2Report, uua3Report, uua4Report);
    }

    @Test
    public void test_threeBatteries1post() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/threeBatteries/threeBatteries.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/threeBatteries/threeBatteries_1post.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("Commute", "Tic"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATS eua2 = new EUAComputer(machine, abstractStates, true).compute();
        JSCATS eua3 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50)).compute();
        JSCATS eua4 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50), true).compute();
        JSCATS uua0 = new OldUUAComputer(machine, eua0).compute();
        JSCATS uua1 = new UUAComputer(machine, eua1).compute();
        JSCATS uua2 = new UUAComputer(machine, eua2).compute();
        JSCATS uua3 = new UUAComputer(machine, eua3).compute();
        JSCATS uua4 = new UUAComputer(machine, eua4).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter eua2Report = new JSCATSStatisticsReporter(eua2, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter eua3Report = new JSCATSStatisticsReporter(eua3, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter eua4Report = new JSCATSStatisticsReporter(eua4, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter uua0Report = new JSCATSStatisticsReporter(uua0, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter uua1Report = new JSCATSStatisticsReporter(uua1, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter uua2Report = new JSCATSStatisticsReporter(uua2, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter uua3Report = new JSCATSStatisticsReporter(uua3, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        JSCATSStatisticsReporter uua4Report = new JSCATSStatisticsReporter(uua4, machine, new ArrayList<>(abstractionPredicates), eventNames, 54);
        testAndSave("threeBatteries", "1post", eua0, eua1, eua2, eua3, eua4, uua0, uua1, uua2, uua3, uua4, eua0Report, eua1Report, eua2Report, eua3Report, eua4Report, uua0Report, uua1Report, uua2Report, uua3Report, uua4Report);
    }

    @Test
    public void test_phone1guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/phone/phone.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/phone/phone_1guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("HangUp", "Start"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATS eua2 = new EUAComputer(machine, abstractStates, true).compute();
        JSCATS eua3 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50)).compute();
        JSCATS eua4 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50), true).compute();
        JSCATS uua0 = new OldUUAComputer(machine, eua0).compute();
        JSCATS uua1 = new UUAComputer(machine, eua1).compute();
        JSCATS uua2 = new UUAComputer(machine, eua2).compute();
        JSCATS uua3 = new UUAComputer(machine, eua3).compute();
        JSCATS uua4 = new UUAComputer(machine, eua4).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter eua2Report = new JSCATSStatisticsReporter(eua2, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter eua3Report = new JSCATSStatisticsReporter(eua3, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter eua4Report = new JSCATSStatisticsReporter(eua4, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter uua0Report = new JSCATSStatisticsReporter(uua0, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter uua1Report = new JSCATSStatisticsReporter(uua1, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter uua2Report = new JSCATSStatisticsReporter(uua2, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter uua3Report = new JSCATSStatisticsReporter(uua3, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter uua4Report = new JSCATSStatisticsReporter(uua4, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        testAndSave("phone", "1guard", eua0, eua1, eua2, eua3, eua4, uua0, uua1, uua2, uua3, uua4, eua0Report, eua1Report, eua2Report, eua3Report, eua4Report, uua0Report, uua1Report, uua2Report, uua3Report, uua4Report);
    }

    @Test
    public void test_phone2guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/phone/phone.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/phone/phone_2guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("HangUp", "Timeout"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATS eua2 = new EUAComputer(machine, abstractStates, true).compute();
        JSCATS eua3 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50)).compute();
        JSCATS eua4 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50), true).compute();
        JSCATS uua0 = new OldUUAComputer(machine, eua0).compute();
        JSCATS uua1 = new UUAComputer(machine, eua1).compute();
        JSCATS uua2 = new UUAComputer(machine, eua2).compute();
        JSCATS uua3 = new UUAComputer(machine, eua3).compute();
        JSCATS uua4 = new UUAComputer(machine, eua4).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter eua2Report = new JSCATSStatisticsReporter(eua2, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter eua3Report = new JSCATSStatisticsReporter(eua3, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter eua4Report = new JSCATSStatisticsReporter(eua4, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter uua0Report = new JSCATSStatisticsReporter(uua0, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter uua1Report = new JSCATSStatisticsReporter(uua1, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter uua2Report = new JSCATSStatisticsReporter(uua2, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter uua3Report = new JSCATSStatisticsReporter(uua3, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter uua4Report = new JSCATSStatisticsReporter(uua4, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        testAndSave("phone", "2guard", eua0, eua1, eua2, eua3, eua4, uua0, uua1, uua2, uua3, uua4, eua0Report, eua1Report, eua2Report, eua3Report, eua4Report, uua0Report, uua1Report, uua2Report, uua3Report, uua4Report);
    }

    @Test
    public void test_phone1post() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/phone/phone.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/phone/phone_1post.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("HangUp", "Start"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATS eua2 = new EUAComputer(machine, abstractStates, true).compute();
        JSCATS eua3 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50)).compute();
        JSCATS eua4 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50), true).compute();
        JSCATS uua0 = new OldUUAComputer(machine, eua0).compute();
        JSCATS uua1 = new UUAComputer(machine, eua1).compute();
        JSCATS uua2 = new UUAComputer(machine, eua2).compute();
        JSCATS uua3 = new UUAComputer(machine, eua3).compute();
        JSCATS uua4 = new UUAComputer(machine, eua4).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter eua2Report = new JSCATSStatisticsReporter(eua2, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter eua3Report = new JSCATSStatisticsReporter(eua3, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter eua4Report = new JSCATSStatisticsReporter(eua4, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter uua0Report = new JSCATSStatisticsReporter(uua0, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter uua1Report = new JSCATSStatisticsReporter(uua1, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter uua2Report = new JSCATSStatisticsReporter(uua2, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter uua3Report = new JSCATSStatisticsReporter(uua3, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter uua4Report = new JSCATSStatisticsReporter(uua4, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        testAndSave("phone", "1post", eua0, eua1, eua2, eua3, eua4, uua0, uua1, uua2, uua3, uua4, eua0Report, eua1Report, eua2Report, eua3Report, eua4Report, uua0Report, uua1Report, uua2Report, uua3Report, uua4Report);
    }

    @Test
    public void test_phone2post() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/phone/phone.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/phone/phone_2post.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("HangUp", "Timeout"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATS eua2 = new EUAComputer(machine, abstractStates, true).compute();
        JSCATS eua3 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50)).compute();
        JSCATS eua4 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50), true).compute();
        JSCATS uua0 = new OldUUAComputer(machine, eua0).compute();
        JSCATS uua1 = new UUAComputer(machine, eua1).compute();
        JSCATS uua2 = new UUAComputer(machine, eua2).compute();
        JSCATS uua3 = new UUAComputer(machine, eua3).compute();
        JSCATS uua4 = new UUAComputer(machine, eua4).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter eua2Report = new JSCATSStatisticsReporter(eua2, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter eua3Report = new JSCATSStatisticsReporter(eua3, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter eua4Report = new JSCATSStatisticsReporter(eua4, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter uua0Report = new JSCATSStatisticsReporter(uua0, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter uua1Report = new JSCATSStatisticsReporter(uua1, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter uua2Report = new JSCATSStatisticsReporter(uua2, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter uua3Report = new JSCATSStatisticsReporter(uua3, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        JSCATSStatisticsReporter uua4Report = new JSCATSStatisticsReporter(uua4, machine, new ArrayList<>(abstractionPredicates), eventNames, 64);
        testAndSave("phone", "2post", eua0, eua1, eua2, eua3, eua4, uua0, uua1, uua2, uua3, uua4, eua0Report, eua1Report, eua2Report, eua3Report, eua4Report, uua0Report, uua1Report, uua2Report, uua3Report, uua4Report);
    }

    @Test
    public void test_carAlarm1guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/carAlarm/carAlarm.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/carAlarm/carAlarm_1guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("Bell_Activation", "Doors_Opening", "User_Unauthorized", "User_Authorized"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATS eua2 = new EUAComputer(machine, abstractStates, true).compute();
        JSCATS eua3 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50)).compute();
        JSCATS eua4 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50), true).compute();
        JSCATS uua0 = new OldUUAComputer(machine, eua0).compute();
        JSCATS uua1 = new UUAComputer(machine, eua1).compute();
        JSCATS uua2 = new UUAComputer(machine, eua2).compute();
        JSCATS uua3 = new UUAComputer(machine, eua3).compute();
        JSCATS uua4 = new UUAComputer(machine, eua4).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter eua2Report = new JSCATSStatisticsReporter(eua2, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter eua3Report = new JSCATSStatisticsReporter(eua3, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter eua4Report = new JSCATSStatisticsReporter(eua4, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter uua0Report = new JSCATSStatisticsReporter(uua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter uua1Report = new JSCATSStatisticsReporter(uua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter uua2Report = new JSCATSStatisticsReporter(uua2, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter uua3Report = new JSCATSStatisticsReporter(uua3, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter uua4Report = new JSCATSStatisticsReporter(uua4, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        testAndSave("carAlarm", "1guard", eua0, eua1, eua2, eua3, eua4, uua0, uua1, uua2, uua3, uua4, eua0Report, eua1Report, eua2Report, eua3Report, eua4Report, uua0Report, uua1Report, uua2Report, uua3Report, uua4Report);
    }

    @Test
    public void test_carAlarm2guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/carAlarm/carAlarm.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/carAlarm/carAlarm_2guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("Lock_Doors", "UnLock_Doors"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATS eua2 = new EUAComputer(machine, abstractStates, true).compute();
        JSCATS eua3 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50)).compute();
        JSCATS eua4 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50), true).compute();
        JSCATS uua0 = new OldUUAComputer(machine, eua0).compute();
        JSCATS uua1 = new UUAComputer(machine, eua1).compute();
        JSCATS uua2 = new UUAComputer(machine, eua2).compute();
        JSCATS uua3 = new UUAComputer(machine, eua3).compute();
        JSCATS uua4 = new UUAComputer(machine, eua4).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter eua2Report = new JSCATSStatisticsReporter(eua2, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter eua3Report = new JSCATSStatisticsReporter(eua3, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter eua4Report = new JSCATSStatisticsReporter(eua4, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter uua0Report = new JSCATSStatisticsReporter(uua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter uua1Report = new JSCATSStatisticsReporter(uua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter uua2Report = new JSCATSStatisticsReporter(uua2, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter uua3Report = new JSCATSStatisticsReporter(uua3, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter uua4Report = new JSCATSStatisticsReporter(uua4, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        testAndSave("carAlarm", "2guard", eua0, eua1, eua2, eua3, eua4, uua0, uua1, uua2, uua3, uua4, eua0Report, eua1Report, eua2Report, eua3Report, eua4Report, uua0Report, uua1Report, uua2Report, uua3Report, uua4Report);
    }

    @Test
    public void test_carAlarm1post() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/carAlarm/carAlarm.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/carAlarm/carAlarm_1post.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("Bell_Activation", "Doors_Opening", "User_Unauthorized", "User_Authorized"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATS eua2 = new EUAComputer(machine, abstractStates, true).compute();
        JSCATS eua3 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50)).compute();
        JSCATS eua4 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50), true).compute();
        JSCATS uua0 = new OldUUAComputer(machine, eua0).compute();
        JSCATS uua1 = new UUAComputer(machine, eua1).compute();
        JSCATS uua2 = new UUAComputer(machine, eua2).compute();
        JSCATS uua3 = new UUAComputer(machine, eua3).compute();
        JSCATS uua4 = new UUAComputer(machine, eua4).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter eua2Report = new JSCATSStatisticsReporter(eua2, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter eua3Report = new JSCATSStatisticsReporter(eua3, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter eua4Report = new JSCATSStatisticsReporter(eua4, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter uua0Report = new JSCATSStatisticsReporter(uua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter uua1Report = new JSCATSStatisticsReporter(uua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter uua2Report = new JSCATSStatisticsReporter(uua2, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter uua3Report = new JSCATSStatisticsReporter(uua3, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter uua4Report = new JSCATSStatisticsReporter(uua4, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        testAndSave("carAlarm", "1post", eua0, eua1, eua2, eua3, eua4, uua0, uua1, uua2, uua3, uua4, eua0Report, eua1Report, eua2Report, eua3Report, eua4Report, uua0Report, uua1Report, uua2Report, uua3Report, uua4Report);
    }

    @Test
    public void test_carAlarm2post() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/carAlarm/carAlarm.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/carAlarm/carAlarm_2post.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("Lock_Doors", "UnLock_Doors"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATS eua2 = new EUAComputer(machine, abstractStates, true).compute();
        JSCATS eua3 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50)).compute();
        JSCATS eua4 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50), true).compute();
        JSCATS uua0 = new OldUUAComputer(machine, eua0).compute();
        JSCATS uua1 = new UUAComputer(machine, eua1).compute();
        JSCATS uua2 = new UUAComputer(machine, eua2).compute();
        JSCATS uua3 = new UUAComputer(machine, eua3).compute();
        JSCATS uua4 = new UUAComputer(machine, eua4).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter eua2Report = new JSCATSStatisticsReporter(eua2, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter eua3Report = new JSCATSStatisticsReporter(eua3, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter eua4Report = new JSCATSStatisticsReporter(eua4, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter uua0Report = new JSCATSStatisticsReporter(uua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter uua1Report = new JSCATSStatisticsReporter(uua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter uua2Report = new JSCATSStatisticsReporter(uua2, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter uua3Report = new JSCATSStatisticsReporter(uua3, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        JSCATSStatisticsReporter uua4Report = new JSCATSStatisticsReporter(uua4, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 15));
        testAndSave("carAlarm", "2post", eua0, eua1, eua2, eua3, eua4, uua0, uua1, uua2, uua3, uua4, eua0Report, eua1Report, eua2Report, eua3Report, eua4Report, uua0Report, uua1Report, uua2Report, uua3Report, uua4Report);
    }

    @Test
    public void test_coffeeMachine1guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/coffeeMachine/coffeeMachine.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/coffeeMachine/coffeeMachine_1guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Collections.singletonList("ServeCoffee"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATS eua2 = new EUAComputer(machine, abstractStates, true).compute();
        JSCATS eua3 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50)).compute();
        JSCATS eua4 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50), true).compute();
        JSCATS uua0 = new OldUUAComputer(machine, eua0).compute();
        JSCATS uua1 = new UUAComputer(machine, eua1).compute();
        JSCATS uua2 = new UUAComputer(machine, eua2).compute();
        JSCATS uua3 = new UUAComputer(machine, eua3).compute();
        JSCATS uua4 = new UUAComputer(machine, eua4).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter eua2Report = new JSCATSStatisticsReporter(eua2, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter eua3Report = new JSCATSStatisticsReporter(eua3, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter eua4Report = new JSCATSStatisticsReporter(eua4, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter uua0Report = new JSCATSStatisticsReporter(uua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter uua1Report = new JSCATSStatisticsReporter(uua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter uua2Report = new JSCATSStatisticsReporter(uua2, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter uua3Report = new JSCATSStatisticsReporter(uua3, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter uua4Report = new JSCATSStatisticsReporter(uua4, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        testAndSave("coffeeMachine", "1guard", eua0, eua1, eua2, eua3, eua4, uua0, uua1, uua2, uua3, uua4, eua0Report, eua1Report, eua2Report, eua3Report, eua4Report, uua0Report, uua1Report, uua2Report, uua3Report, uua4Report);
    }

    @Test
    public void test_coffeeMachine2guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/coffeeMachine/coffeeMachine.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/coffeeMachine/coffeeMachine_2guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("takeMago", "AutoOut", "powerDown"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATS eua2 = new EUAComputer(machine, abstractStates, true).compute();
        JSCATS eua3 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50)).compute();
        JSCATS eua4 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50), true).compute();
        JSCATS uua0 = new OldUUAComputer(machine, eua0).compute();
        JSCATS uua1 = new UUAComputer(machine, eua1).compute();
        JSCATS uua2 = new UUAComputer(machine, eua2).compute();
        JSCATS uua3 = new UUAComputer(machine, eua3).compute();
        JSCATS uua4 = new UUAComputer(machine, eua4).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter eua2Report = new JSCATSStatisticsReporter(eua2, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter eua3Report = new JSCATSStatisticsReporter(eua3, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter eua4Report = new JSCATSStatisticsReporter(eua4, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter uua0Report = new JSCATSStatisticsReporter(uua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter uua1Report = new JSCATSStatisticsReporter(uua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter uua2Report = new JSCATSStatisticsReporter(uua2, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter uua3Report = new JSCATSStatisticsReporter(uua3, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter uua4Report = new JSCATSStatisticsReporter(uua4, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        testAndSave("coffeeMachine", "2guard", eua0, eua1, eua2, eua3, eua4, uua0, uua1, uua2, uua3, uua4, eua0Report, eua1Report, eua2Report, eua3Report, eua4Report, uua0Report, uua1Report, uua2Report, uua3Report, uua4Report);
    }

    @Test
    public void test_coffeeMachine1post() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/coffeeMachine/coffeeMachine.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/coffeeMachine/coffeeMachine_1post.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Collections.singletonList("ServeCoffee"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATS eua2 = new EUAComputer(machine, abstractStates, true).compute();
        JSCATS eua3 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50)).compute();
        JSCATS eua4 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50), true).compute();
        JSCATS uua0 = new OldUUAComputer(machine, eua0).compute();
        JSCATS uua1 = new UUAComputer(machine, eua1).compute();
        JSCATS uua2 = new UUAComputer(machine, eua2).compute();
        JSCATS uua3 = new UUAComputer(machine, eua3).compute();
        JSCATS uua4 = new UUAComputer(machine, eua4).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter eua2Report = new JSCATSStatisticsReporter(eua2, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter eua3Report = new JSCATSStatisticsReporter(eua3, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter eua4Report = new JSCATSStatisticsReporter(eua4, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter uua0Report = new JSCATSStatisticsReporter(uua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter uua1Report = new JSCATSStatisticsReporter(uua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter uua2Report = new JSCATSStatisticsReporter(uua2, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter uua3Report = new JSCATSStatisticsReporter(uua3, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter uua4Report = new JSCATSStatisticsReporter(uua4, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        testAndSave("coffeeMachine", "1post", eua0, eua1, eua2, eua3, eua4, uua0, uua1, uua2, uua3, uua4, eua0Report, eua1Report, eua2Report, eua3Report, eua4Report, uua0Report, uua1Report, uua2Report, uua3Report, uua4Report);
    }

    @Test
    public void test_coffeeMachine2post() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("resources/eventb/coffeeMachine/coffeeMachine.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/coffeeMachine/coffeeMachine_2post.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("takeMago", "AutoOut", "powerDown"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATS eua2 = new EUAComputer(machine, abstractStates, true).compute();
        JSCATS eua3 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50)).compute();
        JSCATS eua4 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50), true).compute();
        JSCATS uua0 = new OldUUAComputer(machine, eua0).compute();
        JSCATS uua1 = new UUAComputer(machine, eua1).compute();
        JSCATS uua2 = new UUAComputer(machine, eua2).compute();
        JSCATS uua3 = new UUAComputer(machine, eua3).compute();
        JSCATS uua4 = new UUAComputer(machine, eua4).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter eua2Report = new JSCATSStatisticsReporter(eua2, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter eua3Report = new JSCATSStatisticsReporter(eua3, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter eua4Report = new JSCATSStatisticsReporter(eua4, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter uua0Report = new JSCATSStatisticsReporter(uua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter uua1Report = new JSCATSStatisticsReporter(uua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter uua2Report = new JSCATSStatisticsReporter(uua2, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter uua3Report = new JSCATSStatisticsReporter(uua3, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        JSCATSStatisticsReporter uua4Report = new JSCATSStatisticsReporter(uua4, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 16));
        testAndSave("coffeeMachine", "2post", eua0, eua1, eua2, eua3, eua4, uua0, uua1, uua2, uua3, uua4, eua0Report, eua1Report, eua2Report, eua3Report, eua4Report, uua0Report, uua1Report, uua2Report, uua3Report, uua4Report);
    }

    @Test
    public void test_frontWiper1guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("/home/gvoiron/IdeaProjects/stratest/resources/eventb/frontWiper/frontWiper.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/frontWiper/frontWiper_1guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("power_down", "wash_action_begin", "wash_action_end"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATS eua2 = new EUAComputer(machine, abstractStates, true).compute();
        JSCATS eua3 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50)).compute();
        JSCATS eua4 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50), true).compute();
        JSCATS uua0 = new OldUUAComputer(machine, eua0).compute();
        JSCATS uua1 = new UUAComputer(machine, eua1).compute();
        JSCATS uua2 = new UUAComputer(machine, eua2).compute();
        JSCATS uua3 = new UUAComputer(machine, eua3).compute();
        JSCATS uua4 = new UUAComputer(machine, eua4).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 27));
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 27));
        JSCATSStatisticsReporter eua2Report = new JSCATSStatisticsReporter(eua2, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 27));
        JSCATSStatisticsReporter eua3Report = new JSCATSStatisticsReporter(eua3, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 27));
        JSCATSStatisticsReporter eua4Report = new JSCATSStatisticsReporter(eua4, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 27));
        JSCATSStatisticsReporter uua0Report = new JSCATSStatisticsReporter(uua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 27));
        JSCATSStatisticsReporter uua1Report = new JSCATSStatisticsReporter(uua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 27));
        JSCATSStatisticsReporter uua2Report = new JSCATSStatisticsReporter(uua2, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 27));
        JSCATSStatisticsReporter uua3Report = new JSCATSStatisticsReporter(uua3, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 27));
        JSCATSStatisticsReporter uua4Report = new JSCATSStatisticsReporter(uua4, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 27));
        testAndSave("frontWiper", "1guard", eua0, eua1, eua2, eua3, eua4, uua0, uua1, uua2, uua3, uua4, eua0Report, eua1Report, eua2Report, eua3Report, eua4Report, uua0Report, uua1Report, uua2Report, uua3Report, uua4Report);
    }

    @Test
    public void test_frontWiper2guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("/home/gvoiron/IdeaProjects/stratest/resources/eventb/frontWiper/frontWiper.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/frontWiper/frontWiper_2guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("block_FW_engine", "action_commodo_down"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATS eua2 = new EUAComputer(machine, abstractStates, true).compute();
        JSCATS eua3 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50)).compute();
        JSCATS eua4 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50), true).compute();
        JSCATS uua0 = new OldUUAComputer(machine, eua0).compute();
        JSCATS uua1 = new UUAComputer(machine, eua1).compute();
        JSCATS uua2 = new UUAComputer(machine, eua2).compute();
        JSCATS uua3 = new UUAComputer(machine, eua3).compute();
        JSCATS uua4 = new UUAComputer(machine, eua4).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 27));
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 27));
        JSCATSStatisticsReporter eua2Report = new JSCATSStatisticsReporter(eua2, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 27));
        JSCATSStatisticsReporter eua3Report = new JSCATSStatisticsReporter(eua3, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 27));
        JSCATSStatisticsReporter eua4Report = new JSCATSStatisticsReporter(eua4, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 27));
        JSCATSStatisticsReporter uua0Report = new JSCATSStatisticsReporter(uua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 27));
        JSCATSStatisticsReporter uua1Report = new JSCATSStatisticsReporter(uua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 27));
        JSCATSStatisticsReporter uua2Report = new JSCATSStatisticsReporter(uua2, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 27));
        JSCATSStatisticsReporter uua3Report = new JSCATSStatisticsReporter(uua3, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 27));
        JSCATSStatisticsReporter uua4Report = new JSCATSStatisticsReporter(uua4, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 27));
        testAndSave("frontWiper", "2guard", eua0, eua1, eua2, eua3, eua4, uua0, uua1, uua2, uua3, uua4, eua0Report, eua1Report, eua2Report, eua3Report, eua4Report, uua0Report, uua1Report, uua2Report, uua3Report, uua4Report);
    }

    @Test
    public void test_creditCard1guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("/home/gvoiron/IdeaProjects/stratest/resources/eventb/creditCard/creditCard.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/creditCard/creditCard_1guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("CARD_failed_pin", "CARD_process_pin"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATS eua2 = new EUAComputer(machine, abstractStates, true).compute();
        JSCATS eua3 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50)).compute();
        JSCATS eua4 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50), true).compute();
        JSCATS uua0 = new OldUUAComputer(machine, eua0).compute();
        JSCATS uua1 = new UUAComputer(machine, eua1).compute();
        JSCATS uua2 = new UUAComputer(machine, eua2).compute();
        JSCATS uua3 = new UUAComputer(machine, eua3).compute();
        JSCATS uua4 = new UUAComputer(machine, eua4).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter eua2Report = new JSCATSStatisticsReporter(eua2, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter eua3Report = new JSCATSStatisticsReporter(eua3, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter eua4Report = new JSCATSStatisticsReporter(eua4, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter uua0Report = new JSCATSStatisticsReporter(uua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter uua1Report = new JSCATSStatisticsReporter(uua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter uua2Report = new JSCATSStatisticsReporter(uua2, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter uua3Report = new JSCATSStatisticsReporter(uua3, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter uua4Report = new JSCATSStatisticsReporter(uua4, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        testAndSave("creditCard", "1guard", eua0, eua1, eua2, eua3, eua4, uua0, uua1, uua2, uua3, uua4, eua0Report, eua1Report, eua2Report, eua3Report, eua4Report, uua0Report, uua1Report, uua2Report, uua3Report, uua4Report);
    }

    @Test
    public void test_creditCard2guard() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("/home/gvoiron/IdeaProjects/stratest/resources/eventb/creditCard/creditCard.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/creditCard/creditCard_2guard.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("DB_ok", "CARD_success_pin", "DB_operation_not_done"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATS eua2 = new EUAComputer(machine, abstractStates, true).compute();
        JSCATS eua3 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50)).compute();
        JSCATS eua4 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50), true).compute();
        JSCATS uua0 = new OldUUAComputer(machine, eua0).compute();
        JSCATS uua1 = new UUAComputer(machine, eua1).compute();
        JSCATS uua2 = new UUAComputer(machine, eua2).compute();
        JSCATS uua3 = new UUAComputer(machine, eua3).compute();
        JSCATS uua4 = new UUAComputer(machine, eua4).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter eua2Report = new JSCATSStatisticsReporter(eua2, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter eua3Report = new JSCATSStatisticsReporter(eua3, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter eua4Report = new JSCATSStatisticsReporter(eua4, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter uua0Report = new JSCATSStatisticsReporter(uua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter uua1Report = new JSCATSStatisticsReporter(uua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter uua2Report = new JSCATSStatisticsReporter(uua2, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter uua3Report = new JSCATSStatisticsReporter(uua3, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter uua4Report = new JSCATSStatisticsReporter(uua4, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        testAndSave("creditCard", "2guard", eua0, eua1, eua2, eua3, eua4, uua0, uua1, uua2, uua3, uua4, eua0Report, eua1Report, eua2Report, eua3Report, eua4Report, uua0Report, uua1Report, uua2Report, uua3Report, uua4Report);
    }

    @Test
    public void test_creditCard1post() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("/home/gvoiron/IdeaProjects/stratest/resources/eventb/creditCard/creditCard.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/creditCard/creditCard_1post.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("CARD_failed_pin", "CARD_process_pin"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATS eua2 = new EUAComputer(machine, abstractStates, true).compute();
        JSCATS eua3 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50)).compute();
        JSCATS eua4 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50), true).compute();
        JSCATS uua0 = new OldUUAComputer(machine, eua0).compute();
        JSCATS uua1 = new UUAComputer(machine, eua1).compute();
        JSCATS uua2 = new UUAComputer(machine, eua2).compute();
        JSCATS uua3 = new UUAComputer(machine, eua3).compute();
        JSCATS uua4 = new UUAComputer(machine, eua4).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter eua2Report = new JSCATSStatisticsReporter(eua2, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter eua3Report = new JSCATSStatisticsReporter(eua3, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter eua4Report = new JSCATSStatisticsReporter(eua4, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter uua0Report = new JSCATSStatisticsReporter(uua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter uua1Report = new JSCATSStatisticsReporter(uua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter uua2Report = new JSCATSStatisticsReporter(uua2, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter uua3Report = new JSCATSStatisticsReporter(uua3, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter uua4Report = new JSCATSStatisticsReporter(uua4, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        testAndSave("creditCard", "1post", eua0, eua1, eua2, eua3, eua4, uua0, uua1, uua2, uua3, uua4, eua0Report, eua1Report, eua2Report, eua3Report, eua4Report, uua0Report, uua1Report, uua2Report, uua3Report, uua4Report);
    }

    @Test
    public void test_creditCard2post() throws BParserException, InvocationTargetException, AfterParserException, IllegalAccessException, NoSuchMethodException, IOException, NoSuchFieldException {
        Machine machine = new EBMParser().parseMachine(new File("/home/gvoiron/IdeaProjects/stratest/resources/eventb/creditCard/creditCard.ebm"));
        Set<Predicate> abstractionPredicates = new LinkedHashSet<>(new EBMParser().parseAbstractionPredicates(new File("resources/eventb/creditCard/creditCard_2post.ap")));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates.stream().collect(Collectors.toList()));
        Set<String> eventNames = new LinkedHashSet<>(Arrays.asList("DB_ok", "CARD_success_pin", "DB_operation_not_done"));
        JSCATS eua0 = new OldEUAComputer(machine, abstractStates).compute();
        JSCATS eua1 = new EUAComputer(machine, abstractStates).compute();
        JSCATS eua2 = new EUAComputer(machine, abstractStates, true).compute();
        JSCATS eua3 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50)).compute();
        JSCATS eua4 = new EUAComputer(machine, abstractStates, new UniversalRelevancyChecker(50), true).compute();
        JSCATS uua0 = new OldUUAComputer(machine, eua0).compute();
        JSCATS uua1 = new UUAComputer(machine, eua1).compute();
        JSCATS uua2 = new UUAComputer(machine, eua2).compute();
        JSCATS uua3 = new UUAComputer(machine, eua3).compute();
        JSCATS uua4 = new UUAComputer(machine, eua4).compute();
        JSCATSStatisticsReporter eua0Report = new JSCATSStatisticsReporter(eua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter eua1Report = new JSCATSStatisticsReporter(eua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter eua2Report = new JSCATSStatisticsReporter(eua2, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter eua3Report = new JSCATSStatisticsReporter(eua3, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter eua4Report = new JSCATSStatisticsReporter(eua4, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter uua0Report = new JSCATSStatisticsReporter(uua0, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter uua1Report = new JSCATSStatisticsReporter(uua1, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter uua2Report = new JSCATSStatisticsReporter(uua2, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter uua3Report = new JSCATSStatisticsReporter(uua3, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        JSCATSStatisticsReporter uua4Report = new JSCATSStatisticsReporter(uua4, machine, new ArrayList<>(abstractionPredicates), eventNames, (int) Math.pow(2, 120));
        testAndSave("creditCard", "2post", eua0, eua1, eua2, eua3, eua4, uua0, uua1, uua2, uua3, uua4, eua0Report, eua1Report, eua2Report, eua3Report, eua4Report, uua0Report, uua1Report, uua2Report, uua3Report, uua4Report);
    }

}