package eventb.substitutions;

import eventb.expressions.arith.Variable;
import eventb.expressions.bool.Equals;
import eventb.expressions.bool.Implication;
import eventb.expressions.bool.True;
import eventb.tools.formatters.EventBFormatter;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by gvoiron on 07/07/16.
 * Time : 10:35
 */
public class AssignmentTest {

    @Test
    public void test_getWP() {
        Variable assignable = new Variable("assignable");
        Variable value = new Variable("value");
        Assignment assignment = new Assignment(assignable, value);
        Assert.assertEquals(new Implication(new Equals(value, value), new Implication(new True(), new Equals(value, value))), assignment.getWP(new Implication(new Equals(assignable, value), new Implication(new True(), new Equals(assignable, value)))));
    }

    @Test
    public void test_accept() {
        Variable assignable = new Variable("assignable");
        Variable value = new Variable("value");
        Assignment assignment = new Assignment(assignable, value);
        Assert.assertEquals(assignable.getName() + " := " + value.getName(), assignment.accept(new EventBFormatter()));
        Assert.assertEquals("assignable := value", assignment.accept(new EventBFormatter()));
    }

}