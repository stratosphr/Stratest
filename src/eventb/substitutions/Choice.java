package eventb.substitutions;

import eventb.expressions.bool.ABooleanExpression;
import eventb.expressions.bool.And;
import eventb.tools.formatters.IEventBFormatter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by gvoiron on 15/08/16.
 * Time : 09:29
 */
public class Choice extends ASubstitution {

    private final List<ASubstitution> substitutions;

    public Choice(ASubstitution... substitutions) {
        this.substitutions = Arrays.asList(substitutions);
    }

    @Override
    public String accept(IEventBFormatter visitor) {
        return visitor.visit(this);
    }

    @Override
    public ABooleanExpression getWP(ABooleanExpression postCondition) {
        return new And(getSubstitutions().stream().map(aSubstitution -> aSubstitution.getWP(postCondition)).toArray(ABooleanExpression[]::new));
    }

    public List<ASubstitution> getSubstitutions() {
        return substitutions;
    }

}
