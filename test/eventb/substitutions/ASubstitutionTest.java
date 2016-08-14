package eventb.substitutions;

import eventb.expressions.arith.Int;
import eventb.expressions.arith.Variable;
import eventb.expressions.bool.ABooleanExpression;
import eventb.expressions.bool.And;
import eventb.expressions.bool.Equals;
import eventb.expressions.bool.Not;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by gvoiron on 14/08/16.
 * Time : 18:00
 */
public class ASubstitutionTest {

    @Test
    public void test_getWCP() {
        Variable v1 = new Variable("v1");
        Variable v2 = new Variable("v2");
        Int fortyTwo = new Int(42);
        And and = new And(new Equals(v1, fortyTwo), new Equals(v1, v2));
        Assignment assignment = new Assignment(v1, fortyTwo);
        Any any = new Any(and, assignment, v1);
        ABooleanExpression postCondition = new Equals(v1, v2);
        Assert.assertEquals(new Not(any.getWP(new Not(postCondition))), any.getWCP(postCondition));
    }

}