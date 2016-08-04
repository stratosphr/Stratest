package eventb.expressions.sets;

import eventb.expressions.arith.Int;
import eventb.tools.formatters.EventBFormatter;
import eventb.tools.replacer.AssignableReplacer;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 04/08/16.
 * Time : 11:30
 */
public class RangeSetTest {

    @Test
    public void test_lowerBoundGreaterThanUpperBound() {
        try {
            new RangeSet(new Int(64), new Int(42));
            throw new Error("RangeSet instance constructed with lower bound greater than upper bound occurred and did not throw the expected Error.");
        } catch (Error ignored) {
        }
    }

    @Test
    public void test_acceptEventBFormatter() {
        RangeSet rangeSet = new RangeSet(new Int(42), new Int(64));
        Assert.assertEquals("42..64", rangeSet.accept(new EventBFormatter()));
    }

    @Test
    public void test_acceptAssignableReplacer() {
        RangeSet rangeSet = new RangeSet(new Int(42), new Int(64));
        try {
            rangeSet.accept(new AssignableReplacer(null, null));
            throw new Error("AssignableReplacer visiting RangeSet instance occurred and did not throw the expected Error.");
        } catch (Error ignored) {
        }
    }

    @Test
    public void test_getLowerBound() {
        RangeSet rangeSet = new RangeSet(new Int(42), new Int(64));
        Assert.assertEquals(new Int(42), rangeSet.getLowerBound());
    }

    @Test
    public void test_getUpperBound() {
        RangeSet rangeSet = new RangeSet(new Int(42), new Int(64));
        Assert.assertEquals(new Int(64), rangeSet.getUpperBound());
    }

    @Test
    public void test_getElements() {
        RangeSet rangeSet = new RangeSet(new Int(42), new Int(48));
        Assert.assertEquals(new LinkedHashSet<>(Arrays.asList(new Int(42), new Int(43), new Int(44), new Int(45), new Int(46), new Int(47), new Int(48))), rangeSet.getElements());
    }

}