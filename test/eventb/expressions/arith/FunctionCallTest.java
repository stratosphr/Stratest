package eventb.expressions.arith;

import eventb.expressions.FunctionDefinition;
import eventb.expressions.sets.CustomSet;
import eventb.expressions.sets.RangeSet;
import eventb.tools.formatters.EventBFormatter;
import eventb.tools.formatters.ExpressionToSMTLib2Formatter;
import eventb.tools.replacer.AssignableReplacer;
import org.junit.Assert;
import org.junit.Test;
import utilities.UCharacters;

import java.util.Collections;

import static utilities.UCharacters.LINE_SEPARATOR;
import static utilities.UCharacters.TABULATION;

/**
 * Created by gvoiron on 05/08/16.
 * Time : 14:13
 */
public class FunctionCallTest {

    @Test
    public void test_acceptEventBFormatter() throws Exception {
        FunctionDefinition functionDefinition = new FunctionDefinition("fun", new RangeSet(new Int(0), new Int(4)), new CustomSet(new Int(0), new Int(1), new Int(3)));
        FunctionCall functionCall = new FunctionCall(functionDefinition);
        Assert.assertEquals("fun()", functionCall.accept(new EventBFormatter()));
        functionCall = new FunctionCall(functionDefinition, new Int(42));
        Assert.assertEquals("fun(42)", functionCall.accept(new EventBFormatter()));
        functionCall = new FunctionCall(functionDefinition, new Variable("v1"));
        Assert.assertEquals("fun(v1)", functionCall.accept(new EventBFormatter()));
    }

    @Test
    public void test_acceptExpressionFormatter() throws Exception {
        ExpressionToSMTLib2Formatter expressionToSMTLib2Formatter = new ExpressionToSMTLib2Formatter();
        FunctionDefinition functionDefinition = new FunctionDefinition("fun", new CustomSet(new Int(0), new Int(3), new Int(4)), new CustomSet(new Int(0), new Int(1), new Int(3)));
        FunctionCall functionCall = new FunctionCall(functionDefinition, new Int(42));
        String readFunction = "(define-fun " + functionDefinition.getName() + " ((index Int)) Int" + LINE_SEPARATOR + TABULATION + "(ite" + LINE_SEPARATOR + TABULATION + TABULATION + "(=" + LINE_SEPARATOR + TABULATION + TABULATION + TABULATION + "index" + LINE_SEPARATOR + TABULATION + TABULATION + TABULATION + "0" + LINE_SEPARATOR + TABULATION + TABULATION + ")" + LINE_SEPARATOR + TABULATION + TABULATION + functionDefinition.getName() + "!0" + LINE_SEPARATOR + TABULATION + TABULATION + "(ite" + LINE_SEPARATOR + TABULATION + TABULATION + TABULATION + "(=" + LINE_SEPARATOR + TABULATION + TABULATION + TABULATION + TABULATION + "index" + LINE_SEPARATOR + TABULATION + TABULATION + TABULATION + TABULATION + "3" + LINE_SEPARATOR + TABULATION + TABULATION + TABULATION + ")" + LINE_SEPARATOR + TABULATION + TABULATION + TABULATION + functionDefinition.getName() + "!3" + LINE_SEPARATOR + TABULATION + TABULATION + TABULATION + "(ite" + LINE_SEPARATOR + TABULATION + TABULATION + TABULATION + TABULATION + "(=" + LINE_SEPARATOR + TABULATION + TABULATION + TABULATION + TABULATION + TABULATION + "index" + LINE_SEPARATOR + TABULATION + TABULATION + TABULATION + TABULATION + TABULATION + "4" + LINE_SEPARATOR + TABULATION + TABULATION + TABULATION + TABULATION + ")" + LINE_SEPARATOR + TABULATION + TABULATION + TABULATION + TABULATION + functionDefinition.getName() + "!4" + LINE_SEPARATOR + TABULATION + TABULATION + TABULATION + TABULATION + "-1" + LINE_SEPARATOR + TABULATION + TABULATION + TABULATION + ")" + LINE_SEPARATOR + TABULATION + TABULATION + ")" + LINE_SEPARATOR + TABULATION + ")" + LINE_SEPARATOR + ")";
        Assert.assertEquals(readFunction + UCharacters.LINE_SEPARATOR + "(fun" + LINE_SEPARATOR + TABULATION + "42" + LINE_SEPARATOR + ")", functionCall.accept(expressionToSMTLib2Formatter));
        functionCall = new FunctionCall(functionDefinition, new Variable("v1"));
        Assert.assertEquals("(fun" + LINE_SEPARATOR + TABULATION + "v1" + LINE_SEPARATOR + ")", functionCall.accept(expressionToSMTLib2Formatter));
        functionDefinition = new FunctionDefinition("fun", new CustomSet(new Int(0), new Int(3), new Int(4)), new CustomSet(new Int(0), new Int(1), new Int(3)));
        functionCall = new FunctionCall(functionDefinition, new Int(42));
        Assert.assertEquals(readFunction + UCharacters.LINE_SEPARATOR + "(fun" + LINE_SEPARATOR + TABULATION + "42" + LINE_SEPARATOR + ")", functionCall.accept(new ExpressionToSMTLib2Formatter()));
        functionCall = new FunctionCall(functionDefinition, new Variable("v1"));
        Assert.assertEquals(readFunction + UCharacters.LINE_SEPARATOR + "(fun" + LINE_SEPARATOR + TABULATION + "v1" + LINE_SEPARATOR + ")", functionCall.accept(new ExpressionToSMTLib2Formatter()));
        System.out.println(readFunction + UCharacters.LINE_SEPARATOR + "(fun" + LINE_SEPARATOR + TABULATION + "v1" + LINE_SEPARATOR + ")");
    }

