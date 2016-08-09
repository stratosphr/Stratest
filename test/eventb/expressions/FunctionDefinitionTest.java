package eventb.expressions;

import eventb.expressions.arith.Int;
import eventb.expressions.sets.CustomSet;
import eventb.expressions.sets.RangeSet;
import eventb.tools.formatters.EventBFormatter;
import eventb.tools.formatters.ExpressionToSMTLib2Formatter;
import org.junit.Assert;
import org.junit.Test;

import static utilities.UCharacters.LINE_SEPARATOR;
import static utilities.UCharacters.TABULATION;

/**
 * Created by gvoiron on 05/08/16.
 * Time : 14:34
 */
public class FunctionDefinitionTest {

    @Test
    public void test_acceptEventBFormatter() {
        FunctionDefinition functionDefinition = new FunctionDefinition("fun", new RangeSet(new Int(0), new Int(4)), new CustomSet(new Int(0), new Int(1), new Int(3)));
        Assert.assertEquals("fun : 0..4 -> {0, 1, 3}", functionDefinition.accept(new EventBFormatter()));
    }

    @Test
    public void test_acceptExpressionToSMTLibFormatter() {
        FunctionDefinition functionDefinition = new FunctionDefinition("fun", new CustomSet(new Int(0), new Int(3), new Int(4)), new CustomSet(new Int(0), new Int(1), new Int(3)));
        Assert.assertEquals("(declare-fun fun!0 () Int)" + LINE_SEPARATOR + "(declare-fun fun!3 () Int)" + LINE_SEPARATOR + "(declare-fun fun!4 () Int)" + LINE_SEPARATOR + "(define-fun fun ((index Int)) Int" + LINE_SEPARATOR + TABULATION + "(ite" + LINE_SEPARATOR + TABULATION + TABULATION + "(=" + LINE_SEPARATOR + TABULATION + TABULATION + TABULATION + "index" + LINE_SEPARATOR + TABULATION + TABULATION + TABULATION + "0" + LINE_SEPARATOR + TABULATION + TABULATION + ")" + LINE_SEPARATOR + TABULATION + TABULATION + "fun!0" + LINE_SEPARATOR + TABULATION + TABULATION + "(ite" + LINE_SEPARATOR + TABULATION + TABULATION + TABULATION + "(=" + LINE_SEPARATOR + TABULATION + TABULATION + TABULATION + TABULATION + "index" + LINE_SEPARATOR + TABULATION + TABULATION + TABULATION + TABULATION + "3" + LINE_SEPARATOR + TABULATION + TABULATION + TABULATION + ")" + LINE_SEPARATOR + TABULATION + TABULATION + TABULATION + "fun!3" + LINE_SEPARATOR + TABULATION + TABULATION + TABULATION + "(ite" + LINE_SEPARATOR + TABULATION + TABULATION + TABULATION + TABULATION + "(=" + LINE_SEPARATOR + TABULATION + TABULATION + TABULATION + TABULATION + TABULATION + "index" + LINE_SEPARATOR + TABULATION + TABULATION + TABULATION + TABULATION + TABULATION + "4" + LINE_SEPARATOR + TABULATION + TABULATION + TABULATION + TABULATION + ")" + LINE_SEPARATOR + TABULATION + TABULATION + TABULATION + TABULATION + "fun!4" + LINE_SEPARATOR + TABULATION + TABULATION + TABULATION + TABULATION + "-1" + LINE_SEPARATOR + TABULATION + TABULATION + TABULATION + ")" + LINE_SEPARATOR + TABULATION + TABULATION + ")" + LINE_SEPARATOR + TABULATION + ")" + LINE_SEPARATOR + ")", functionDefinition.accept(new ExpressionToSMTLib2Formatter()));
    }

    @Test
    public void test_getName() {
        RangeSet domain = new RangeSet(new Int(0), new Int(4));
        CustomSet coDomain = new CustomSet(new Int(0), new Int(1), new Int(3));
        FunctionDefinition functionDefinition = new FunctionDefinition("fun", domain, coDomain);
        Assert.assertEquals("fun", functionDefinition.getName());
    }

    @Test
    public void test_getDomain() {
        RangeSet domain = new RangeSet(new Int(0), new Int(4));
        CustomSet coDomain = new CustomSet(new Int(0), new Int(1), new Int(3));
        FunctionDefinition functionDefinition = new FunctionDefinition("fun", domain, coDomain);
        Assert.assertEquals(domain, functionDefinition.getDomain());
    }

    @Test
    public void test_getCoDomain() {
        RangeSet domain = new RangeSet(new Int(0), new Int(4));
        CustomSet coDomain = new CustomSet(new Int(0), new Int(1), new Int(3));
        FunctionDefinition functionDefinition = new FunctionDefinition("fun", domain, coDomain);
        Assert.assertEquals(coDomain, functionDefinition.getCoDomain());
    }

}