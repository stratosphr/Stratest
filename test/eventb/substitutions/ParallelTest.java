package eventb.substitutions;

import eventb.expressions.arith.Int;
import eventb.expressions.arith.Sum;
import eventb.expressions.arith.Variable;
import eventb.expressions.bool.ABooleanExpression;
import eventb.expressions.bool.Equals;
import eventb.tools.formatters.EventBFormatter;
import org.junit.Assert;
import org.junit.Test;
import utilities.UCharacters;

import java.util.Arrays;

/**
 * Created by gvoiron on 15/08/16.
 * Time : 11:38
 */
public class ParallelTest {

    @Test
    public void test_acceptEventBFormatter() {
        Int fortyTwo = new Int(42);
        Int twelve = new Int(12);
        Variable v1 = new Variable("v1");
        Variable v2 = new Variable("v2");
        Assignment assignment1 = new Assignment(v1, new Sum(fortyTwo, twelve));
        Assignment assignment2 = new Assignment(v2, new Sum(v1, twelve, fortyTwo));
        Skip skip = new Skip();
        Parallel parallel = new Parallel(assignment1, skip, assignment2);
        Assert.assertEquals("v1 := (42 + 12) ||" + UCharacters.LINE_SEPARATOR + "SKIP ||" + UCharacters.LINE_SEPARATOR + "v2 := (v1 + 12 + 42)", parallel.accept(new EventBFormatter()));
    }

    @Test
    public void test_getWP() {
        Int fortyTwo = new Int(42);
        Int twelve = new Int(12);
        Variable v1 = new Variable("v1");
        Variable v2 = new Variable("v2");
        Assignment assignment1 = new Assignment(v1, new Sum(fortyTwo, twelve));
        Assignment assignment2 = new Assignment(v2, new Sum(v1, twelve, fortyTwo));
        Skip skip = new Skip();
        Parallel parallel = new Parallel(assignment1, skip, assignment2);
        ASubstitution surrogate = new MultipleAssignment(assignment1, assignment2);
        ABooleanExpression postCondition = new Equals(v1, new Sum(v2, fortyTwo, twelve));
        Assert.assertEquals(surrogate.getWP(postCondition), parallel.getWP(postCondition));
    }

    @Test
    public void test_getSubstitutions() {
        Int fortyTwo = new Int(42);
        Int twelve = new Int(12);
        Variable v1 = new Variable("v1");
        Variable v2 = new Variable("v2");
        Assignment assignment1 = new Assignment(v1, new Sum(fortyTwo, twelve));
        Assignment assignment2 = new Assignment(v2, new Sum(v1, twelve, fortyTwo));
        Skip skip = new Skip();
        Parallel parallel = new Parallel(assignment1, skip, assignment2);
        Assert.assertEquals(Arrays.asList(assignment1, skip, assignment2), parallel.getSubstitutions());
    }

    @Test
    public void test_getSurrogate() {
        Int fortyTwo = new Int(42);
        Int twelve = new Int(12);
        Variable v1 = new Variable("v1");
        Variable v2 = new Variable("v2");
        Assignment assignment1 = new Assignment(v1, new Sum(fortyTwo, twelve));
        Assignment assignment2 = new Assignment(v2, new Sum(v1, twelve, fortyTwo));
        Parallel parallel = new Parallel(assignment1, assignment2);
        Assert.assertEquals(new MultipleAssignment(assignment1, assignment2), parallel.getSurrogate());
        parallel = new Parallel();
        Assert.assertEquals(new Skip(), parallel.getSurrogate());
    }

}