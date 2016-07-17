package utilities;

import java.io.File;

/**
 * Created by gvoiron on 17/07/16.
 * Time : 20:53
 */
public class UDirectory extends File {

    public UDirectory(String path) {
        super(path);
        if (!exists()) {
            throw new Error("The file \"" + path + "\" does not exist.");
        } else if (!isDirectory()) {
            throw new Error("The file \"" + path + "\" is not a directory.");
        }
    }

}
