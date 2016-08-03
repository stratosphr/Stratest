package eventb.substitutions;

import eventb.expressions.arith.*;
import eventb.tools.formatters.EventBFormatter;
import org.junit.Assert;
import org.junit.Test;
import utilities.UCharacters;

/**
 * Created by gvoiron on 07/07/16.
 * Time : 14:28
 */
public class MultipleAssignmentTest {

    @Test
    public void test_getWP() {
        Variable x = new Variable("x");
        Int two = new Int(2);
        Int five = new Int(5);
        Int twenty = new Int(20);
        MultipleAssignment multipleAssignment = new MultipleAssignment(new Assignment(x, new Subtraction(x, five)), new Assignment(x, new Multiplication(x, two)));
        Assert.assertEquals(new GreaterThan(new Multiplication(new Subtraction(x, five), two), twenty), multipleAssignment.getWP(new GreaterThan(x, twenty)));
    }

    @Test
    public void test_acceptEventBFormatter() {
        Variable x = new Variable("x");
        MultipleAssignment multipleAssignment = new MultipleAssignment(new Assignment(x, new Subtraction(x, new Int(5))), new Assignment(x, new Multiplication(x, new Int(2))));
        Assert.assertEquals("x := (x - 5) ||" + UCharacters.LINE_SEPARATOR + "x := (x * 2)", multipleAssignment.accept(new EventBFormatter()));
    }

    @Test
    public void test_getAssignments() {

    }

    @Test
    public void test_getWP1() {

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