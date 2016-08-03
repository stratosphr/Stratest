package eventb.expressions.bool;

import eventb.tools.formatters.EventBFormatter;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by gvoiron on 07/07/16.
 * Time : 00:30
 */
public class TrueTest {

    @Test
    public void test_accept() {
        True aTrue = new True();
        Assert.assertEquals("_true_", aTrue.accept(new EventBFormatter()));
        Assert.assertEquals(aTrue.toString(), aTrue.accept(new EventBFormatter()));
    }

    @Test
    public void test_hashCode() {
        True aTrue = new True();
        Assert.assertEquals(aTrue.accept(new EventBFormatter()).hashCode(), aTrue.hashCode());
    }

    @Test
    public void test_equals() {
        True aTrue = new True();
        Assert.assertNotEquals(null, aTrue);
        Assert.assertEquals(new True(), aTrue);
        Assert.assertEquals(aTrue, aTrue);
    }

    @Test
    public void test_toString() {
        Assert.assertEquals("_true_", new True().toString());
    }

}