package eventb.expressions.bool;

import eventb.formatter.IEventBFormatter;

/**
 * Created by gvoiron on 07/07/16.
 * Time : 00:03
 */
public final class True extends ABooleanExpression {

    @Override
    public String accept(IEventBFormatter visitor) {
        return visitor.visit(this);
    }

}
