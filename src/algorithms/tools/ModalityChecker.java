package algorithms.tools;

import com.microsoft.z3.Status;
import eventb.Machine;
import eventb.expressions.bool.ABooleanExpression;
import eventb.expressions.bool.And;
import eventb.tools.formatters.ExpressionToSMTLib2Formatter;
import graphs.AbstractTransition;
import solvers.z3.Z3;

/**
 * Created by gvoiron on 13/08/16.
 * Time : 15:45
 */
public final class ModalityChecker {

    private Machine machine;

    public ModalityChecker(Machine machine) {
        this.machine = machine;
    }

    public void isMay(AbstractTransition abstractTransition) {
        Z3 z3 = new Z3();
        z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(
                getMachine().getInvariant(),
                (ABooleanExpression) getMachine().getInvariant().prime(true),
                abstractTransition.getSource(),
                abstractTransition.getEvent().getSubstitution().getWCP((ABooleanExpression) abstractTransition.getTarget().prime())
        )));
        Status status = z3.checkSAT();
        System.out.println(status);
    }

    public Machine getMachine() {
        return machine;
    }

}
