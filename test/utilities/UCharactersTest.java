package utilities;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by gvoiron on 08/07/16.
 * Time : 00:15
 */
public class UCharactersTest {

    @Test
    public void test_construction_throwsError() {
        Constructor constructor = UCharacters.class.getDeclaredConstructors()[0];
        try {
            constructor.setAccessible(true);
            constructor.newInstance();
            throw new Error("UCharacters instantiation occurred and did not throw the expected Error.");
        } catch (Error | IllegalAccessException | InvocationTargetException | InstantiationException ignored) {
            constructor.setAccessible(false);
        }
    }

}