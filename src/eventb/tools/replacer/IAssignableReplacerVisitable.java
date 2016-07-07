package eventb.tools.replacer;

import eventb.expressions.AExpression;

/**
 * Created by gvoiron on 07/07/16.
 * Time : 11:59
 */
public interface IAssignableReplacerVisitable {

    AExpression accept(IAssignableReplacer visitor);

}
