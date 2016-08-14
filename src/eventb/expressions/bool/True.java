package eventb.expressions.bool;

import eventb.expressions.AExpression;
import eventb.expressions.arith.AAssignable;
import eventb.tools.formatters.IEventBFormatter;
import eventb.tools.formatters.IExpressionFormatter;
import eventb.tools.primer.IExpressionToExpressionVisitor;
import eventb.tools.replacer.IAssignableReplacer;

import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 07/07/16.
 * Time : 00:03
 */
public final class True extends ABooleanExpression {

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
    public AExpression accept(IExpressionToExpressionVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public LinkedHashSet<AAssignable> getAssignables() {
        return new LinkedHashSet<>();
    }

}
