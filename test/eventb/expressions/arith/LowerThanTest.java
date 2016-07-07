package eventb.expressions.arith;

import eventb.tools.replacer.AssignableReplacer;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by gvoiron on 08/07/16.
 * Time : 00:00
 */
public class LowerThanTest {

    @Test
    public void acceptAssignableReplacer() {
        Variable x = new Variable("x");
        Int two = new Int(2);
        Multiplication substitute = new Multiplication(two, two, x);
        LowerThan lowerThan = new LowerThan(two, x);
        Assert.assertEquals(new LowerThan(two, substitute), lowerThan.accept(new AssignableReplacer(x, substitute)));
    }

}