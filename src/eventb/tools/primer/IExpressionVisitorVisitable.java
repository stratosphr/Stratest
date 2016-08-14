package eventb.tools.primer;

import eventb.expressions.AExpression;

/**
 * Created by gvoiron on 14/08/16.
 * Time : 10:31
 */
public interface IExpressionVisitorVisitable {

    AExpression accept(IExpressionToExpressionVisitor visitor);

}
