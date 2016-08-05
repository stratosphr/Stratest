package eventb.tools.formatters;

import eventb.expressions.arith.*;
import eventb.expressions.bool.*;
import org.junit.Assert;
import org.junit.Test;
import utilities.UCharacters;

/**
 * Created by gvoiron on 04/08/16.
 * Time : 13:24
 */
public class ExpressionToSMTLib2FormatterTest {

    @Test
    public void test_visitTrue() {
        Assert.assertEquals("true", new True().accept(new ExpressionToSMTLib2Formatter()));
    }

    @Test
    public void test_visitNot() {
        True aTrue = new True();
        And and = new And(aTrue, new Not(aTrue));
        Not not = new Not(and);
        Assert.assertEquals("(not (and" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "true" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "(not true)" + UCharacters.LINE_SEPARATOR + "))", not.accept(new ExpressionToSMTLib2Formatter()));
    }

    @Test
    public void test_visitAnd() {
        True aTrue = new True();
        And and = new And(aTrue, new Not(aTrue));
        Assert.assertEquals("(and" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "true" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "(not true)" + UCharacters.LINE_SEPARATOR + ")", and.accept(new ExpressionToSMTLib2Formatter()));
        and = new And();
        Assert.assertEquals("and", and.accept(new ExpressionToSMTLib2Formatter()));
        and = new And(aTrue);
        Assert.assertEquals("(and" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "true" + UCharacters.LINE_SEPARATOR + ")", and.accept(new ExpressionToSMTLib2Formatter()));
    }

    @Test
    public void test_visitEquals() {
        Int fortyTwo = new Int(42);
        Int sixtyFour = new Int(64);
        Equals equals = new Equals(fortyTwo, sixtyFour);
        Assert.assertEquals("(=" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "42" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "64" + UCharacters.LINE_SEPARATOR + ")", equals.accept(new ExpressionToSMTLib2Formatter()));
    }

    @Test
    public void test_visitLowerThan() {
        Int fortyTwo = new Int(42);
        Int sixtyFour = new Int(64);
        LowerThan lowerThan = new LowerThan(fortyTwo, sixtyFour);
        Assert.assertEquals("(<" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "42" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "64" + UCharacters.LINE_SEPARATOR + ")", lowerThan.accept(new ExpressionToSMTLib2Formatter()));
    }

    @Test
    public void test_visitLowerOrEqual() {
        Int fortyTwo = new Int(42);
        Int sixtyFour = new Int(64);
        LowerOrEqual lowerOrEqual = new LowerOrEqual(fortyTwo, sixtyFour);
        Assert.assertEquals("(<=" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "42" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "64" + UCharacters.LINE_SEPARATOR + ")", lowerOrEqual.accept(new ExpressionToSMTLib2Formatter()));
    }

    @Test
    public void test_visitGreaterThan() {
        Int fortyTwo = new Int(42);
        Int sixtyFour = new Int(64);
        GreaterThan greaterThan = new GreaterThan(fortyTwo, sixtyFour);
        Assert.assertEquals("(>" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "42" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "64" + UCharacters.LINE_SEPARATOR + ")", greaterThan.accept(new ExpressionToSMTLib2Formatter()));
    }

    @Test
    public void test_visitGreaterOrEqual() {
        Int fortyTwo = new Int(42);
        Int sixtyFour = new Int(64);
        GreaterOrEqual greaterOrEqual = new GreaterOrEqual(fortyTwo, sixtyFour);
        Assert.assertEquals("(>=" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "42" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "64" + UCharacters.LINE_SEPARATOR + ")", greaterOrEqual.accept(new ExpressionToSMTLib2Formatter()));
    }

    @Test
    public void test_visitImplication() {
        True aTrue = new True();
        And and = new And(aTrue, new Not(aTrue));
        Implication implication = new Implication(aTrue, and);
        Assert.assertEquals("(=>" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "true" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "(and" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + UCharacters.TABULATION + "true" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + UCharacters.TABULATION + "(not true)" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + ")" + UCharacters.LINE_SEPARATOR + ")", implication.accept(new ExpressionToSMTLib2Formatter()));
    }

    @Test
    public void test_visitVariable() {
        Variable variable = new Variable("v1");
        Assert.assertEquals("v1", variable.accept(new ExpressionToSMTLib2Formatter()));
    }

    @Test
    public void test_visitInt() {
        Int fortyTwo = new Int(42);
        Assert.assertEquals("42", fortyTwo.accept(new ExpressionToSMTLib2Formatter()));
    }

    @Test
    public void test_visitSubtraction() {
        Int fortyTwo = new Int(42);
        Int sixtyFour = new Int(64);
        Subtraction subtraction = new Subtraction(fortyTwo, sixtyFour, sixtyFour);
        Assert.assertEquals("(-" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "42" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "64" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "64" + UCharacters.LINE_SEPARATOR + ")", subtraction.accept(new ExpressionToSMTLib2Formatter()));
    }

    @Test
    public void test_visitMultiplication() {
        Int fortyTwo = new Int(42);
        Int sixtyFour = new Int(64);
        Multiplication multiplication = new Multiplication(fortyTwo, sixtyFour, sixtyFour);
        Assert.assertEquals("(*" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "42" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "64" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "64" + UCharacters.LINE_SEPARATOR + ")", multiplication.accept(new ExpressionToSMTLib2Formatter()));
    }

}