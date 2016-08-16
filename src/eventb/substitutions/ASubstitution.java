package eventb.substitutions;

import eventb.AObjectEventB;
import eventb.Machine;
import eventb.expressions.arith.AArithmeticExpression;
import eventb.expressions.arith.Variable;
import eventb.expressions.bool.*;
import eventb.tools.formatters.IEventBFormatterVisitable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by gvoiron on 06/07/16.
 * Time : 21:43
 */
public abstract class ASubstitution extends AObjectEventB implements IEventBFormatterVisitable {

    public abstract ABooleanExpression getWP(ABooleanExpression postCondition);

    public final ABooleanExpression getWCP(ABooleanExpression postCondition) {
        return new Not(getWP(new Not(postCondition)));
    }

    public final ABooleanExpression getSP(ABooleanExpression preCondition, Machine machine) {
        List<Variable> variables = machine.getAssignables().stream().filter(assignable -> assignable instanceof Variable).map(assignable -> (Variable) assignable).collect(Collectors.toList());
        return new Exists(new And(machine.getInvariant(), preCondition, getWCP(new And(variables.stream().map(variable -> new Equals(variable, (AArithmeticExpression) variable.prime())).toArray(ABooleanExpression[]::new)))), variables.toArray(new Variable[variables.size()]));
    }

}
