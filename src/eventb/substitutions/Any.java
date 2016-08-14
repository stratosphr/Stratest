package eventb.substitutions;

import eventb.expressions.arith.Variable;
import eventb.expressions.bool.ABooleanExpression;
import eventb.expressions.bool.ForAll;
import eventb.tools.formatters.IEventBFormatter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by gvoiron on 14/08/16.
 * Time : 16:36
 */
public final class Any extends ASubstitution {

    private final ABooleanExpression wherePart;
    private final ASubstitution thenPart;
    private final List<Variable> quantifiedVariables;

    public Any(ABooleanExpression wherePart, ASubstitution thenPart, Variable... quantifiedVariables) {
        this.wherePart = wherePart;
        this.thenPart = thenPart;
        this.quantifiedVariables = Arrays.asList(quantifiedVariables);
    }

    @Override
    public String accept(IEventBFormatter visitor) {
        return visitor.visit(this);
    }

    @Override
    public ABooleanExpression getWP(ABooleanExpression postCondition) {
        return new ForAll(new Select(getWherePart(), getThenPart()).getWP(postCondition), getQuantifiedVariables().toArray(new Variable[getQuantifiedVariables().size()]));
    }

    public ABooleanExpression getWherePart() {
        return wherePart;
    }

    public ASubstitution getThenPart() {
        return thenPart;
    }

    public List<Variable> getQuantifiedVariables() {
        return quantifiedVariables;
    }

}
