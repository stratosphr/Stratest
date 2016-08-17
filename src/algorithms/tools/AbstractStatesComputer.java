package algorithms.tools;

import com.microsoft.z3.Status;
import eventb.Machine;
import eventb.expressions.bool.ABooleanExpression;
import eventb.expressions.bool.And;
import eventb.expressions.bool.Not;
import eventb.expressions.bool.Predicate;
import eventb.tools.formatters.ExpressionToSMTLib2Formatter;
import graphs.AbstractState;
import solvers.z3.Z3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gvoiron on 17/08/16.
 * Time : 09:03
 */
public final class AbstractStatesComputer {

    public static List<AbstractState> computeAbstractStates(Machine machine, List<Predicate> abstractionPredicates) {
        List<AbstractState> abstractStates = new ArrayList<>();
        for (int i = 0; i < Math.pow(abstractionPredicates.size(), 2); i++) {
            List<ABooleanExpression> andOperands = new ArrayList<>();
            String binaryString = String.format("%2s", Integer.toBinaryString(i)).replace(' ', '0');
            for (int j = 0, n = binaryString.length(); j < n; j++) {
                char c = binaryString.charAt(j);
                if (c == '0') {
                    andOperands.add(new Not(abstractionPredicates.get(j)));
                } else if (c == '1') {
                    andOperands.add(abstractionPredicates.get(j));
                } else {
                    throw new Error("The binary representation of a number should never contain any other characters than '0' and '1'.");
                }
            }
            And and = new And(andOperands.toArray(new ABooleanExpression[andOperands.size()]));
            Z3 z3 = new Z3();
            z3.addCode(ExpressionToSMTLib2Formatter.formatExpression(new And(machine.getInvariant(), and)));
            if (z3.checkSAT() == Status.SATISFIABLE) {
                abstractStates.add(new AbstractState("q" + i, and));
            }
        }
        return abstractStates;
    }

}
