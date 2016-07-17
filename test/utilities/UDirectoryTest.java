package utilities;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by gvoiron on 17/07/16.
 * Time : 21:03
 */
public class UDirectoryTest {

    @Test
    public void test_invalidDirectory_throwsError() {
        try {
            new UDirectory("lib/z3/z3.jar");
            throw new Error("UDirectory creation with non directory path did not throw the expected Error.");
        } catch (Error ignored) {
            Assert.assertEquals("The file \"lib/z3/z3.jar\" is not a directory.", ignored.getMessage());
        }
        try {
            new UDirectory("");
            throw new Error("UDirectory creation with non existing path did not throw the expected Error.");
        } catch (Error ignored) {
            Assert.assertEquals("The file \"\" does not exist.", ignored.getMessage());
        }
    }

}