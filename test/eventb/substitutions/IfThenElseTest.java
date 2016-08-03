package eventb.substitutions;

import eventb.expressions.arith.GreaterOrEqual;
import eventb.expressions.arith.Int;
import eventb.expressions.arith.LowerThan;
import eventb.expressions.arith.Variable;
import eventb.expressions.bool.*;
import eventb.tools.formatters.EventBFormatter;
import org.junit.Assert;
import org.junit.Test;
import utilities.UCharacters;

/**
 * Created by gvoiron on 07/07/16.
 * Time : 23:13
 */
public class IfThenElseTest {

    @Test
    public void test_getWP() {
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        ABooleanExpression condition = new LowerThan(x, y);
        ASubstitution thenPart = new Assignment(x, y);
        ASubstitution elsePart = new Skip();
        ABooleanExpression postCondition = new GreaterOrEqual(x, y);
        Assert.assertEquals(new And(new Implication(condition, new GreaterOrEqual(y, y)), new Implication(new Not(condition), new GreaterOrEqual(x, y))), new IfThenElse(condition, thenPart, elsePart).getWP(postCondition));
    }

    @Test
    public void test_acceptEventBFormatter() {
        Assert.assertEquals("IF" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "_true_" + UCharacters.LINE_SEPARATOR + "THEN" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "SKIP" + UCharacters.LINE_SEPARATOR + "ELSE" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + "x := 42" + UCharacters.LINE_SEPARATOR + "END", new IfThenElse(new True(), new Skip(), new Assignment(new Variable("x"), new Int(42))).accept(new EventBFormatter()));
    }

    @Test
    public void test_hashCode() {

    }

    @Test
    public void test_equals() {

    }

    @Test
    public void test_toString() {

    }

}