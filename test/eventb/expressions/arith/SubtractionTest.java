package eventb.expressions.arith;

import eventb.tools.replacer.AssignableReplacer;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by gvoiron on 07/07/16.
 * Time : 21:06
 */
public final class SubtractionTest {

    @Test
    public void test_acceptAssignableReplacer() {
        Variable x = new Variable("x");
        Int two = new Int(2);
        Multiplication substitute = new Multiplication(x, two);
        Subtraction subtraction = new Subtraction(x, two, x);
        Assert.assertEquals(new Subtraction(substitute, two, substitute), subtraction.accept(new AssignableReplacer(x, substitute)));
    }

}