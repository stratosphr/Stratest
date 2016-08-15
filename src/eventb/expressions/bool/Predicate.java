package eventb.expressions.bool;

import eventb.expressions.AExpression;
import eventb.expressions.arith.AAssignable;
import eventb.tools.formatters.IEventBFormatter;
import eventb.tools.formatters.IExpressionFormatter;
import eventb.tools.primer.IExpressionToExpressionVisitor;
import eventb.tools.replacer.IAssignableReplacer;

import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 15/08/16.
 * Time : 08:46
 */
public final class Predicate extends APredicate {

    public Predicate(String name, ABooleanExpression expression) {
        super(name, expression);
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
    public AExpression accept(IExpressionToExpressionVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public AExpression accept(IAssignableReplacer visitor) {
        return visitor.visit(this);
    }

    @Override
    public LinkedHashSet<AAssignable> getAssignables() {
        return getExpression().getAssignables();
    }

}
