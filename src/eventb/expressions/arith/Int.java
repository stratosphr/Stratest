package eventb.expressions.arith;

import eventb.expressions.AExpression;
import eventb.tools.formatters.IEventBFormatter;
import eventb.tools.replacer.IAssignableReplacer;

/**
 * Created by gvoiron on 07/07/16.
 * Time : 14:45
 */
public final class Int extends AArithmeticExpression {

    private Integer value;

    public Int(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public String accept(IEventBFormatter visitor) {
        return visitor.visit(this);
    }

    @Override
    public AExpression accept(IAssignableReplacer visitor) {
        return visitor.visit(this);
    }

}
