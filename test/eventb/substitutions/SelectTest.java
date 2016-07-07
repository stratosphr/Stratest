package eventb.substitutions;

import eventb.expressions.bool.Implication;
import eventb.expressions.bool.True;
import eventb.tools.formatter.EventBFormatter;
import org.junit.Assert;
import org.junit.Test;
import utilities.UCharacters;

/**
 * Created by gvoiron on 07/07/16.
 * Time : 00:48
 */
public class SelectTest {

    @Test
    public void test_getWP() {
        True aTrue = new True();
        Assert.assertEquals(new Implication(aTrue, aTrue), new Select(aTrue, new Skip()).getWP(aTrue));
    }

    @Test
    public void test_accept() {
        True condition = new True();
        Skip skip = new Skip();
        Select select = new Select(condition, skip);
        EventBFormatter eventBFormatter = new EventBFormatter();
        Assert.assertEquals("SELECT" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + condition.accept(eventBFormatter) + UCharacters.LINE_SEPARATOR + "THEN" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + skip.accept(eventBFormatter) + UCharacters.LINE_SEPARATOR + "END", select.accept(eventBFormatter));
        Assert.assertEquals(select.toString(), select.accept(new EventBFormatter()));
    }

    @Test
    public void test_getCondition() {
        True condition = new True();
        Assert.assertEquals(condition, new Select(condition, new Skip()).getCondition());
    }

    @Test
    public void test_getSubstitution() {
        Skip skip = new Skip();
        Assert.assertEquals(skip, new Select(new True(), skip).getSubstitution());
    }

    @Test
    public void test_hashCode() {
        Select select = new Select(new True(), new Skip());
        Assert.assertEquals(select.accept(new EventBFormatter()).hashCode(), select.hashCode());
    }

    @Test
    public void test_equals() {
        Select select = new Select(new True(), new Skip());
        Assert.assertNotEquals(null, select);
        Assert.assertEquals(new Select(new True(), new Skip()), select);
        Assert.assertNotEquals(new Select(new True(), new Skip()), new Skip());
        Assert.assertEquals(select, select);
    }

    @Test
    public void test_toString() {
        True condition = new True();
        Skip substitution = new Skip();
        EventBFormatter eventBFormatter = new EventBFormatter();
        Assert.assertEquals("SELECT" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + condition.accept(eventBFormatter) + UCharacters.LINE_SEPARATOR + "THEN" + UCharacters.LINE_SEPARATOR + UCharacters.TABULATION + substitution.accept(eventBFormatter) + UCharacters.LINE_SEPARATOR + "END", new Select(condition, substitution).toString());
    }

}