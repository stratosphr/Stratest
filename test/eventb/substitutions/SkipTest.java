package eventb.substitutions;

import eventb.expressions.bool.True;
import eventb.tools.formatter.EventBFormatter;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by gvoiron on 06/07/16.
 * Time : 22:11
 */
public class SkipTest {

    @Test
    public void test_getWCP() {
        True wp = new True();
        Assert.assertEquals(wp, new Skip().getWP(wp));
    }

    @Test
    public void test_accept() {
        Skip skip = new Skip();
        Assert.assertEquals("SKIP", skip.accept(new EventBFormatter()));
        Assert.assertEquals(skip.toString(), skip.accept(new EventBFormatter()));
    }

    @Test
    public void test_hashCode() {
        Skip skip = new Skip();
        Assert.assertEquals(skip.accept(new EventBFormatter()).hashCode(), skip.hashCode());
    }

    @Test
    public void test_equals() {
        Skip skip = new Skip();
        Assert.assertNotEquals(null, skip);
        Assert.assertEquals(new Skip(), skip);
        Assert.assertNotEquals(new Skip(), new Select(new True(), new Skip()));
        Assert.assertEquals(skip, skip);
    }

    @Test
    public void test_toString() {
        Assert.assertEquals("SKIP", new Skip().toString());
    }

}