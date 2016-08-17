package algorithms.tools;

import eventb.expressions.arith.AArithmeticExpression;
import eventb.expressions.bool.ABooleanExpression;
import eventb.expressions.bool.And;
import eventb.expressions.bool.Equals;
import eventb.tools.primer.Primer;
import graphs.ConcreteState;
import solvers.z3.Model;

/**
 * Created by gvoiron on 18/08/16.
 * Time : 00:16
 */
public final class ConcreteStateComputer {

    public static ConcreteState computeConcreteState(String name, Model model, boolean isSource) {
        if (isSource) {
            return new ConcreteState(name, new And(model.keySet().stream().filter(variable -> !variable.getName().contains(Primer.getSuffix())).map(variable -> new Equals(variable, model.get(variable))).toArray(ABooleanExpression[]::new)));
        } else {
            return new ConcreteState(name, new And(model.keySet().stream().filter(variable -> variable.getName().contains(Primer.getSuffix())).map(variable -> new Equals((AArithmeticExpression) variable.unprime(), model.get(variable))).toArray(ABooleanExpression[]::new)));
        }
    }

}
