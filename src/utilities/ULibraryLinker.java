package utilities;

import java.lang.reflect.Field;

/**
 * Created by gvoiron on 26/02/16.
 * Time : 15:46
 */
public final class ULibraryLinker extends AUninstantiable {

    private ULibraryLinker() {
        super();
    }

    public static void addLibraryDirectory(UDirectory libraryFolder) {
        if (libraryFolder != null) {
            System.setProperty("java.library.path", System.getProperty("java.library.path") + ":" + libraryFolder.getAbsolutePath());
            try {
                Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
                fieldSysPath.setAccessible(true);
                fieldSysPath.set(null, null);
                fieldSysPath.setAccessible(false);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new Error(e);
            }
        } else {
            throw new Error("The library folder to be linked can't be null.");
        }
    }

}
