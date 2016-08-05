package eventb.expressions.bool;

import eventb.expressions.AExpression;
import eventb.expressions.IBinaryOperation;
import eventb.tools.formatters.IEventBFormatter;
import eventb.tools.formatters.IExpressionFormatter;
import eventb.tools.replacer.IAssignableReplacer;

/**
 * Created by gvoiron on 07/07/16.
 * Time : 01:09
 */
public final class Implication extends ABooleanExpression implements IBinaryOperation {

    private final ABooleanExpression left;
    private final ABooleanExpression right;

    public Implication(ABooleanExpression left, ABooleanExpression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String accept(IEventBFormatter visitor) {
        return visitor.visit(this);
    }

    @Override
    public AExpression accept(IAssignableReplacer visitor) {
        return visitor.visit(this);
    }

    @Override
    public String accept(IExpressionFormatter visitor) {
        return visitor.visit(this);
    }

    @Override
    public ABooleanExpression getLeft() {
        return left;
    }

    @Override
    public ABooleanExpression getRight() {
        return right;
    }

}
