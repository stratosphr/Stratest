package solvers.z3;

import com.microsoft.z3.*;
import utilities.UCharacters;
import utilities.UDirectory;
import utilities.ULibraryLinker;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 17/07/16.
 * Time : 20:50
 */
public abstract class AZ3 {

    private final Context context;
    private final Solver solver;
    private final List<String> lines;

    public AZ3() {
        this(new UDirectory("lib/z3"));
    }

    public AZ3(UDirectory z3LibrariesDirectory) {
        ULibraryLinker.addLibraryDirectory(z3LibrariesDirectory);
        this.context = new Context();
        this.solver = getContext().mkSolver();
        lines = new ArrayList<>();
    }

    public void addCode(String code) {
        getLines().add(code);
    }

    public Status checkSAT() {
        try {
            addCode("(check-sat)");
            BoolExpr boolExpr = getContext().parseSMTLIB2String(getLines().stream().collect(Collectors.joining(UCharacters.LINE_SEPARATOR)), null, null, null, null);
            getSolver().add(boolExpr);
            return getSolver().check();
        } catch (Z3Exception e) {
            throw new Error("SMT-Lib2 code cannot be parsed by z3." + UCharacters.LINE_SEPARATOR + "SMT-Lib2 code was :" + UCharacters.LINE_SEPARATOR + getLines().stream().collect(Collectors.joining(UCharacters.LINE_SEPARATOR)));
        }
    }

    public Model getModel() {
        return new Model(this, getSolver().getModel());
    }

    public void reset() {
        getLines().clear();
        getSolver().reset();
    }

    public Context getContext() {
        return context;
    }

    private Solver getSolver() {
        return solver;
    }

    public List<String> getLines() {
        return lines;
    }

}
