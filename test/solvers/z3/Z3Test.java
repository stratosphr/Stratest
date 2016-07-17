package solvers.z3;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by gvoiron on 17/07/16.
 * Time : 21:03
 */
public class Z3Test {

    @Test
    public void test_defaultConstructor_loadsLibraries() {
        Z3 z3 = new Z3();
        Assert.assertEquals("1", z3.getContext().mkInt(1).toString());
    }

    @Test
    public void test_getSolver() {
        Z3 z3 = new Z3();
        Assert.assertNotNull(z3.getSolver());
    }

}