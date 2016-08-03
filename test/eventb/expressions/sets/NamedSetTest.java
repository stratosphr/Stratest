package eventb.expressions.sets;

import eventb.expressions.arith.Int;
import eventb.expressions.arith.Variable;
import eventb.tools.formatters.EventBFormatter;
import eventb.tools.replacer.AssignableReplacer;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by gvoiron on 04/08/16.
 * Time : 00:18
 */
public class NamedSetTest {

    @Test
    public void test_getName() throws Exception {
        NamedSet namedSet = new NamedSet("set1");
        Assert.assertEquals("set1", namedSet.getName());
    }

    @Test
    public void test_acceptEventBFormatter() throws Exception {
        NamedSet namedSet = new NamedSet("set1");
        Assert.assertEquals("(set1 : {})", namedSet.accept(new EventBFormatter()));
        namedSet = new NamedSet("set2", new Int(0), new Int(42));
        Assert.assertEquals("(set2 : {0, 42})", namedSet.accept(new EventBFormatter()));
    }

    @Test
    public void test_acceptAssignableReplacer() throws Exception {
        NamedSet namedSet = new NamedSet("set1");
        try {
            namedSet.accept(new AssignableReplacer(new Variable("a1"), new Int(42)));
            throw new Error("AssignableReplacer visiting NamedSet instance occurred and did not throw the expected Error.");
        } catch (Error ignored) {
        }
    }

}