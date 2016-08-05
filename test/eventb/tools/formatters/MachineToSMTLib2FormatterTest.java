package eventb.tools.formatters;

import eventb.Machine;
import eventb.expressions.arith.Int;
import eventb.expressions.arith.Subtraction;
import eventb.expressions.arith.Variable;
import eventb.expressions.bool.And;
import eventb.expressions.bool.Equals;
import eventb.expressions.bool.True;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by gvoiron on 05/08/16.
 * Time : 10:48
 */
public class MachineToSMTLib2FormatterTest {

    @Test
    public void test_getMachineToSMTLib2() {
        Machine machine = new Machine("aMachine", null, Arrays.asList(new Variable("v1"), new Variable("v2")), new And(new True(), new Equals(new Variable("v1"), new Subtraction(new Variable("v2"), new Int(2)))), null, null);
        Assert.assertEquals("", MachineToSMTLib2Formatter.getMachineToSMTLib2(machine));
    }

}