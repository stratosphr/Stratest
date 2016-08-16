package eventb.expressions.bool;

import eventb.expressions.arith.Subtraction;
import eventb.expressions.arith.Variable;
import eventb.tools.formatters.EventBFormatter;
import eventb.tools.formatters.ExpressionToSMTLib2Formatter;
import eventb.tools.replacer.AssignableReplacer;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashSet;

import static utilities.UCharacters.*;

/**
 * Created by gvoiron on 14/08/16.
 * Time : 18:09
 */
public class ForAllTest {

    @Test
    public void test_acceptEventBFormatter() {
        Variable v1 = new Variable("v1");
        Variable v2 = new Variable("v2");
        Equals equals = new Equals(v1, v2);
        ForAll forAll = new ForAll(equals, v1, v2);
        Assert.assertEquals("(" + FORALL + "(v1, v2).(v1 = v2))", forAll.accept(new EventBFormatter()));
    }

    @Test
    public void test_acceptExpressionToSMTLib2Formatter() {
        Variable v1 = new Variable("v1");
        Variable v2 = new Variable("v2");
        Variable v3 = new Variable("v3");
        Equals equals = new Equals(v1, v2);
        ForAll forAll = new ForAll(equals, v1, v2, v3);
        Assert.assertEquals("(declare-fun v1 () Int)" + LINE_SEPARATOR + "(declare-fun v2 () Int)" + LINE_SEPARATOR + LINE_SEPARATOR + "(assert" + LINE_SEPARATOR + TABULATION + "(forall" + LINE_SEPARATOR + TABULATION + TABULATION + "((v1 Int)" + LINE_SEPARATOR + TABULATION + TABULATION + "(v2 Int)" + LINE_SEPARATOR + TABULATION + TABULATION + "(v3 Int))" + LINE_SEPARATOR + TABULATION + TABULATION + "(=" + LINE_SEPARATOR + TABULATION + TABULATION + TABULATION + "v1" + LINE_SEPARATOR + TABULATION + TABULATION + TABULATION + "v2" + LINE_SEPARATOR + TABULATION + TABULATION + ")" + LINE_SEPARATOR + TABULATION + ")" + LINE_SEPARATOR + ")", ExpressionToSMTLib2Formatter.formatExpression(forAll));
    }

    @Test
    public void test_acceptAssignableReplacer() {
        Variable v1 = new Variable("v1");
        Variable v2 = new Variable("v2");
        Variable v3 = new Variable("v3");
        Equals equals = new Equals(v1, v2);
        ForAll forAll = new ForAll(equals, v1, v2, v3);
        try {
            forAll.accept(new AssignableReplacer(v1, new Subtraction(v1, v2)));
            throw new Error("AssignableReplacer visiting ForAll instance occurred and did not throw the expected error.");
        } catch (Error ignored) {

        }
    }

    @Test
    public void test_getAssignables() {
        Variable v1 = new Variable("v1");
        Variable v2 = new Variable("v2");
        Variable v3 = new Variable("v3");
        Equals equals = new Equals(v1, v2);
        ForAll forAll = new ForAll(equals, v1, v3, v2);
        Assert.assertEquals(new LinkedHashSet<>(Arrays.asList(v1, v2)), forAll.getAssignables());
    }

    @Test
    public void test_getQuantifiedVariables() {
        Variable v1 = new Variable("v1");
        Variable v2 = new Variable("v2");
        Variable v3 = new Variable("v3");
        Equals equals = new Equals(v1, v2);
        ForAll forAll = new ForAll(equals, v1, v3, v2);
        Assert.assertEquals(Arrays.asList(v1, v3, v2), forAll.getQuantifiedVariables());
    }

    @Test
    public void test_getExpression() {
        Variable v1 = new Variable("v1");
        Variable v2 = new Variable("v2");
        Variable v3 = new Variable("v3");
        Equals equals = new Equals(v1, v2);
        ForAll forAll = new ForAll(equals, v1, v2, v3);
        Assert.assertEquals(equals, forAll.getExpression());
    }

}