package algorithms.tools;

import eventb.Machine;
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
                //getMachine().getInvariant().prime(true),
                abstractTransition.getSource()
                //abstractTransition.getTarget().prime(),
                //abstractTransition.getEvent().getSubstitution().getWCP()
        )));
        z3.checkSAT();
    }

    public Machine getMachine() {
        return machine;
    }

}
