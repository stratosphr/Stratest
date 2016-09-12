package algorithms.outputs;

import algorithms.tools.AbstractStatesComputer;
import algorithms.tools.EStateColor;
import algorithms.tools.TestsGenerator;
import eventb.Machine;
import eventb.expressions.bool.Predicate;
import graphs.*;
import utilities.UCharacters;
import utilities.UMath;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 25/08/16.
 * Time: 11:12
 */
public final class JSCATSStatisticsReporter extends AStatisticsReporter {

    private final int size;
    private final int nbAbstractionPredicates;
    private final int nbTests;
    private final double averageTestsLength;
    private final double testsLengthStandardDeviation;
    private final int nbAbstractStates;
    private final int nbAbstractTransitions;
    private final int nbMustMinusTransitions;
    private final int nbMustPlusTransitions;
    private final int nbMustSharpTransitions;
    private final int nbPureMayTransitions;
    private final int nbConcreteStates;
    private final int nbConcreteTransitions;
    private final int nbUniqueConcreteStatesInTests;
    private final int nbUniqueConcreteTransitionsInTests;
    private final int nbEventsInTestPurpose;
    private final int nbEventsInTestPurposeFiredInTests;
    private final int nbBlueStates;
    private final int nbGreenStates;
    private final int nbBlueTransitions;
    private final int nbGreenTransitions;
    private final int nbAbstractStatesInTests;
    private final int nbAbstractTransitionsInTests;
    private final List<Test> tests;
    private final double computationTime;

    public JSCATSStatisticsReporter(JSCATS jscats, Machine machine, List<Predicate> abstractionPredicates, Set<String> testPurposeEventNames) {
        List<Test> tests = TestsGenerator.generateTests(jscats);
        LinkedHashSet<ConcreteTransition> flattenedTestsTransitions = tests.stream().flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
        LinkedHashSet<ConcreteState> flattenedTestsStates = flattenedTestsTransitions.stream().map(concreteTransition -> Arrays.asList(concreteTransition.getSource(), concreteTransition.getTarget())).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
        List<AbstractState> abstractStates = AbstractStatesComputer.computeAbstractStates(machine, abstractionPredicates);
        List<AbstractTransition> mustSharpTransitions = jscats.getDeltaMinus().stream().filter(abstractTransition -> jscats.getDeltaPlus().contains(abstractTransition)).collect(Collectors.toList());
        this.tests = tests;
        this.size = -1;
        this.nbAbstractionPredicates = abstractionPredicates.size();
        this.nbTests = tests.size();
        this.averageTestsLength = tests.stream().mapToInt(ArrayList::size).average().orElse(0);
        this.testsLengthStandardDeviation = UMath.getStandardDeviation(tests);
        this.nbAbstractStates = abstractStates.size();
        this.nbAbstractTransitions = jscats.getDelta().size();
        this.nbMustMinusTransitions = jscats.getDeltaMinus().size();
        this.nbMustPlusTransitions = jscats.getDeltaPlus().size();
        this.nbMustSharpTransitions = mustSharpTransitions.size();
        this.nbPureMayTransitions = jscats.getDelta().stream().filter(abstractTransition -> !jscats.getDeltaMinus().contains(abstractTransition) && !jscats.getDeltaPlus().contains(abstractTransition)).collect(Collectors.toList()).size();
        this.nbConcreteStates = jscats.getC().size();
        this.nbConcreteTransitions = jscats.getDeltaC().size();
        this.nbUniqueConcreteStatesInTests = flattenedTestsStates.size();
        this.nbUniqueConcreteTransitionsInTests = flattenedTestsTransitions.size();
        this.nbEventsInTestPurpose = testPurposeEventNames.size();
        this.nbEventsInTestPurposeFiredInTests = testPurposeEventNames.stream().filter(eventName -> flattenedTestsTransitions.stream().anyMatch(concreteTransition -> concreteTransition.getEvent().getName().equals(eventName))).collect(Collectors.toList()).size();
        this.nbBlueStates = jscats.getKappa().values().stream().filter(stateColor -> stateColor == EStateColor.BLUE).collect(Collectors.toList()).size();
        this.nbGreenStates = jscats.getKappa().values().stream().filter(stateColor -> stateColor == EStateColor.GREEN).collect(Collectors.toList()).size();
        this.nbBlueTransitions = flattenedTestsTransitions.stream().filter(concreteTransition -> jscats.getKappa().get(concreteTransition.getSource()) == EStateColor.BLUE).collect(Collectors.toList()).size();
        this.nbGreenTransitions = flattenedTestsTransitions.stream().filter(concreteTransition -> jscats.getKappa().get(concreteTransition.getSource()) == EStateColor.GREEN).collect(Collectors.toList()).size();
        this.nbAbstractStatesInTests = flattenedTestsStates.stream().map(concreteState -> jscats.getAlpha().get(concreteState)).collect(Collectors.toSet()).size();
        this.nbAbstractTransitionsInTests = flattenedTestsTransitions.stream().map(concreteTransition -> new AbstractTransition(jscats.getAlpha().get(concreteTransition.getSource()), concreteTransition.getEvent(), jscats.getAlpha().get(concreteTransition.getTarget()))).collect(Collectors.toSet()).size();
        this.computationTime = jscats.getComputationTime();
    }

    public List<Test> getTests() {
        return tests;
    }

    public int getSize() {
        return size;
    }

    public int getNbAbstractionPredicates() {
        return nbAbstractionPredicates;
    }

