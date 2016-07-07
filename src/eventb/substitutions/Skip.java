package eventb.substitutions;

import eventb.expressions.bool.ABooleanExpression;
import eventb.tools.formatter.IEventBFormatter;

/**
 * Created by gvoiron on 06/07/16.
 * Time : 21:44
 */
public final class Skip extends ASubstitution {

    @Override
    public ABooleanExpression getWP(ABooleanExpression postCondition) {
        return postCondition;
    }

    @Override
    public String accept(IEventBFormatter visitor) {
        return visitor.visit(this);
    }

}
