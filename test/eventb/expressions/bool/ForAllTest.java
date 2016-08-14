package eventb.expressions.bool;

import eventb.expressions.arith.Variable;
import eventb.tools.formatters.EventBFormatter;
import eventb.tools.formatters.ExpressionToSMTLib2Formatter;
import org.junit.Assert;
import org.junit.Test;

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
        Assert.assertEquals("(declare-fun v1 () Int)" + LINE_SEPARATOR + "(declare-fun v2 () Int)" + LINE_SEPARATOR + LINE_SEPARATOR + "(forall" + LINE_SEPARATOR + TABULATION + "((v1 Int)" + LINE_SEPARATOR + TABULATION + "(v2 Int)" + LINE_SEPARATOR + TABULATION + "(v3 Int))" + LINE_SEPARATOR + TABULATION + "(=" + LINE_SEPARATOR + TABULATION + TABULATION + "v1" + LINE_SEPARATOR + TABULATION + TABULATION + "v2" + LINE_SEPARATOR + TABULATION + ")" + LINE_SEPARATOR + ")", ExpressionToSMTLib2Formatter.formatExpression(forAll));
    }

    @Test
    public void test_acceptExpressionToExpressionVisitor() {
    }

    @Test
    public void test_acceptAssignableReplacer() {
    }

    @Test
    public void test_getAssignables() {
    }

    @Test
    public void test_getQuantifiedVariables() {
    }

    @Test
    public void test_getExpression() {
    }

    @Test
    public void test_prime() {
    }

}