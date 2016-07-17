package utilities;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by gvoiron on 17/07/16.
 * Time : 21:39
 */
public class ULibraryLinkerTest {

    @Test
    public void test_addLibraryDirectory_withNullLibraryFolder_throwsError() throws Exception {
        try {
            ULibraryLinker.addLibraryDirectory(null);
            throw new Error("Library directory linkage with null directory did not throw the expected Error.");
        } catch (Error e) {
            Assert.assertEquals("The library folder to be linked can't be null.", e.getMessage());
        }
    }

}