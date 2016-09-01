package algorithms.outputs;

import eventb.Machine;
import eventb.expressions.bool.Predicate;
import utilities.UMath;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by gvoiron on 25/08/16.
 * Time : 11:12
 */
public final class JSCATSStatisticsReporter extends AStatisticsReporter {

    private final int nbAbstractionPredicates;
    private final int nbTests;
    private final double avgTestsLength;

    public JSCATSStatisticsReporter(List<Test> tests, Machine machine, List<Predicate> abstractionPredicates, Set<String> testPurposeEventNames) {
        this.nbAbstractionPredicates = new LinkedHashSet<>(abstractionPredicates).size();
        this.nbTests = tests.size();
        this.avgTestsLength = tests.stream().mapToInt(ArrayList::size).average().orElseGet(() -> 0);
        tests.forEach(concreteTransitions -> {
            System.out.println("-------------------------------------");
            concreteTransitions.forEach(System.out::println);
            System.out.println("-------------------------------------");
        });
        System.out.println(avgTestsLength);
        System.out.println(UMath.getStandardDeviation(tests));
    }

}