    @Test
    public void test_acceptAssignableReplacer() throws Exception {
        try {
            FunctionDefinition functionDefinition = new FunctionDefinition("fun", new RangeSet(new Int(0), new Int(4)), new CustomSet(new Int(0), new Int(1), new Int(3)));
            FunctionCall functionCall = new FunctionCall(functionDefinition);
            functionCall.accept(new AssignableReplacer(new FunctionCall(functionDefinition), new Variable("v1")));
            throw new Error("AssignableReplacer visiting FunctionCall instance occurred and did not throw the expected Error.");
        } catch (Error ignored) {

        }
    }

    @Test
    public void test_getDefinition() throws Exception {
        FunctionDefinition functionDefinition = new FunctionDefinition("fun", new RangeSet(new Int(0), new Int(4)), new CustomSet(new Int(0), new Int(1), new Int(3)));
        FunctionCall functionCall = new FunctionCall(functionDefinition);
        Assert.assertEquals(functionDefinition, functionCall.getDefinition());
    }

    @Test
    public void test_getOperands() throws Exception {
        FunctionDefinition functionDefinition = new FunctionDefinition("fun", new RangeSet(new Int(0), new Int(4)), new CustomSet(new Int(0), new Int(1), new Int(3)));
        FunctionCall functionCall = new FunctionCall(functionDefinition);
        Assert.assertEquals(Collections.emptyList(), functionCall.getOperands());
        Variable v1 = new Variable("v1");
        functionCall = new FunctionCall(functionDefinition, v1);
        Assert.assertEquals(Collections.singletonList(v1), functionCall.getOperands());
    }

    @Test
    public void test_equals() throws Exception {
        FunctionDefinition functionDefinition = new FunctionDefinition("fun", new RangeSet(new Int(0), new Int(4)), new CustomSet(new Int(0), new Int(1), new Int(3)));
        Variable v1 = new Variable("v1");
        FunctionCall functionCall = new FunctionCall(functionDefinition, v1);
        Assert.assertNotEquals(new FunctionCall(functionDefinition, new Int(0)), functionCall);
        Assert.assertEquals(new FunctionCall(functionDefinition, v1), functionCall);
    }

}