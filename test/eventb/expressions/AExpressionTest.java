package eventb.expressions;

import eventb.expressions.arith.ArithmeticITE;
import eventb.expressions.arith.FunctionCall;
import eventb.expressions.arith.Int;
import eventb.expressions.arith.Variable;
import eventb.expressions.bool.Equals;
import eventb.expressions.sets.CustomSet;
import eventb.expressions.sets.RangeSet;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 11/08/16.
 * Time : 15:33
 */
public class AExpressionTest {

    @Test
    public void getAssignables() throws Exception {
        Variable v1 = new Variable("v1");
        Variable v2 = new Variable("v2");
        Variable v3 = new Variable("v3");
        Variable v4 = new Variable("v4");
        Variable v5 = new Variable("v5");
        FunctionCall fun1 = new FunctionCall(new FunctionDefinition("fun1", new RangeSet(new Int(1), new Int(3)), new CustomSet(new Int(0), new Int(1))), v2, v4);
        FunctionCall fun2 = new FunctionCall(new FunctionDefinition("fun2", new CustomSet(new Int(1), new Int(3)), new CustomSet(new Int(1), new Int(3))), v1, v3);
        FunctionCall fun3 = new FunctionCall(new FunctionDefinition("fun3", new RangeSet(new Int(1), new Int(3)), new RangeSet(new Int(0), new Int(4))), v1, v4);
        ArithmeticITE arithmeticITE = new ArithmeticITE(new Equals(v4, v2), fun1, new ArithmeticITE(new Equals(fun3, fun2), v5, v2));
        Assert.assertEquals(new LinkedHashSet<>(Arrays.asList(v4, v2, fun1, v1, fun3, v3, fun2, v5)), arithmeticITE.getAssignables());
    }

}