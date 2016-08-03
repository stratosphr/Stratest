package eventb.substitutions;

import eventb.expressions.bool.ABooleanExpression;
import eventb.expressions.bool.And;
import eventb.expressions.bool.Implication;
import eventb.expressions.bool.Not;
import eventb.tools.formatters.IEventBFormatter;

/**
 * Created by gvoiron on 07/07/16.
 * Time : 23:12
 */
public final class IfThenElse extends ASubstitution {

    private final ABooleanExpression condition;
    private final ASubstitution thenPart;
    private final ASubstitution elsePart;

    public IfThenElse(ABooleanExpression condition, ASubstitution thenPart, ASubstitution elsePart) {
        this.condition = condition;
        this.thenPart = thenPart;
        this.elsePart = elsePart;
    }

    @Override
    public ABooleanExpression getWP(ABooleanExpression postCondition) {
        return new And(new Implication(getCondition(), getThenPart().getWP(postCondition)), new Implication(new Not(getCondition()), getElsePart().getWP(postCondition)));
    }

    @Override
    public String accept(IEventBFormatter visitor) {
        return visitor.visit(this);
    }

    public ABooleanExpression getCondition() {
        return condition;
    }

    public ASubstitution getThenPart() {
        return thenPart;
    }

    public ASubstitution getElsePart() {
        return elsePart;
    }

}
