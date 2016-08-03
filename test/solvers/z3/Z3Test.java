package solvers.z3;

import com.microsoft.z3.Status;
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
        Assert.assertEquals(Status.SATISFIABLE, z3.checkSAT());
        z3.addCode("(define-fun x () Int 1)");
        z3.addCode("(define-fun y () Int 2)");
        Assert.assertEquals(Status.SATISFIABLE, z3.checkSAT());
        //Assert.assertEquals("1", z3.getContext().mkInt(1).toString());
    }

    @Test
    public void test_nonParsableCode_throwsError() {
        Z3 z3 = new Z3();
        z3.addCode("NON PARSABLE CODE");
        try {
            z3.checkSAT();
            throw new Error("Non parsable SMT-Lib2 parsing occurred and did not throw the expected Error.");
        } catch (Error ignored) {
        }
    }

}