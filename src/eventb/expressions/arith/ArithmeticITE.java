package eventb.expressions.arith;

import eventb.expressions.AExpression;
import eventb.expressions.bool.ABooleanExpression;
import eventb.tools.formatters.IEventBFormatter;
import eventb.tools.formatters.IExpressionFormatter;
import eventb.tools.replacer.IAssignableReplacer;

/**
 * Created by gvoiron on 08/08/16.
 * Time : 23:41
 */
public final class ArithmeticITE extends AArithmeticExpression {

    private final ABooleanExpression condition;
    private final AArithmeticExpression thenPart;
    private final AArithmeticExpression elsePart;

    public ArithmeticITE(ABooleanExpression condition, AArithmeticExpression thenPart, AArithmeticExpression elsePart) {
        this.condition = condition;
        this.thenPart = thenPart;
        this.elsePart = elsePart;
    }

    @Override
    public String accept(IEventBFormatter visitor) {
        return visitor.visit(this);
    }

    @Override
    public String accept(IExpressionFormatter visitor) {
        return visitor.visit(this);
    }

    @Override
    public AExpression accept(IAssignableReplacer visitor) {
        return visitor.visit(this);
    }

    public ABooleanExpression getCondition() {
        return condition;
    }

    public AArithmeticExpression getThenPart() {
        return thenPart;
    }

    public AArithmeticExpression getElsePart() {
        return elsePart;
    }

}
