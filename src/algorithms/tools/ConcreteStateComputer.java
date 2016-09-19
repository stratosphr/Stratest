package algorithms.tools;

import eventb.expressions.arith.AAssignable;
import eventb.expressions.arith.Int;
import eventb.expressions.bool.ABooleanExpression;
import eventb.expressions.bool.And;
import eventb.expressions.bool.Equals;
import eventb.tools.primer.Primer;
import graphs.ConcreteState;
import solvers.z3.Model;

import java.util.TreeMap;

/**
 * Created by gvoiron on 18/08/16.
 * Time : 00:16
 */
public final class ConcreteStateComputer {

    public static ConcreteState computeConcreteState(String name, Model model, boolean isSource) {
        return new ConcreteState(name, computeConcreteStateExpression(model, isSource));
    }

    public static ABooleanExpression computeConcreteStateExpression(Model model, boolean isSource) {
        TreeMap<AAssignable, Int> sortedAssignables = new TreeMap<>();
        model.forEach((assignable, ints) -> {
            if (isSource && !assignable.getName().endsWith(Primer.getPrimeSuffix())) {
                if (assignable.getName().endsWith(Primer.getAnteSuffix())) {
                    sortedAssignables.put((AAssignable) assignable.unAnte(), model.get(assignable));
                } else {
                    sortedAssignables.put(assignable, model.get(assignable));
                }
            }
            if (!isSource && assignable.getName().endsWith(Primer.getPrimeSuffix())) {
                sortedAssignables.put((AAssignable) assignable.unPrime(), model.get(assignable));
            }
        });
        return new And(sortedAssignables.keySet().stream().map(assignable -> new Equals(assignable, sortedAssignables.get(assignable))).toArray(ABooleanExpression[]::new));
    }

}
