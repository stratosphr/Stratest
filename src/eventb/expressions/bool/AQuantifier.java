package eventb.expressions.bool;

import eventb.expressions.arith.AAssignable;
import eventb.expressions.arith.Variable;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by gvoiron on 16/08/16.
 * Time : 22:40
 */
public abstract class AQuantifier extends ABooleanExpression {

    private final ABooleanExpression expression;
    private final List<Variable> quantifiedVariables;

    public AQuantifier(ABooleanExpression expression, Variable... quantifiedVariables) {
        this.expression = expression;
        this.quantifiedVariables = Arrays.asList(quantifiedVariables);
    }

    public ABooleanExpression getExpression() {
        return expression;
    }

    public List<Variable> getQuantifiedVariables() {
        return quantifiedVariables;
    }

    @Override
    public LinkedHashSet<AAssignable> getAssignables() {
        return getExpression().getAssignables();
    }

}
