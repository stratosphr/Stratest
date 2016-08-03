package utilities;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by gvoiron on 17/07/16.
 * Time : 21:39
 */
public class ULibraryLinkerTest {

    @Test
    public void test_construction_throwsError() {
        Constructor constructor = ULibraryLinker.class.getDeclaredConstructors()[0];
        try {
            constructor.setAccessible(true);
            constructor.newInstance();
            throw new Error("ULibraryLinker instantiation occurred and did not throw the expected Error.");
        } catch (Error | IllegalAccessException | InvocationTargetException | InstantiationException ignored) {
            constructor.setAccessible(false);
        }
    }

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