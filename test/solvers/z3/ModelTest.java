package solvers.z3;

import eventb.expressions.FunctionDefinition;
import eventb.expressions.arith.FunctionCall;
import eventb.expressions.arith.Int;
import eventb.expressions.arith.Variable;
import eventb.expressions.bool.And;
import eventb.expressions.bool.Equals;
import eventb.expressions.sets.CustomSet;
import eventb.expressions.sets.RangeSet;
import eventb.tools.formatters.ExpressionToSMTLib2Formatter;
import eventb.tools.primer.Primer;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by gvoiron on 17/08/16.
 * Time : 10:42
 */
public class ModelTest {

    @Test
    public void test_instantiation() {
        Z3 z3 = new Z3();
        Int one = new Int(1);
        Int three = new Int(3);
        Int four = new Int(4);
        Variable v1 = new Variable("v1");
        FunctionDefinition functionDefinition = new FunctionDefinition("fun", new CustomSet(one, three, four), new RangeSet(one, three));
        FunctionCall functionCall = new FunctionCall(functionDefinition, one);
        z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(new Equals(v1, one), new Equals(functionCall, three))));
        z3.checkSAT();
        Model model = z3.getModel();
        Assert.assertEquals(one, model.get(v1));
        Assert.assertEquals(three, model.get(new Variable("fun!1")));
        z3.reset();
        z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(new Equals(v1, one), new Equals(functionCall, three)).prime(true)));
        z3.checkSAT();
        model = z3.getModel();
        Assert.assertEquals(one, model.get(new Variable("v1" + Primer.getPrimeSuffix())));
        Assert.assertEquals(three, model.get(new Variable("fun" + Primer.getPrimeSuffix() + "!1")));
    }

}