package eventb.expressions.bool;

import eventb.expressions.arith.Int;
import eventb.expressions.arith.Multiplication;
import eventb.expressions.arith.Variable;
import eventb.tools.replacer.AssignableReplacer;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by gvoiron on 15/08/16.
 * Time : 10:19
 */
public class OrTest {

    @Test
    public void test_acceptAssignableReplacer() {
        Variable x = new Variable("x");
        Int two = new Int(2);
        Multiplication substitute = new Multiplication(x, two);
        Or or = new Or(new Equals(x, two), new Not(new Implication(new True(), new LowerOrEqual(two, x))));
        Assert.assertEquals(new Or(new Equals(substitute, two), new Not(new Implication(new True(), new LowerOrEqual(two, substitute)))), or.accept(new AssignableReplacer(x, substitute)));
    }

}