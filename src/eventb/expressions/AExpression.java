package eventb.expressions;

import eventb.AObjectEventB;
import eventb.tools.formatters.IExpressionFormatterVisitable;
import eventb.tools.replacer.IAssignableReplacerVisitable;

/**
 * Created by gvoiron on 07/07/16.
 * Time : 00:41
 */
public abstract class AExpression extends AObjectEventB implements IAssignableReplacerVisitable, IExpressionFormatterVisitable {
}
