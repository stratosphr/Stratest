package eventb.substitutions;

import eventb.expressions.bool.ABooleanExpression;
import eventb.expressions.bool.Implication;
import eventb.formatter.IEventBFormatter;

/**
 * Created by gvoiron on 06/07/16.
 * Time : 21:44
 */
public final class Select extends ASubstitution {

    private final ABooleanExpression condition;
    private final ASubstitution substitution;

    public Select(ABooleanExpression condition, ASubstitution substitution) {
        this.condition = condition;
        this.substitution = substitution;
    }

    @Override
    public ABooleanExpression getWP(ABooleanExpression postCondition) {
        return new Implication(getCondition(), getSubstitution().getWP(postCondition));
    }

    @Override
    public String accept(IEventBFormatter visitor) {
        return visitor.visit(this);
    }

    public ABooleanExpression getCondition() {
        return condition;
    }

    public ASubstitution getSubstitution() {
        return substitution;
    }

}
