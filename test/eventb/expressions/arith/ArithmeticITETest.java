package eventb.expressions.arith;

import eventb.expressions.bool.And;
import eventb.expressions.bool.Equals;
import eventb.expressions.bool.True;
import eventb.tools.formatters.EventBFormatter;
import eventb.tools.replacer.AssignableReplacer;
import org.junit.Assert;
import org.junit.Test;

import static utilities.UCharacters.LAND;

/**
 * Created by gvoiron on 13/08/16.
 * Time : 15:23
 */
public class ArithmeticITETest {

    @Test
    public void test_acceptEventBFormatter() throws Exception {
        Int fortyTwo = new Int(42);
        Int twelve = new Int(12);
        ArithmeticITE arithmeticITE = new ArithmeticITE(new And(new True(), new Equals(new Variable("v1"), fortyTwo)), new Subtraction(twelve, fortyTwo), twelve);
        Assert.assertEquals("((_true_ " + LAND + " (v1 = 42)) ? (12 - 42) : 12)", arithmeticITE.accept(new EventBFormatter()));
    }

    @Test
    public void test_acceptAssignableReplacer() throws Exception {
        Variable v1 = new Variable("v1");
        Int fortyTwo = new Int(42);
        Int twelve = new Int(12);
        ArithmeticITE arithmeticITE = new ArithmeticITE(new And(new True(), new Equals(v1, fortyTwo)), new Subtraction(twelve, fortyTwo), twelve);
        try {
            arithmeticITE.accept(new AssignableReplacer(v1, new Multiplication(new Subtraction(v1, twelve), fortyTwo)));
            throw new Error("AssignableReplacer visiting ArithmeticITE instance occurred and did not throw the expected Error.");
        } catch (Error ignored) {

        }
    }

}