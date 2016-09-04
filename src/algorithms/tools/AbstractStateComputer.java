package algorithms.tools;

import eventb.Machine;
import eventb.expressions.bool.ABooleanExpression;
import eventb.expressions.bool.And;
import eventb.tools.formatters.ExpressionToSMTLib2Formatter;
import graphs.AbstractState;
import solvers.z3.Z3;

import java.util.ArrayList;
import java.util.List;

import static com.microsoft.z3.Status.SATISFIABLE;

/**
 * Created by gvoiron on 03/09/16.
 * Time : 10:41
 */
public class AbstractStateComputer {

    public static AbstractState computeAbstractState(ABooleanExpression booleanExpression, Machine machine, List<AbstractState> abstractStates) {
        Z3 z3 = new Z3();
        List<AbstractState> abstractStatesList = new ArrayList<>(abstractStates);
        for (int i = 0; i < abstractStates.size(); i++) {
            z3.reset();
            z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(booleanExpression, machine.getInvariant(), abstractStatesList.get(i))));
            if (z3.checkSAT() == SATISFIABLE) {
                return abstractStatesList.get(i);
            }
        }
        throw new Error("Impossible case encountered in abstract state computation: expression \"" + booleanExpression + "\" does not have any abstract state.");
    }

}
