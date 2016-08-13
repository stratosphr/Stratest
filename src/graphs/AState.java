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

    public String getName() {
        return name;
    }

    public ABooleanExpression getExpression() {
        return expression;
    }

    @Override
    public LinkedHashSet<AAssignable> getAssignables() {
        return getExpression().getAssignables();
    }

}
