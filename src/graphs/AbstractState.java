package graphs;

import eventb.expressions.AExpression;
import eventb.expressions.bool.ABooleanExpression;
import eventb.tools.primer.IExpressionToExpressionVisitor;

/**
 * Created by gvoiron on 13/08/16.
 * Time : 15:49
 */
public final class AbstractState extends AState {

    public AbstractState(String name, ABooleanExpression expression) {
        super(name, expression);
    }

    @Override
    public AExpression accept(IExpressionToExpressionVisitor visitor) {
        return visitor.visit(this);
    }

}
