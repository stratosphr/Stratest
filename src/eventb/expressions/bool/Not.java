package eventb.expressions.bool;

import eventb.expressions.AExpression;
import eventb.expressions.IUnaryOperation;
import eventb.tools.formatters.IEventBFormatter;
import eventb.tools.formatters.IExpressionFormatter;
import eventb.tools.replacer.IAssignableReplacer;

/**
 * Created by gvoiron on 07/07/16.
 * Time : 23:39
 */
public final class Not extends ABooleanExpression implements IUnaryOperation {

    private final ABooleanExpression operand;

    public Not(ABooleanExpression operand) {
        this.operand = operand;
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
    public ABooleanExpression getOperand() {
        return operand;
    }

}