    public int getNbTests() {
        return nbTests;
    }

    public double getAverageTestsLength() {
        return averageTestsLength;
    }

    public double getTestsLengthStandardDeviation() {
        return testsLengthStandardDeviation;
    }

    public int getNbAbstractStates() {
        return nbAbstractStates;
    }

    public int getNbAbstractTransitions() {
        return nbAbstractTransitions;
    }

    public int getNbMustMinusTransitions() {
        return nbMustMinusTransitions;
    }

    public int getNbMustPlusTransitions() {
        return nbMustPlusTransitions;
    }

    public int getNbMustSharpTransitions() {
        return nbMustSharpTransitions;
    }

    public int getNbPureMayTransitions() {
        return nbPureMayTransitions;
    }

    public int getNbConcreteStates() {
        return nbConcreteStates;
    }

    public int getNbConcreteTransitions() {
        return nbConcreteTransitions;
    }

    public int getNbUniqueConcreteStatesInTests() {
        return nbUniqueConcreteStatesInTests;
    }

    public int getNbUniqueConcreteTransitionsInTests() {
        return nbUniqueConcreteTransitionsInTests;
    }

    public int getNbEventsInTestPurpose() {
        return nbEventsInTestPurpose;
    }

    public int getNbEventsInTestPurposeFiredInTests() {
        return nbEventsInTestPurposeFiredInTests;
    }

    public int getNbBlueStates() {
        return nbBlueStates;
    }

    public int getNbGreenStates() {
        return nbGreenStates;
    }

    public int getNbBlueTransitions() {
        return nbBlueTransitions;
    }

    public int getNbGreenTransitions() {
        return nbGreenTransitions;
    }

    public int getNbAbstractStatesInTests() {
        return nbAbstractStatesInTests;
    }

    public int getNbAbstractTransitionsInTests() {
        return nbAbstractTransitionsInTests;
    }

    public double getComputationTime() {
        return computationTime;
    }

    @Override
    public String toString() {
        String str = "";
        str += "System size: " + getSize() + UCharacters.LINE_SEPARATOR;
        str += "#AbstractionPredicates: " + getNbAbstractionPredicates() + UCharacters.LINE_SEPARATOR;
        str += "#Tests: " + getNbTests() + UCharacters.LINE_SEPARATOR;
        str += "AverageTestsLength: " + getAverageTestsLength() + UCharacters.LINE_SEPARATOR;
        str += "TestsLengthStandardDeviation: " + getTestsLengthStandardDeviation() + UCharacters.LINE_SEPARATOR;
        str += "#AbstractStates: " + getNbAbstractStates() + UCharacters.LINE_SEPARATOR;
        str += "#AbstractTransitions: " + getNbAbstractTransitions() + UCharacters.LINE_SEPARATOR;
        str += "#MustMinusTransitions: " + getNbMustMinusTransitions() + UCharacters.LINE_SEPARATOR;
        str += "#MustPlusTransitions: " + getNbMustPlusTransitions() + UCharacters.LINE_SEPARATOR;
        str += "#MustSharpTransitions: " + getNbMustSharpTransitions() + UCharacters.LINE_SEPARATOR;
        str += "#PureMayTransitions: " + getNbPureMayTransitions() + UCharacters.LINE_SEPARATOR;
        str += "#ConcreteStates: " + getNbConcreteStates() + UCharacters.LINE_SEPARATOR;
        str += "#ConcreteTransitions: " + getNbConcreteTransitions() + UCharacters.LINE_SEPARATOR;
        str += "#UniqueConcreteStatesInTests: " + getNbUniqueConcreteStatesInTests() + UCharacters.LINE_SEPARATOR;
        str += "#UniqueConcreteTransitionsInTests: " + getNbUniqueConcreteTransitionsInTests() + UCharacters.LINE_SEPARATOR;
        str += "#EventsInTestPurpose: " + getNbEventsInTestPurpose() + UCharacters.LINE_SEPARATOR;
        str += "#EventsInTestPurposeFiredInTests: " + getNbEventsInTestPurposeFiredInTests() + UCharacters.LINE_SEPARATOR;
        str += "#BlueStates: " + getNbBlueStates() + UCharacters.LINE_SEPARATOR;
        str += "#GreenStates: " + getNbGreenStates() + UCharacters.LINE_SEPARATOR;
        str += "#BlueTransitions: " + getNbBlueTransitions() + UCharacters.LINE_SEPARATOR;
        str += "#GreenTransitions: " + getNbGreenTransitions() + UCharacters.LINE_SEPARATOR;
        str += "#AbstractStatesInTests: " + getNbAbstractStatesInTests() + UCharacters.LINE_SEPARATOR;
        str += "#AbstractTransitionsInTests: " + getNbAbstractTransitionsInTests() + UCharacters.LINE_SEPARATOR;
        str += "Time: " + getComputationTime() + "s" + UCharacters.LINE_SEPARATOR;
        str += getTests().stream().map(test -> test.stream().map(ATransition::toString).collect(Collectors.joining(UCharacters.LINE_SEPARATOR))).collect(Collectors.joining(UCharacters.LINE_SEPARATOR + "-----------------------------------" + UCharacters.LINE_SEPARATOR, "-----------------------------------" + UCharacters.LINE_SEPARATOR, "")) + UCharacters.LINE_SEPARATOR + "-----------------------------------" + UCharacters.LINE_SEPARATOR;
        return str;
    }

}
