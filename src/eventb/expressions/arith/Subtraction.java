package eventb.expressions.arith;

import eventb.expressions.AExpression;
import eventb.tools.formatter.IEventBFormatter;
import eventb.tools.replacer.IAssignableReplacer;

import java.util.Arrays;
import java.util.List;

/**
 * Created by gvoiron on 07/07/16.
 * Time : 14:46
 */
public final class Subtraction extends AArithmeticExpression {

    private final List<AArithmeticExpression> operands;

    public Subtraction(AArithmeticExpression... operands) {
        this.operands = Arrays.asList(operands);
    }

    @Override
    public String accept(IEventBFormatter visitor) {
        return visitor.visit(this);
    }

    @Override
    public AExpression accept(IAssignableReplacer visitor) {
        return visitor.visit(this);
    }

    public List<AArithmeticExpression> getOperands() {
        return operands;
    }

}
