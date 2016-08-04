package eventb.expressions.bool;

import eventb.expressions.AExpression;
import eventb.expressions.arith.AArithmeticExpression;
import eventb.tools.formatters.IEventBFormatter;
import eventb.tools.formatters.IExpressionFormatter;
import eventb.tools.replacer.IAssignableReplacer;

/**
 * Created by gvoiron on 07/07/16.
 * Time : 01:09
 */
public final class Equals extends ABooleanExpression {

    private final AArithmeticExpression left;
    private final AArithmeticExpression right;

    public Equals(AArithmeticExpression left, AArithmeticExpression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String accept(IEventBFormatter visitor) {
        return visitor.visit(this);
    }

    public AArithmeticExpression getLeft() {
        return left;
    }

    public AArithmeticExpression getRight() {
        return right;
    }

    @Override
    public AExpression accept(IAssignableReplacer visitor) {
        return visitor.visit(this);
    }

    @Override
    public String accept(IExpressionFormatter visitor) {
        return visitor.visit(this);
    }

}
