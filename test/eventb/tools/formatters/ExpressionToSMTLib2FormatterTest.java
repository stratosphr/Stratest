package eventb.tools.formatters;

import eventb.expressions.arith.*;
import eventb.expressions.bool.*;
import org.junit.Assert;
import org.junit.Test;

import static utilities.UCharacters.LINE_SEPARATOR;
import static utilities.UCharacters.TABULATION;

/**
 * Created by gvoiron on 04/08/16.
 * Time : 13:24
 */
public class ExpressionToSMTLib2FormatterTest {

    @Test
    public void test_visitTrue() {
        Assert.assertEquals("true", ExpressionToSMTLib2Formatter.formatExpression(new True()));
    }

    @Test
    public void test_visitNot() {
        True aTrue = new True();
        And and = new And(aTrue, new Not(aTrue));
        Not not = new Not(and);
        Assert.assertEquals("(not (and" + LINE_SEPARATOR + TABULATION + "true" + LINE_SEPARATOR + TABULATION + "(not true)" + LINE_SEPARATOR + "))", ExpressionToSMTLib2Formatter.formatExpression(not));
    }

    @Test
    public void test_visitAnd() {
        True aTrue = new True();
        And and = new And(aTrue, new Not(aTrue));
        Assert.assertEquals("(and" + LINE_SEPARATOR + TABULATION + "true" + LINE_SEPARATOR + TABULATION + "(not true)" + LINE_SEPARATOR + ")", ExpressionToSMTLib2Formatter.formatExpression(and));
        and = new And();
        Assert.assertEquals("and", ExpressionToSMTLib2Formatter.formatExpression(and));
        and = new And(aTrue);
        Assert.assertEquals("(and" + LINE_SEPARATOR + TABULATION + "true" + LINE_SEPARATOR + ")", ExpressionToSMTLib2Formatter.formatExpression(and));
    }

    @Test
    public void test_visitOr() {
        True aTrue = new True();
        Or or = new Or(aTrue, new Not(aTrue));
        Assert.assertEquals("(or" + LINE_SEPARATOR + TABULATION + "true" + LINE_SEPARATOR + TABULATION + "(not true)" + LINE_SEPARATOR + ")", ExpressionToSMTLib2Formatter.formatExpression(or));
        or = new Or();
        Assert.assertEquals("or", ExpressionToSMTLib2Formatter.formatExpression(or));
        or = new Or(aTrue);
        Assert.assertEquals("(or" + LINE_SEPARATOR + TABULATION + "true" + LINE_SEPARATOR + ")", ExpressionToSMTLib2Formatter.formatExpression(or));
    }

    @Test
    public void test_visitEquals() {
        Int fortyTwo = new Int(42);
        Int sixtyFour = new Int(64);
        Equals equals = new Equals(fortyTwo, sixtyFour);
        Assert.assertEquals("(=" + LINE_SEPARATOR + TABULATION + "42" + LINE_SEPARATOR + TABULATION + "64" + LINE_SEPARATOR + ")", ExpressionToSMTLib2Formatter.formatExpression(equals));
    }

    @Test
    public void test_visitLowerThan() {
        Int fortyTwo = new Int(42);
        Int sixtyFour = new Int(64);
        LowerThan lowerThan = new LowerThan(fortyTwo, sixtyFour);
        Assert.assertEquals("(<" + LINE_SEPARATOR + TABULATION + "42" + LINE_SEPARATOR + TABULATION + "64" + LINE_SEPARATOR + ")", ExpressionToSMTLib2Formatter.formatExpression(lowerThan));
    }

    @Test
    public void test_visitLowerOrEqual() {
        Int fortyTwo = new Int(42);
        Int sixtyFour = new Int(64);
        LowerOrEqual lowerOrEqual = new LowerOrEqual(fortyTwo, sixtyFour);
        Assert.assertEquals("(<=" + LINE_SEPARATOR + TABULATION + "42" + LINE_SEPARATOR + TABULATION + "64" + LINE_SEPARATOR + ")", ExpressionToSMTLib2Formatter.formatExpression(lowerOrEqual));
    }

