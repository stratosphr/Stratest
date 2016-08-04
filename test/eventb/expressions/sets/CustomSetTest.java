package eventb.expressions.sets;

import eventb.expressions.arith.Int;
import eventb.tools.formatters.EventBFormatter;
import eventb.tools.replacer.AssignableReplacer;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by gvoiron on 04/08/16.
 * Time : 11:44
 */
public class CustomSetTest {

    @Test
    public void test_acceptEventBFormatter() {
        EventBFormatter eventBFormatter = new EventBFormatter();
        CustomSet customSet = new CustomSet();
        Assert.assertEquals("{}", customSet.accept(eventBFormatter));
        customSet = new CustomSet(new Int(0));
        Assert.assertEquals("{0}", customSet.accept(eventBFormatter));
        customSet = new CustomSet(new Int(2), new Int(42), new Int(64));
        Assert.assertEquals("{2, 42, 64}", customSet.accept(eventBFormatter));
    }

    @Test
    public void test_acceptAssignableReplacer() {
        try {
            new CustomSet().accept(new AssignableReplacer(null, null));
            throw new Error("AssignableReplacer visiting CustomSet instance occurred and did not throw the expected Error.");
        } catch (Error ignored) {
        }
    }

}