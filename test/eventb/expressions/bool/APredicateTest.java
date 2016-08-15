package eventb.expressions.bool;

import eventb.expressions.arith.Int;
import eventb.expressions.arith.Variable;
import graphs.AbstractState;
import graphs.ConcreteState;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by gvoiron on 15/08/16.
 * Time : 08:50
 */
public class APredicateTest {

    @Test
    public void test_getName() {
        Assert.assertEquals("c0", new Predicate("c0", null).getName());
        Assert.assertEquals("c0", new ConcreteState("c0", null).getName());
        Assert.assertEquals("c0", new AbstractState("c0", null).getName());
    }

    @Test
    public void test_getExpression() {
        Equals equals = new Equals(new Variable("v1"), new Int(42));
        Assert.assertEquals(equals, new Predicate(null, equals).getExpression());
        Assert.assertEquals(equals, new ConcreteState(null, equals).getExpression());
        Assert.assertEquals(equals, new AbstractState(null, equals).getExpression());
    }

}