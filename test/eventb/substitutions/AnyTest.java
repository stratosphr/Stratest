package eventb.substitutions;

import eventb.expressions.arith.Int;
import eventb.expressions.arith.Variable;
import eventb.expressions.bool.ABooleanExpression;
import eventb.expressions.bool.And;
import eventb.expressions.bool.Equals;
import eventb.expressions.bool.ForAll;
import eventb.tools.formatters.EventBFormatter;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static utilities.UCharacters.*;

/**
 * Created by gvoiron on 14/08/16.
 * Time : 16:41
 */
public class AnyTest {

    @Test
    public void test_acceptEventBFormatter() {
        Variable v1 = new Variable("v1");
        Variable v2 = new Variable("v2");
        Int fortyTwo = new Int(42);
        And and = new And(new Equals(v1, fortyTwo), new Equals(v1, v2));
        Skip skip = new Skip();
        Any any = new Any(and, skip, v1);
        Assert.assertEquals("" + "ANY" + LINE_SEPARATOR + TABULATION + "v1" + LINE_SEPARATOR + "WHERE" + LINE_SEPARATOR + TABULATION + "((v1 = 42) " + LAND + " (v1 = v2))" + LINE_SEPARATOR + "THEN" + LINE_SEPARATOR + TABULATION + "SKIP" + LINE_SEPARATOR + "END", any.accept(new EventBFormatter()));
        any = new Any(and, skip, v1, v2);
        Assert.assertEquals("" + "ANY" + LINE_SEPARATOR + TABULATION + "v1, v2" + LINE_SEPARATOR + "WHERE" + LINE_SEPARATOR + TABULATION + "((v1 = 42) " + LAND + " (v1 = v2))" + LINE_SEPARATOR + "THEN" + LINE_SEPARATOR + TABULATION + "SKIP" + LINE_SEPARATOR + "END", any.accept(new EventBFormatter()));
    }

    @Test
    public void test_getWP() {
        Variable v1 = new Variable("v1");
        Variable v2 = new Variable("v2");
        Int fortyTwo = new Int(42);
        And and = new And(new Equals(v1, fortyTwo), new Equals(v1, v2));
        Assignment assignment = new Assignment(v1, fortyTwo);
        Select select = new Select(and, assignment);
        Any any = new Any(and, assignment, v1);
        ABooleanExpression postCondition = new Equals(v1, v2);
        ForAll forAll = new ForAll(select.getWP(postCondition), v1);
        Assert.assertEquals(forAll, any.getWP(postCondition));
        any = new Any(and, assignment, v1, v2);
        forAll = new ForAll(select.getWP(postCondition), v1, v2);
        Assert.assertEquals(forAll, any.getWP(postCondition));
        System.out.println(forAll);
    }

    @Test
    public void test_getWherePart() {
        Variable v1 = new Variable("v1");
        Variable v2 = new Variable("v2");
        Int fortyTwo = new Int(42);
        And and = new And(new Equals(v1, fortyTwo), new Equals(v1, v2));
        Assignment assignment = new Assignment(v1, fortyTwo);
        Any any = new Any(and, assignment, v1);
        Assert.assertEquals(and, any.getWherePart());
    }

    @Test
    public void test_getThenPart() {
        Variable v1 = new Variable("v1");
        Variable v2 = new Variable("v2");
        Int fortyTwo = new Int(42);
        And and = new And(new Equals(v1, fortyTwo), new Equals(v1, v2));
        Assignment assignment = new Assignment(v1, fortyTwo);
        Any any = new Any(and, assignment, v1);
        Assert.assertEquals(assignment, any.getThenPart());
    }

    @Test
    public void test_getQuantifiedVariables() {
        Variable v1 = new Variable("v1");
        Variable v2 = new Variable("v2");
        Int fortyTwo = new Int(42);
        And and = new And(new Equals(v1, fortyTwo), new Equals(v1, v2));
        Assignment assignment = new Assignment(v1, fortyTwo);
        Any any = new Any(and, assignment, v1, v2);
        Assert.assertEquals(Arrays.asList(v1, v2), any.getQuantifiedVariables());
    }

}