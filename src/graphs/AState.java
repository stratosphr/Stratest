package graphs;

import eventb.expressions.AExpression;
import eventb.expressions.arith.AAssignable;
import eventb.expressions.bool.ABooleanExpression;
import eventb.tools.formatters.IEventBFormatter;
import eventb.tools.formatters.IExpressionFormatter;
import eventb.tools.replacer.IAssignableReplacer;

import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 13/08/16.
 * Time : 15:47
 */
public abstract class AState extends ABooleanExpression {

    private String name;
    private final ABooleanExpression expression;

    public AState(String name, ABooleanExpression expression) {
        this.name = name;
        this.expression = expression;
    }

    @Override
    public final String accept(IEventBFormatter visitor) {
        return visitor.visit(this);
    }

    @Override
    public final String accept(IExpressionFormatter visitor) {
        return visitor.visit(this);
    }

    @Override
    public final AExpression accept(IAssignableReplacer visitor) {
        return visitor.visit(this);
    }

    public final String getName() {
        return name;
    }

    public final ABooleanExpression getExpression() {
        return expression;
    }

    @Override
    public final LinkedHashSet<AAssignable> getAssignables() {
        return getExpression().getAssignables();
    }

}
