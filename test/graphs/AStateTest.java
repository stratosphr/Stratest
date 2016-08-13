package graphs;

import eventb.expressions.arith.AAssignable;
import eventb.expressions.arith.Int;
import eventb.expressions.arith.Subtraction;
import eventb.expressions.arith.Variable;
import eventb.expressions.bool.Equals;
import eventb.tools.formatters.EventBFormatter;
import eventb.tools.formatters.ExpressionToSMTLib2Formatter;
import eventb.tools.replacer.AssignableReplacer;
import org.junit.Assert;
import org.junit.Test;
import utilities.UCharacters;

import java.util.Collections;
import java.util.LinkedHashSet;

import static utilities.UCharacters.LINE_SEPARATOR;
import static utilities.UCharacters.TABULATION;

/**
 * Created by gvoiron on 13/08/16.
 * Time : 15:54
 */
public class AStateTest {

    @Test
    public void test_acceptEventBFormatter() {
        Variable v1 = new Variable("v1");
        Int fortyTwo = new Int(42);
        Equals equals = new Equals(v1, fortyTwo);
        ConcreteState state = new ConcreteState("c0", equals);
        Assert.assertEquals("(c0 " + UCharacters.EQ_DEF + " (v1 = 42))", state.accept(new EventBFormatter()));
    }

    @Test
    public void test_acceptExpressionToSMTLib2Formatter() {
        Variable v1 = new Variable("v1");
        Int fortyTwo = new Int(42);
        Equals equals = new Equals(v1, fortyTwo);
        ConcreteState state = new ConcreteState("c0", equals);
        Assert.assertEquals("(declare-fun v1 () Int)" + LINE_SEPARATOR + LINE_SEPARATOR + "(=" + LINE_SEPARATOR + TABULATION + "v1" + LINE_SEPARATOR + TABULATION + "42" + LINE_SEPARATOR + ")", ExpressionToSMTLib2Formatter.formatExpression(state));
    }

    @Test
    public void test_acceptAssignableReplacer() {
        Variable v1 = new Variable("v1");
        Int fortyTwo = new Int(42);
        Equals equals = new Equals(v1, fortyTwo);
        ConcreteState state = new ConcreteState("c0", equals);
        Subtraction subtraction = new Subtraction(v1, fortyTwo);
        Assert.assertEquals(new Equals(subtraction, fortyTwo), state.accept(new AssignableReplacer(v1, subtraction)));
    }

    @Test
    public void test_getName() {
        Assert.assertEquals("c0", new ConcreteState("c0", null).getName());
        Assert.assertEquals("c0", new AbstractState("c0", null).getName());
    }

    @Test
    public void test_getExpression() {
        Equals equals = new Equals(new Variable("v1"), new Int(42));
        Assert.assertEquals(equals, new ConcreteState(null, equals).getExpression());
        Assert.assertEquals(equals, new AbstractState(null, equals).getExpression());
    }

    @Test
    public void test_getAssignables() {
        Variable v1 = new Variable("v1");
        Int fortyTwo = new Int(42);
        Equals equals = new Equals(v1, fortyTwo);
        ConcreteState state = new ConcreteState("c0", equals);
        Assert.assertEquals(new LinkedHashSet<AAssignable>(Collections.singletonList(v1)), state.getAssignables());
    }

}