package eventb.expressions.bool;

import eventb.expressions.arith.Int;
import eventb.expressions.arith.Multiplication;
import eventb.expressions.arith.Variable;
import eventb.tools.replacer.AssignableReplacer;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by gvoiron on 08/07/16.
 * Time : 00:10
 */
public class AndTest {

    @Test
    public void test_acceptAssignableReplacer() {
        Variable x = new Variable("x");
        Int two = new Int(2);
        Multiplication substitute = new Multiplication(x, two);
        And and = new And(new Equals(x, two), new Not(new Implication(new True(), new LowerOrEqual(two, x))));
        Assert.assertEquals(new And(new Equals(substitute, two), new Not(new Implication(new True(), new LowerOrEqual(two, substitute)))), and.accept(new AssignableReplacer(x, substitute)));
    }

}