package eventb.expressions;

import eventb.expressions.arith.Int;
import eventb.expressions.sets.CustomSet;
import eventb.expressions.sets.RangeSet;
import eventb.tools.formatters.EventBFormatter;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by gvoiron on 05/08/16.
 * Time : 14:34
 */
public class FunctionDefinitionTest {

    @Test
    public void test_acceptEventBFormatter() throws Exception {
        FunctionDefinition functionDefinition = new FunctionDefinition("fun", new RangeSet(new Int(0), new Int(4)), new CustomSet(new Int(0), new Int(1), new Int(3)));
        Assert.assertEquals("fun : 0..4 -> {0, 1, 3}", functionDefinition.accept(new EventBFormatter()));
    }

    @Test
    public void getName() throws Exception {
        RangeSet domain = new RangeSet(new Int(0), new Int(4));
        CustomSet coDomain = new CustomSet(new Int(0), new Int(1), new Int(3));
        FunctionDefinition functionDefinition = new FunctionDefinition("fun", domain, coDomain);
        Assert.assertEquals("fun", functionDefinition.getName());
    }

    @Test
    public void getDomain() throws Exception {
        RangeSet domain = new RangeSet(new Int(0), new Int(4));
        CustomSet coDomain = new CustomSet(new Int(0), new Int(1), new Int(3));
        FunctionDefinition functionDefinition = new FunctionDefinition("fun", domain, coDomain);
        Assert.assertEquals(domain, functionDefinition.getDomain());
    }

    @Test
    public void getCoDomain() throws Exception {
        RangeSet domain = new RangeSet(new Int(0), new Int(4));
        CustomSet coDomain = new CustomSet(new Int(0), new Int(1), new Int(3));
        FunctionDefinition functionDefinition = new FunctionDefinition("fun", domain, coDomain);
        Assert.assertEquals(coDomain, functionDefinition.getCoDomain());
    }

}