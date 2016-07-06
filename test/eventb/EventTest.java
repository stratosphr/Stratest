package eventb;

import eventb.formatter.EventBFormatter;
import eventb.substitutions.Skip;
import org.junit.Assert;
import org.junit.Test;
import utilities.UCharacters;

/**
 * Created by gvoiron on 06/07/16.
 * Time : 21:55
 */
public class EventTest {

    @Test
    public void test_getName() {
        String name = "name";
        Assert.assertEquals(name, new Event(name, null).getName());
    }

    @Test
    public void test_getSubstitution() {
        Skip skip = new Skip();
        Assert.assertEquals(skip, new Event("name", skip).getSubstitution());
    }

    @Test
    public void test_toString() {
        String name = "name";
        Skip skip = new Skip();
        EventBFormatter eventBFormatter = new EventBFormatter();
        Assert.assertEquals(name + " " + UCharacters.EQ_DEF + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + skip.accept(eventBFormatter), new Event(name, new Skip()).accept(eventBFormatter));
    }

}