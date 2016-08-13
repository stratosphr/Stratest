package eventb.expressions;

import eventb.AObjectEventB;
import eventb.expressions.arith.AAssignable;
import eventb.tools.formatters.IExpressionFormatterVisitable;
import eventb.tools.replacer.IAssignableReplacerVisitable;

import java.util.LinkedHashSet;

/**
 * Created by gvoiron on 07/07/16.
 * Time : 00:41
 */
public abstract class AExpression extends AObjectEventB implements IAssignableReplacerVisitable, IExpressionFormatterVisitable {

    public abstract LinkedHashSet<AAssignable> getAssignables();

}
