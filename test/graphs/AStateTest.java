package graphs;

import eventb.expressions.arith.AAssignable;
import eventb.expressions.arith.Int;
import eventb.expressions.arith.Subtraction;
import eventb.expressions.arith.Variable;
import eventb.expressions.bool.Equals;
import eventb.expressions.bool.Predicate;
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
        Predicate predicate = new Predicate("p0", equals);
        ConcreteState concreteState = new ConcreteState("c0", equals);
        AbstractState abstractState = new AbstractState("q0", equals);
        Assert.assertEquals("(p0 " + UCharacters.EQ_DEF + " (v1 = 42))", predicate.accept(new EventBFormatter()));
        Assert.assertEquals("(c0 " + UCharacters.EQ_DEF + " (v1 = 42))", concreteState.accept(new EventBFormatter()));
        Assert.assertEquals("(q0 " + UCharacters.EQ_DEF + " (v1 = 42))", abstractState.accept(new EventBFormatter()));
    }

    @Test
    public void test_acceptExpressionToSMTLib2Formatter() {
        Variable v1 = new Variable("v1");
        Int fortyTwo = new Int(42);
        Equals equals = new Equals(v1, fortyTwo);
        Predicate predicate = new Predicate("p0", equals);
        ConcreteState concreteState = new ConcreteState("c0", equals);
        AbstractState abstractState = new AbstractState("q0", equals);
        Assert.assertEquals("(declare-fun v1 () Int)" + LINE_SEPARATOR + LINE_SEPARATOR + "(assert" + LINE_SEPARATOR + TABULATION + "(=" + LINE_SEPARATOR + TABULATION + TABULATION + "v1" + LINE_SEPARATOR + TABULATION + TABULATION + "42" + LINE_SEPARATOR + TABULATION + ")" + LINE_SEPARATOR + ")", ExpressionToSMTLib2Formatter.formatExpression(predicate));
        Assert.assertEquals("(declare-fun v1 () Int)" + LINE_SEPARATOR + LINE_SEPARATOR + "(assert" + LINE_SEPARATOR + TABULATION + "(=" + LINE_SEPARATOR + TABULATION + TABULATION + "v1" + LINE_SEPARATOR + TABULATION + TABULATION + "42" + LINE_SEPARATOR + TABULATION + ")" + LINE_SEPARATOR + ")", ExpressionToSMTLib2Formatter.formatExpression(abstractState));
        Assert.assertEquals("(declare-fun v1 () Int)" + LINE_SEPARATOR + LINE_SEPARATOR + "(assert" + LINE_SEPARATOR + TABULATION + "(=" + LINE_SEPARATOR + TABULATION + TABULATION + "v1" + LINE_SEPARATOR + TABULATION + TABULATION + "42" + LINE_SEPARATOR + TABULATION + ")" + LINE_SEPARATOR + ")", ExpressionToSMTLib2Formatter.formatExpression(concreteState));
    }

    @Test
    public void test_acceptAssignableReplacer() {
        Variable v1 = new Variable("v1");
        Int fortyTwo = new Int(42);
        Equals equals = new Equals(v1, fortyTwo);
        Subtraction subtraction = new Subtraction(v1, fortyTwo);
        Predicate predicate = new Predicate("p0", equals);
        ConcreteState concreteState = new ConcreteState("c0", equals);
        AbstractState abstractState = new AbstractState("q0", equals);
        Assert.assertEquals(new Equals(subtraction, fortyTwo), predicate.accept(new AssignableReplacer(v1, subtraction)));
        Assert.assertEquals(new Equals(subtraction, fortyTwo), concreteState.accept(new AssignableReplacer(v1, subtraction)));
        Assert.assertEquals(new Equals(subtraction, fortyTwo), abstractState.accept(new AssignableReplacer(v1, subtraction)));
    }

    @Test
    public void test_getAssignables() {
        Variable v1 = new Variable("v1");
        Int fortyTwo = new Int(42);
        Equals equals = new Equals(v1, fortyTwo);
        Predicate predicate = new Predicate("p0", equals);
        ConcreteState concreteState = new ConcreteState("c0", equals);
        AbstractState abstractState = new AbstractState("q0", equals);
        Assert.assertEquals(new LinkedHashSet<AAssignable>(Collections.singletonList(v1)), predicate.getAssignables());
        Assert.assertEquals(new LinkedHashSet<AAssignable>(Collections.singletonList(v1)), concreteState.getAssignables());
        Assert.assertEquals(new LinkedHashSet<AAssignable>(Collections.singletonList(v1)), abstractState.getAssignables());
    }

}