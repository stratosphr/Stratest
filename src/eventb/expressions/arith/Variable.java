package eventb.expressions.arith;

import eventb.expressions.AExpression;
import eventb.tools.formatter.IEventBFormatter;
import eventb.tools.replacer.IAssignableReplacer;

/**
 * Created by gvoiron on 07/07/16.
 * Time : 10:45
 */
public final class Variable extends AAssignable {

    private String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public String accept(IEventBFormatter visitor) {
        return visitor.visit(this);
    }

    public String getName() {
        return name;
    }

    @Override
    public AExpression accept(IAssignableReplacer visitor) {
        return visitor.visit(this);
    }

}
