package eventb.substitutions;

import eventb.expressions.arith.Int;
import eventb.expressions.arith.Variable;
import eventb.expressions.bool.ABooleanExpression;
import eventb.expressions.bool.And;
import eventb.expressions.bool.Equals;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by gvoiron on 15/08/16.
 * Time : 18:04
 */
public class ChoiceTest {

    @Test
    public void getWP() throws Exception {
        Int fortyTwo = new Int(42);
        Int twelve = new Int(12);
        Variable v1 = new Variable("v1");
        Variable v2 = new Variable("v2");
        Select select1 = new Select(new Equals(fortyTwo, v1), new Assignment(v1, fortyTwo));
        Select select2 = new Select(new Equals(twelve, v2), select1);
        Choice choice = new Choice(select1, select2);
        ABooleanExpression postCondition = new Equals(fortyTwo, twelve);
        Assert.assertEquals(new And(select1.getWP(postCondition), select2.getWP(postCondition)), choice.getWP(postCondition));
    }

}