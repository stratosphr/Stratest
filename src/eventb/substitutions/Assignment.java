package eventb.substitutions;

import eventb.expressions.arith.AArithmeticExpression;
import eventb.expressions.arith.AAssignable;
import eventb.expressions.bool.ABooleanExpression;
import eventb.tools.formatters.IEventBFormatter;
import eventb.tools.replacer.AssignableReplacer;

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
