package eventb.expressions.arith;

import eventb.tools.replacer.AssignableReplacer;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by gvoiron on 15/08/16.
 * Time : 10:14
 */
public class SumTest {

    @Test
    public void test_acceptAssignableReplacer() {
        Variable x = new Variable("x");
        Int two = new Int(2);
        Multiplication substitute = new Multiplication(x, two);
        Sum sum = new Sum(x, two, x);
        Assert.assertEquals(new Sum(substitute, two, substitute), sum.accept(new AssignableReplacer(x, substitute)));
    }

}