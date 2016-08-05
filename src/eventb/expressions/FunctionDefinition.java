package eventb.expressions;

import eventb.AObjectEventB;
import eventb.expressions.sets.ASet;
import eventb.tools.formatters.IEventBFormatter;

/**
 * Created by gvoiron on 05/08/16.
 * Time : 13:56
 */
public final class FunctionDefinition extends AObjectEventB {

    private final String name;
    private final ASet domain;
    private final ASet coDomain;

    public FunctionDefinition(String name, ASet domain, ASet coDomain) {
        this.name = name;
        this.domain = domain;
        this.coDomain = coDomain;
    }

    @Override
    public String accept(IEventBFormatter visitor) {
        return visitor.visit(this);
    }

    public String getName() {
        return name;
    }

    public ASet getDomain() {
        return domain;
    }

    public ASet getCoDomain() {
        return coDomain;
    }

}
