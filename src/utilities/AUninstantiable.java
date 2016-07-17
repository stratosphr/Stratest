package utilities;

/**
 * Created by gvoiron on 17/07/16.
 * Time : 21:41
 */
public abstract class AUninstantiable {

    public AUninstantiable() {
        throw new Error(getClass() + " class should never be instantiated.");
    }

}
