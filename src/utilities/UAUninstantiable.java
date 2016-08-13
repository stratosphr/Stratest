package utilities;

/**
 * Created by gvoiron on 17/07/16.
 * Time : 21:41
 */
public abstract class UAUninstantiable {

    public UAUninstantiable() {
        throw new Error(getClass() + " class should never be instantiated.");
    }

}
