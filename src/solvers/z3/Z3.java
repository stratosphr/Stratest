package solvers.z3;

import com.microsoft.z3.Context;
import com.microsoft.z3.Solver;
import utilities.UDirectory;
import utilities.ULibraryLinker;

/**
 * Created by gvoiron on 17/07/16.
 * Time : 20:50
 */
public class Z3 {

    private final Context context;
    private final Solver solver;

    public Z3() {
        this(new UDirectory("lib/z3"));
    }

    public Z3(UDirectory z3LibrariesDirectory) {
        ULibraryLinker.addLibraryDirectory(z3LibrariesDirectory);
        this.context = new Context();
        this.solver = getContext().mkSolver();
    }

    public Context getContext() {
        return context;
    }

    public Solver getSolver() {
        return solver;
    }

}
