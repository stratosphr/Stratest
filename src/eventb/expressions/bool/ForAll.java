package eventb.expressions.bool;

import eventb.expressions.AExpression;
import eventb.expressions.arith.AAssignable;
import eventb.expressions.arith.Variable;
import eventb.tools.formatters.IEventBFormatter;
import eventb.tools.formatters.IExpressionFormatter;
import eventb.tools.primer.IExpressionToExpressionVisitor;
import eventb.tools.replacer.IAssignableReplacer;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by gvoiron on 14/08/16.
 * Time : 16:52
 */
public final class ForAll extends ABooleanExpression {

    private final ABooleanExpression expression;
    private final List<Variable> quantifiedVariables;

    public ForAll(ABooleanExpression expression, Variable... quantifiedVariables) {
        this.expression = expression;
        this.quantifiedVariables = Arrays.asList(quantifiedVariables);
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

    public List<Variable> getQuantifiedVariables() {
        return quantifiedVariables;
    }

    public ABooleanExpression getExpression() {
        return expression;
    }

}
