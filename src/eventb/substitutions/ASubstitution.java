package eventb.substitutions;

import eventb.AObjectEventB;
import eventb.expressions.bool.ABooleanExpression;
import eventb.tools.formatters.IEventBFormatterVisitable;

/**
 * Created by gvoiron on 06/07/16.
 * Time : 21:43
 */
public abstract class ASubstitution extends AObjectEventB implements IEventBFormatterVisitable {

    public abstract ABooleanExpression getWP(ABooleanExpression postCondition);

}
