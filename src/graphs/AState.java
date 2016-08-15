package graphs;

import eventb.expressions.arith.AAssignable;
import eventb.expressions.bool.ABooleanExpression;
import eventb.expressions.bool.APredicate;

import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 13/08/16.
 * Time : 15:47
 */
public abstract class AState extends APredicate {

    public AState(String name, ABooleanExpression expression) {
        super(name, expression);
    }

    @Override
    public final LinkedHashSet<AAssignable> getAssignables() {
        return getExpression().getAssignables();
    }

}
