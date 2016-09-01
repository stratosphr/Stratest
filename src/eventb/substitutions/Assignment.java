package eventb.substitutions;

import eventb.Machine;
import eventb.expressions.arith.AArithmeticExpression;
import eventb.expressions.arith.AAssignable;
import eventb.expressions.arith.FunctionCall;
import eventb.expressions.arith.Variable;
import eventb.expressions.bool.*;
import eventb.tools.formatters.IEventBFormatter;
import eventb.tools.replacer.AssignableReplacer;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by gvoiron on 07/07/16.
 * Time : 10:35
 */
public final class Assignment extends ASubstitution {

    private final AAssignable assignable;
    private final AArithmeticExpression value;

    public Assignment(AAssignable assignable, AArithmeticExpression value) {
        this.assignable = assignable;
        this.value = value;
    }

    @Override
    public ABooleanExpression getPrd(Machine machine) {
        return getPrd(machine, false);
    }

    public ABooleanExpression getPrd(Machine machine, boolean isInMultipleAssignment) {
        Set<ABooleanExpression> parameters = new LinkedHashSet<>();
        if (getAssignable() instanceof Variable) {
            parameters.add(new Equals((AArithmeticExpression) getAssignable().prime(), getValue()));
        } else if (getAssignable() instanceof FunctionCall) {
            if (!isInMultipleAssignment) {
                Variable quantifiedVariable = new Variable("p!0");
                parameters.add(new ForAll(new Implication(new Not(new Equals(quantifiedVariable, ((FunctionCall) getAssignable()).getOperands().get(0))), new Equals(new FunctionCall(((FunctionCall) getAssignable().prime()).getDefinition(), quantifiedVariable), new FunctionCall(((FunctionCall) getAssignable()).getDefinition(), quantifiedVariable))), quantifiedVariable));
            }
            parameters.add(new Equals((AArithmeticExpression) getAssignable().prime(), getValue()));
        } else {
            throw new Error("Unhandled assignment case : the assignable is neither a Variable or a FunctionCall.");
        }
        if (!isInMultipleAssignment) {
            machine.getAssignables().stream().filter(assignable -> !assignable.getName().equals(getAssignable().getName())).forEach(assignable -> {
                if (assignable instanceof Variable) {
                    parameters.add(new Equals((AArithmeticExpression) assignable.prime(), assignable));
                } else if (assignable instanceof FunctionCall) {
                    Variable quantifiedVariable = new Variable("p!0");
                    parameters.add(new ForAll(new Equals(new FunctionCall(((FunctionCall) assignable).getDefinition(), quantifiedVariable), new FunctionCall(((FunctionCall) assignable.prime()).getDefinition(), quantifiedVariable)), quantifiedVariable));
                } else {
                    throw new Error("Unhandled assignment case : the assignable is neither a Variable or a FunctionCall.");
                }
            });
        }
        return new And(parameters.toArray(new ABooleanExpression[parameters.size()]));
    }

    @Override
    public ABooleanExpression getWP(ABooleanExpression postCondition) {
        return (ABooleanExpression) postCondition.accept(new AssignableReplacer(getAssignable(), getValue()));
    }

    @Override
    public String accept(IEventBFormatter visitor) {
        return visitor.visit(this);
    }

    public AAssignable getAssignable() {
        return assignable;
    }

    public AArithmeticExpression getValue() {
        return value;
    }

}
