package eventb.expressions.sets;

import eventb.expressions.arith.Int;
import eventb.tools.formatters.IEventBFormatter;

/**
 * Created by gvoiron on 03/08/16.
 * Time : 23:30
 */
public final class CustomSet extends ASet {

    public CustomSet(Int... elements) {
        super(elements);
    }

    @Override
    public String accept(IEventBFormatter visitor) {
        return visitor.visit(this);
    }

}
