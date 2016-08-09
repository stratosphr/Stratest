package eventb.expressions;

import eventb.AObjectEventB;
import eventb.expressions.sets.ASet;
import eventb.tools.formatters.IEventBFormatter;
import eventb.tools.formatters.IExpressionFormatter;
import eventb.tools.formatters.IExpressionFormatterVisitable;

/**
 * Created by gvoiron on 05/08/16.
 * Time : 13:56
 */
public final class FunctionDefinition extends AObjectEventB implements IExpressionFormatterVisitable {

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

    @Override
    public String accept(IExpressionFormatter visitor) {
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