    @Test
    public void test_visitGreaterThan() {
        Int fortyTwo = new Int(42);
        Int sixtyFour = new Int(64);
        GreaterThan greaterThan = new GreaterThan(fortyTwo, sixtyFour);
        Assert.assertEquals("(>" + LINE_SEPARATOR + TABULATION + "42" + LINE_SEPARATOR + TABULATION + "64" + LINE_SEPARATOR + ")", ExpressionToSMTLib2Formatter.formatExpression(greaterThan));
    }

    @Test
    public void test_visitGreaterOrEqual() {
        Int fortyTwo = new Int(42);
        Int sixtyFour = new Int(64);
        GreaterOrEqual greaterOrEqual = new GreaterOrEqual(fortyTwo, sixtyFour);
        Assert.assertEquals("(>=" + LINE_SEPARATOR + TABULATION + "42" + LINE_SEPARATOR + TABULATION + "64" + LINE_SEPARATOR + ")", ExpressionToSMTLib2Formatter.formatExpression(greaterOrEqual));
    }

    @Test
    public void test_visitImplication() {
        True aTrue = new True();
        And and = new And(aTrue, new Not(aTrue));
        Implication implication = new Implication(aTrue, and);
        Assert.assertEquals("(=>" + LINE_SEPARATOR + TABULATION + "true" + LINE_SEPARATOR + TABULATION + "(and" + LINE_SEPARATOR + TABULATION + TABULATION + "true" + LINE_SEPARATOR + TABULATION + TABULATION + "(not true)" + LINE_SEPARATOR + TABULATION + ")" + LINE_SEPARATOR + ")", ExpressionToSMTLib2Formatter.formatExpression(implication));
    }

    @Test
    public void test_visitVariable() {
        Variable variable = new Variable("v1");
        Assert.assertEquals("(declare-fun v1 () Int)" + LINE_SEPARATOR + LINE_SEPARATOR + "v1", ExpressionToSMTLib2Formatter.formatExpression(variable));
    }

    @Test
    public void test_visitInt() {
        Int fortyTwo = new Int(42);
        Assert.assertEquals("42", ExpressionToSMTLib2Formatter.formatExpression(fortyTwo));
    }

    @Test
    public void test_visitSum() {
        Int fortyTwo = new Int(42);
        Int sixtyFour = new Int(64);
        Sum sum = new Sum(fortyTwo, sixtyFour, sixtyFour);
        Assert.assertEquals("(+" + LINE_SEPARATOR + TABULATION + "42" + LINE_SEPARATOR + TABULATION + "64" + LINE_SEPARATOR + TABULATION + "64" + LINE_SEPARATOR + ")", ExpressionToSMTLib2Formatter.formatExpression(sum));
    }

    @Test
    public void test_visitSubtraction() {
        Int fortyTwo = new Int(42);
        Int sixtyFour = new Int(64);
        Subtraction subtraction = new Subtraction(fortyTwo, sixtyFour, sixtyFour);
        Assert.assertEquals("(-" + LINE_SEPARATOR + TABULATION + "42" + LINE_SEPARATOR + TABULATION + "64" + LINE_SEPARATOR + TABULATION + "64" + LINE_SEPARATOR + ")", ExpressionToSMTLib2Formatter.formatExpression(subtraction));
    }

    @Test
    public void test_visitMultiplication() {
        Int fortyTwo = new Int(42);
        Int sixtyFour = new Int(64);
        Multiplication multiplication = new Multiplication(fortyTwo, sixtyFour, sixtyFour);
        Assert.assertEquals("(*" + LINE_SEPARATOR + TABULATION + "42" + LINE_SEPARATOR + TABULATION + "64" + LINE_SEPARATOR + TABULATION + "64" + LINE_SEPARATOR + ")", ExpressionToSMTLib2Formatter.formatExpression(multiplication));
    }

}