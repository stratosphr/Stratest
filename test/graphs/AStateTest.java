package graphs;

import eventb.expressions.arith.AAssignable;
import eventb.expressions.arith.Int;
import eventb.expressions.arith.Variable;
import eventb.expressions.bool.Equals;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 13/08/16.
 * Time : 15:54
 */
public class AStateTest {

    @Test
    public void test_acceptEventBFormatter() {
    }

    @Test
    public void test_acceptExpressionToSMTLib2Formatter() {
    }

    @Test
    public void test_acceptAssignableReplacer() {
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