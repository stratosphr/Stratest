package eventb.expressions.sets;

import eventb.expressions.arith.Int;
import eventb.tools.formatters.IEventBFormatter;

/**
 * Created by gvoiron on 03/08/16.
 * Time : 23:30
 */
public final class NamedSet extends ASet {

    private final String name;

    public NamedSet(String name, Int... elements) {
        super(elements);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String accept(IEventBFormatter visitor) {
        return visitor.visit(this);
    }

}
