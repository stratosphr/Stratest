package eventb.expressions.bool;

import eventb.expressions.arith.Int;
import eventb.expressions.arith.Multiplication;
import eventb.expressions.arith.Variable;
import eventb.tools.replacer.AssignableReplacer;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by gvoiron on 07/07/16.
 * Time : 23:58
 */
public class NotTest {

    @Test
    public void acceptAssignableReplacer() throws Exception {
        Variable x = new Variable("x");
        Int two = new Int(2);
        Multiplication substitute = new Multiplication(x, two);
        Not not = new Not(new Equals(x, two));
        Assert.assertEquals(new Not(new Equals(substitute, two)), not.accept(new AssignableReplacer(x, substitute)));
    }

}