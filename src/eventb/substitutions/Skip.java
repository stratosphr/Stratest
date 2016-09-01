package eventb.substitutions;

import eventb.Machine;
import eventb.expressions.bool.ABooleanExpression;
import eventb.expressions.bool.True;
import eventb.tools.formatters.IEventBFormatter;

/**
 * Created by gvoiron on 06/07/16.
 * Time : 21:44
 */
public final class Skip extends ASubstitution {

    @Override
    public ABooleanExpression getPrd(Machine machine) {
        return new True();
    }

    @Override
    public ABooleanExpression getWP(ABooleanExpression postCondition) {
        return postCondition;
    }

    @Override
    public String accept(IEventBFormatter visitor) {
        return visitor.visit(this);
    }

}
